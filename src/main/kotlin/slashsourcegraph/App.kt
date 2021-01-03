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

@KtorExperimentalAPI
fun main() {
    val applicationID = checkNotNull(System.getProperty("application_id")) { "application_id property not set" }
    val bearerToken = checkNotNull(System.getProperty("bearer_token")) { "bearer_token property not set" }

    // because it falls over when it sees __typename ...
    val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val graphqlClient = GraphQLKtorClient(URL("https://sourcegraph.com/.api/graphql"), mapper = objectMapper)

    val gson = GsonBuilder().interactionTypeAdapters().create()

    embeddedServer(CIO, port = 8080) {
        main(gson, graphqlClient, applicationID, bearerToken)
    }.start(true)
}

fun Application.main(gson: Gson, graphqlClient: GraphQLClient, applicationID: String, bearerToken: String) {
    install(ContentNegotiation) {
        gson(block = GsonBuilder::interactionTypeAdapters)
    }
    install(CallLogging)

    routing {
        post("/") {
            val interaction = call.receive<Interaction>()

            this@main.log.info("received request: $interaction")

            when(interaction.type) {
                InteractionType.PING -> {
                    call.respond(gson, InteractionResponse(InteractionResponseType.PONG, null))
                }
                InteractionType.APPLICATION_COMMAND -> {
                    call.respond(gson, InteractionResponse(InteractionResponseType.ACK_WITH_SOURCE, null))

                    val query = searchQueryGenerators[interaction.data.name]?.invoke(interaction.data.options) ?: return@post

                    val graphqlResponse = querySourcegraph(graphqlClient, query)

                    followUpDiscord(graphqlResponse, applicationID, bearerToken, interaction.token)
                }
                else -> {}
            }
        }
    }
}

suspend fun Application.querySourcegraph(graphqlClient: GraphQLClient, query: String): GraphQLResponse<Query.Result> {
    val resp = Query(graphqlClient).execute(Query.Variables(query))

    this.log.info("graphql response: data=${resp.data?.search?.results?.results?.size} errors=${resp.errors?.size}")

    return resp
}

suspend fun Application.followUpDiscord(
    graphQLResponse: GraphQLResponse<Query.Result>,
    applicationID: String,
    bearerToken: String,
    interactionToken: String
) {
    val interactionResponse = mapOf<String, Any>(
        "embeds" to listOf(formatGraphQLResponse(graphQLResponse))
    )

    HttpClient().use {
        val response: String = it.post("https://discord.com/api/v8/webhooks/${applicationID}/${interactionToken}") {
            headers.append("Authorization", "Bearer $bearerToken")
            contentType(ContentType.Application.Json)
            body = Gson().toJson(interactionResponse)
        }

        this.log.info("discord response: $response")
    }
}

fun formatGraphQLResponse(resp: GraphQLResponse<Query.Result>): Embed {
    val fields = resp.data?.search?.results?.results
        ?.mapNotNull {
            if(it !is Query.FileMatch) return@mapNotNull null
            val lines = it.file.content.split("\n")
            return@mapNotNull EmbedField(
                // repo+filepath and link to the file on Sourcegraph
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
        if (input.peek() == JsonToken.NULL) {
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

suspend fun ApplicationCall.respond(gson: Gson, response: InteractionResponse) {
    this.application.log.info("sending response: $response")
    this.respondText(gson.toJson(response))
}

fun GsonBuilder.interactionTypeAdapters() = apply {
    this.registerTypeAdapter(InteractionResponseType::class.java, EnumSerializer.create<InteractionResponseType>())
    this.registerTypeAdapter(InteractionType::class.java, EnumSerializer.create<InteractionType>())
}