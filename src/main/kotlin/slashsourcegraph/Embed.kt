package slashsourcegraph

import com.google.gson.annotations.SerializedName

typealias Color = Int

data class Embed(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("color") val color: Color? = null,
    @SerializedName("footer") val footer: EmbedFooter? = null,
    @SerializedName("image") val image: EmbedImage? = null,
    @SerializedName("thumbnail") val thumbnail: EmbedImage? = null,
    @SerializedName("video") val video: EmbedVideo? = null,
    @SerializedName("provider") val provider: EmbedProvider? = null,
    @SerializedName("author") val author: EmbedAuthor? = null,
    @SerializedName("fields") val fields: List<EmbedField> = emptyList(),
    @SerializedName("type") val type: String = "rich"
)

data class EmbedImage(
    @SerializedName("url") val url: String? = null,
    @SerializedName("proxy_url") val proxyUrl: String? = null,
    @SerializedName("height") val imageHeight: Int = 0,
    @SerializedName("width") val imageWidth: Int = 0
)

data class EmbedVideo(
    @SerializedName("url") val url: String? = null,
    @SerializedName("height") val videoHeight: Int = 0,
    @SerializedName("width") val videoWidth: Int = 0
)

data class EmbedProvider(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)

data class EmbedAuthor(
    @SerializedName("name") val name: String,
    @SerializedName("url") val authorUrl: String? = null,
    @SerializedName("icon_url") val authorImageUrl: String? = null,
    @SerializedName("proxy_icon_url") val authorImageProxyUrl: String? = null
)

data class EmbedFooter(
    @SerializedName("text") val text: String,
    @SerializedName("icon_url") val iconUrl: String? = null,
    @SerializedName("proxy_icon_url") val iconProxyUrl: String? = null
)

data class EmbedField(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: String,
    @SerializedName("inline") val inline: Boolean? = null
)