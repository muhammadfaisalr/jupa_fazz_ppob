package id.muhammadfaisal.jupafazz.api.model.deposit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DepositMethodResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("method")
    var method: String,
): Serializable