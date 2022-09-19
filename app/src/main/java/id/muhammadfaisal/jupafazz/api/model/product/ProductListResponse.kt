package id.muhammadfaisal.jupafazz.api.model.product

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("id")
    var id: String,

    @SerializedName("id")
    var product: String,

    @SerializedName("id")
    var type: String,

    @SerializedName("id")
    var brand  : String,

    @SerializedName("id")
    var category: String,

    @SerializedName("id")
    var price: String,

    @SerializedName("id")
    var status: String,

    @SerializedName("id")
    var sku: String,

    @SerializedName("id")
    var note: String,
) {}