package id.muhammadfaisal.jupafazz.helper

import android.util.Log
import id.muhammadfaisal.jupafazz.api.ApiServices
import id.muhammadfaisal.jupafazz.api.RetrofitBuilder
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
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
            Log.i(ApiHelper::class.simpleName, registerRequest.toString())

            return getServices()
                .register(registerRequest.name, registerRequest.wa, registerRequest.password, registerRequest.confirmPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}