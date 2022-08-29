package id.muhammadfaisal.jupafazz.api.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
   @SerializedName("wa")
   val wa: String,
   @SerializedName("password")
   val password: String,
) {}