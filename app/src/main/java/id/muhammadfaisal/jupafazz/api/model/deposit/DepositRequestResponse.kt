package id.muhammadfaisal.jupafazz.api.model.deposit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DepositRequestResponse(
    @SerializedName("deposit_id")
    var depositId: String,
    @SerializedName("method")
    var method: String,
    @SerializedName("payment")
    var payment: DepositPaymentResponse,
) : Serializable

data class DepositListResponse(
    @SerializedName("deposit_id")
    var depositId: String,
    @SerializedName("method")
    var method: String,
    @SerializedName("quantity")
    var quantity: String,
    @SerializedName("balance")
    var balance: String,
    @SerializedName("payment")
    var payment: DepositPaymentResponse,
    @SerializedName("status")
    var status: String,
    @SerializedName("date_create")
    var createDate: String,
    @SerializedName("image")
    var image: String
) : Serializable

data class DepositPaymentResponse(
    @SerializedName("type")
    var type: String,
    @SerializedName("data")
    var data: String,
) : Serializable