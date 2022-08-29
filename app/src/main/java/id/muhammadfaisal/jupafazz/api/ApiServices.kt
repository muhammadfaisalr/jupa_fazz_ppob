package id.muhammadfaisal.jupafazz.api

import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.register.RegisterRequest
import id.muhammadfaisal.jupafazz.utils.Constant
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST(Constant.URL.REGISTER)
    fun register(
        @Field("name") name: String,
        @Field("wa") wa: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
    ): Observable<Response<BaseResponse>>
}