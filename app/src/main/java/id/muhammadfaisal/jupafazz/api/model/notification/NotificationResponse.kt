package id.muhammadfaisal.jupafazz.api.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("date_create")
    val createDate: String,

) {}