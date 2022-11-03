package id.muhammadfaisal.jupafazz.helper

import android.util.Log
import id.muhammadfaisal.jupafazz.api.ApiServices
import id.muhammadfaisal.jupafazz.api.RetrofitBuilder
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.login.LoginRequest
import id.muhammadfaisal.jupafazz.api.model.register.RegisterRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ApiHelper {
    companion object {
        private fun getServices(): ApiServices {
            return RetrofitBuilder.getRetrofit().create(ApiServices::class.java)
        }

        fun register(registerRequest: RegisterRequest): Observable<Response<BaseResponse>> {
            return getServices()
                .register(registerRequest.name, registerRequest.wa, registerRequest.password, registerRequest.confirmPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun resendOtp(wa: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .resendOtp(wa)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun login(loginRequest: LoginRequest): Observable<Response<BaseResponse>> {
            return getServices()
                .login(loginRequest.wa, loginRequest.password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun otpVerification(wa: String, otp: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .otpVerification(wa, otp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getMutation(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .getBalance(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getHistory(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .getHistory(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun destroySession(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .destroySession(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun changePassword(wa: String, session: String, oldPassword: String, newPassword: String, confirmPassword: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .changePassword(wa, session, oldPassword, newPassword, confirmPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun transaction(wa: String, session: String, product: String, target: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .transaction(wa, session, product, target)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun detailTransaction(wa: String, session: String, orderId: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .detailTransaction(wa, session, orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun depositMethod(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .depositMethod(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }


        fun depositRequest(wa: String, session: String, method: String, amount: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .depositRequest(wa, session, method, amount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }


        fun depositList(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .depositList(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun startSession(session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .startSession(session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getProductVersion() : Observable<Response<BaseResponse>> {
            return getServices()
                .getProductVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun userDetail(wa: String, session: String) : Observable<Response<BaseResponse>> {
            return getServices()
                .userDetail(wa, session)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getProducts() : Observable<Response<BaseResponse>> {
            return getServices()
                .getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getBanners() : Observable<Response<BaseResponse>> {
            return getServices()
                .getBanners()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun getOutletBanners() : Observable<Response<BaseResponse>> {
            return getServices()
                .getOutletBanners()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}