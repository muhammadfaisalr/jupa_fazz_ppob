package id.muhammadfaisal.jupafazz.api.model.banner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BannerResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("sub_title")
    val subtitle: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
): Serializable {}