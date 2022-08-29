package id.muhammadfaisal.jupafazz.api.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
   @SerializedName("name")
   var name: String,
   @SerializedName("wa")
   var wa: String,
   @SerializedName("password")
   var password: String,
   @SerializedName("confirm_password")
   var confirmPassword: String,
) {}