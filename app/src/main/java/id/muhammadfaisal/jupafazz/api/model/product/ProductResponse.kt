package id.muhammadfaisal.jupafazz.api.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    var id: String,

    @SerializedName("product")
    var product: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("brand")
    var brand  : String,

    @SerializedName("category")
    var category: String,

    @SerializedName("price")
    var price: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("sku")
    var sku: String,

    @SerializedName("note")
    var note: String,
) {}