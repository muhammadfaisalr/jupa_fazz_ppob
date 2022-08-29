package id.muhammadfaisal.jupafazz.api

import id.muhammadfaisal.jupafazz.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    companion object {
        fun getRetrofit(): Retrofit {
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer edQexwDiJ9iedQexwDiJ9iN727Rwb3qN727Rwb3q"
                        )
                        .build()

                    it.proceed(request)
                }
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constant.URL.BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}