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

    @FormUrlEncoded
    @POST(Constant.URL.RESEND_OTP)
    fun resendOtp(
        @Field("wa") wa: String
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.LOGIN)
    fun login(
        @Field("wa") wa: String,
        @Field("password") password: String
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.VERIFICATION_OTP)
    fun otpVerification(
        @Field("wa") wa: String,
        @Field("otp") otp: String
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.START_SESSION)
    fun startSession(
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.USER_DETAIL)
    fun userDetail(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.TRANSACTION)
    fun transaction(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("product") product: String,
        @Field("pin") pin: String,
        @Field("target") target: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.DETAIL_TRANSACTION)
    fun detailTransaction(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("order_id") order_id: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.DEPOSIT_METHOD)
    fun depositMethod(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.DEPOSIT_REQUEST)
    fun depositRequest(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("method") method: String,
        @Field("quantity") amount: String
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.DEPOSIT_LIST)
    fun depositList(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.USER_BALANCE)
    fun getBalance(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>


    @FormUrlEncoded
    @POST(Constant.URL.LIST_TRANSACTION)
    fun getHistory(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.LIST_TRANSACTION)
    fun destroySession(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.USER_NOTIFICATION)
    fun notifications(
        @Field("wa") wa: String,
        @Field("session") session: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.CHANGE_PASSWORD)
    fun changePassword(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("password_old") oldPassword: String,
        @Field("password_new") newPassword: String,
        @Field("password_confirm") confirmNewPassword: String,
    ) : Observable<Response<BaseResponse>>

    @FormUrlEncoded
    @POST(Constant.URL.CHANGE_PIN)
    fun changePin(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("pin_old") oldPassword: String,
        @Field("pin_new") newPassword: String,
        @Field("pin_confirm") confirmNewPassword: String,
    ) : Observable<Response<BaseResponse>>


    @FormUrlEncoded
    @POST(Constant.URL.TRANSFER_INQUIRY)
    fun transferInquiry(
        @Field("wa") wa: String,
        @Field("session") session: String,
        @Field("to") to: String,
    ) : Observable<Response<BaseResponse>>

    @GET(Constant.URL.GET_PRODUCT)
    fun getProducts() : Observable<Response<BaseResponse>>

    @GET(Constant.URL.GET_PRODUCT_V)
    fun getProductVersion() : Observable<Response<BaseResponse>>

    @GET(Constant.URL.BANNER)
    fun getBanners() : Observable<Response<BaseResponse>>

    @GET(Constant.URL.BANNER_ACTIVITY)
    fun getOutletBanners() : Observable<Response<BaseResponse>>
}