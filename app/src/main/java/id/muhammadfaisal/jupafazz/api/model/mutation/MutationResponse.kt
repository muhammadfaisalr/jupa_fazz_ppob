package id.muhammadfaisal.jupafazz.api.model.mutation

import com.google.gson.annotations.SerializedName

data class MutationResponse(
    @SerializedName("description")
    var description: String,
    @SerializedName("balance")
    var balance: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("date_create")
    var createDate: String,
) {}
