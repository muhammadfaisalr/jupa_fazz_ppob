package id.muhammadfaisal.jupafazz.api.model

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any
) {}