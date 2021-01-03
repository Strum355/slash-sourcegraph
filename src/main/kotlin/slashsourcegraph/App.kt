package slashsourcegraph

import com.expediagroup.graphql.client.GraphQLClient
import com.expediagroup.graphql.client.GraphQLKtorClient
import com.expediagroup.graphql.types.GraphQLResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import io.ktor.util.*
import slashsourcegraph.graphql.Query
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

enum class InteractionType {
    SKIP,
    PING,
    APPLICATION_COMMAND,
}

enum class InteractionResponseType {
    SKIP,
    PONG,
    ACKNOWLEDGE,
    CHANNEL_MESSAGE,
    CHANNEL_MESSAGE_WITH_SOURCE,
    ACK_WITH_SOURCE,
}

data class ApplicationCommandInteractionDataOption(val name: String, val value: String?)

data class ApplicationCommandInteractionData(
    val name: String,
    val options: Array<ApplicationCommandInteractionDataOption>?
)

data class Interaction(val type: InteractionType, val data: ApplicationCommandInteractionData, val token: String)

data class InteractionApplicationCommandCallbackData(val content: String)

data class InteractionResponse(val type: InteractionResponseType, val data: InteractionApplicationCommandCallbackData?)

@KtorExperimentalAPI
fun main() {
    val applicationID = ""
    val token = ""

    // because it falls over when it sees __typename ...
    val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val graphqlClient = GraphQLKtorClient(URL("https://sourcegraph.com/.api/graphql"), mapper = objectMapper)

    val gson = GsonBuilder().apply { this.interactionTypeAdapters() }.create()

    embeddedServer(CIO, port = 8080) {
        install(ContentNegotiation) {
            gson(block = GsonBuilder::interactionTypeAdapters)
        }
        install(CallLogging)
        routing {
            post("/") {
                val interaction = call.receive<Interaction>()
                println(interaction)
                when(interaction.type) {
                    InteractionType.PING -> {
                        call.respondText {
                            gson.toJson(InteractionResponse(InteractionResponseType.PONG, null))
                        }
                    }
                    InteractionType.APPLICATION_COMMAND -> {
                        call.respondText {
                            gson.toJson(InteractionResponse(InteractionResponseType.ACK_WITH_SOURCE, null))
                        }

                        querySourcegraph(graphqlClient, interaction.data.options!![0].value!!, applicationID, token, interaction.token)
                    }
                    else -> {}
                }
            }
        }
    }.start(true)
}

@KtorExperimentalAPI
suspend fun querySourcegraph(graphqlClient: GraphQLClient, query: String, applicationID: String, bearerToken: String, interactionToken: String) {
    val resp = Query(graphqlClient).execute(Query.Variables(query))
    val interactionResponse = mapOf<String, Any>(
        "embeds" to listOf(formatGraphQLResponse(resp))
    )

    println(Gson().toJson(interactionResponse))

    HttpClient {
        install(HttpTimeout)
        expectSuccess = false
    }.use {
        val response: String = it.post("https://discord.com/api/v8/webhooks/${applicationID}/${interactionToken}") {
            headers.append("Authorization", "Bearer $bearerToken")
            contentType(ContentType.Application.Json)
            timeout {
                this.connectTimeoutMillis = 1500
                this.requestTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
            body = Gson().toJson(interactionResponse)
        }
        println("response $response")
    }
}

fun formatGraphQLResponse(resp: GraphQLResponse<Query.Result>): Embed {
    val fields = resp.data?.search?.results?.results
        //?.groupBy { (it as Query.FileMatch).repository.url }
        ?.mapNotNull {
            if(it !is Query.FileMatch) return@mapNotNull null
            val lines = it.file.content.split("\n")
            return@mapNotNull EmbedField(
                "${it.repository.name}/${it.file.path} https://sourcegraph.com${it.file.url}",
                it.lineMatches.foldIndexed("```go\n") { i, s, l ->
                    s + (if(i != 0) "\n...\n" else "") +
                        lines.subList(l.lineNumber - 1, l.lineNumber + 2).mapIndexed { i1, line ->
                            (if(i1 == 1) "*" else " ") + "${l.lineNumber-1+i1}| $line"
                        }.joinToString("\n")
                } + "\n```"
            )
        } ?: emptyList()
    return Embed(fields = fields)
}

class EnumSerializer<T: Enum<T>>(private val clazz: Class<T>): TypeAdapter<T>() {
    companion object {
        inline fun <reified T: Enum<T>> create() = EnumSerializer(T::class.java)
    }

    override fun read(input: JsonReader): T? {
        if (input.peek() === JsonToken.NULL) {
            input.nextNull()
            return null
        }
        val stringValue: String = input.nextString()
        return try {
            val int = Integer.valueOf(stringValue)
            clazz.enumConstants.getOrNull(int)
        } catch (e: NumberFormatException) {
            null
        }
    }

    override fun write(out: JsonWriter, value: T?) {
        if(value == null) {
            out.nullValue()
            return
        }
        out.value(value.ordinal)
    }
}

fun GsonBuilder.interactionTypeAdapters() = apply {
    this.registerTypeAdapter(InteractionResponseType::class.java, EnumSerializer.create<InteractionResponseType>())
    this.registerTypeAdapter(InteractionType::class.java, EnumSerializer.create<InteractionType>())
}