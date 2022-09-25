package id.muhammadfaisal.jupafazz.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.product.ProductResponse
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class VersioningService() : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val productV = Preferences.get(applicationContext, Constant.Key.PRODUCT_V)
        var productVersion = ""
        CompositeDisposable().add(
            ApiHelper
                .getProductVersion()
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()!!

                        if (body.isSuccess) {
                            val data = body.data as Map<*, *>
                            productVersion = data["version"].toString()
                        } else {
                            BottomSheets.error(
                                (baseContext as AppCompatActivity),
                                getString(R.string.something_wrong),
                                body.message,
                                isShowReason = false,
                                isCancelable = true
                            )
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error((baseContext as AppCompatActivity), e, false, true)
                    }

                    override fun onComplete() {
                        if (productV == null || productV != productVersion) {
                            val startTime = System.currentTimeMillis()
                            Log.d(VersioningService::class.java.simpleName, "Versioning Started();")
                            CompositeDisposable().add(
                                ApiHelper
                                    .getProducts()
                                    .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                                        override fun onNext(t: Response<BaseResponse>) {
                                            if (t.body() != null) {
                                                val body = t.body()!!

                                                if (body.isSuccess) {
                                                    val data = body.data as Map<*, *>
                                                    val products1 = data["product"] as List<*>

                                                    for (product in products1) {
                                                        val gson = Gson()
                                                        val productA = gson.fromJson(
                                                            gson.toJson(product),
                                                            ProductResponse::class.java
                                                        )

                                                        DatabaseHelper
                                                            .productDao(applicationContext)
                                                            .insert(
                                                                ProductEntity(
                                                                    productA.id,
                                                                    productA.product,
                                                                    productA.type,
                                                                    productA.brand,
                                                                    productA.category,
                                                                    productA.price,
                                                                    productA.status,
                                                                    productA.sku,
                                                                    productA.note
                                                                )
                                                            )
                                                    }
                                                } else {
                                                    BottomSheets.error(
                                                        (baseContext as AppCompatActivity),
                                                        getString(R.string.something_wrong),
                                                        body.message,
                                                        isShowReason = false,
                                                        isCancelable = true
                                                    )
                                                }
                                            }
                                        }

                                        override fun onError(e: Throwable) {
                                            BottomSheets.error((baseContext as AppCompatActivity), e, false, true)
                                        }

                                        override fun onComplete() {
                                            Preferences.save(baseContext, Constant.Key.PRODUCT_V, productVersion)
                                            Log.d(
                                                VersioningService::class.java.simpleName,
                                                "Versioning Ended. Done in ${System.currentTimeMillis() - startTime}ms"
                                            )
                                            Toast.makeText(
                                                baseContext,
                                                "Data Produk Berhasil Diperbarui!.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    })
                            )
                        }

                    }

                })
        )


        return START_STICKY
    }
}