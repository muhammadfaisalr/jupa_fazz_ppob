package id.muhammadfaisal.jupafazz.api.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionListResponse(
    @SerializedName("order_id")
    var orderId: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("sn")
    var sn: String,
    @SerializedName("target")
    var target: String,
    @SerializedName("product_name")
    var productName: String,
    @SerializedName("product_img")
    var productImg: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("date_create")
    var createDate: String,
)