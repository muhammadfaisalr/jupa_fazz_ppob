package id.muhammadfaisal.jupafazz.api.model.transaction

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailTransactionResponse (
    @SerializedName("wa")
    var wa: String,
    @SerializedName("product")
    var product: String,
    @SerializedName("target")
    var target: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("sn")
    var sn: String,
    @SerializedName("date_create")
    var createDate: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("date_update")
    var modifiedDate: String,
) : Serializable