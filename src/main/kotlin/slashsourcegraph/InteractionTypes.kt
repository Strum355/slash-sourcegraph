package slashsourcegraph

import com.google.gson.annotations.SerializedName

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
    val options: List<ApplicationCommandInteractionDataOption>?,
)

data class Interaction(
    val type: InteractionType,
    val data: ApplicationCommandInteractionData,
    val token: String,
    @SerializedName("channel_id")
    val channelID: String,
    @SerializedName("guild_id")
    val guildID: String
)

data class InteractionApplicationCommandCallbackData(val content: String)

data class InteractionResponse(val type: InteractionResponseType, val data: InteractionApplicationCommandCallbackData?)