package id.muhammadfaisal.jupafazz.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.product.ProductResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityStartSessionBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.service.VersioningService
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class StartSessionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartSessionBinding

    private lateinit var session: String
    private lateinit var wa: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityStartSessionBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.session = bundle.getString(Constant.Key.SESSION, "")
            this.wa = bundle.getString(Constant.Key.WHATSAPP, "")
        } else {
            this.session = Preferences.get(this, Constant.Key.SESSION) as String
            this.wa = Preferences.get(this, Constant.Key.WHATSAPP) as String
        }
    }

    private fun initialize() {
        Font.setInto(this, Font.Rubik.MEDIUM, this.binding.textPleaseWait)
        Font.setInto(this, Font.Rubik.REGULAR, this.binding.textDesc)

        var isSuccess = false
        CompositeDisposable().add(
            ApiHelper
                .userDetail(wa, session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                val data = body.data as Map<*, *>
                                this@StartSessionActivity.apply {
                                    Preferences.save(this, Constant.Key.BALANCE, data["balance"]!!)
                                    Preferences.save(this, Constant.Key.NAME, data["name"]!!)
                                    Preferences.save(this, Constant.Key.WHATSAPP, wa)
                                }
                            } else {
                                isSuccess = false
                                BottomSheets.error(
                                    this@StartSessionActivity,
                                    getString(R.string.something_wrong),
                                    body.message,
                                    isShowReason = false,
                                    isCancelable = true
                                )
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        isSuccess = false
                        BottomSheets.error(
                            this@StartSessionActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        this@StartSessionActivity.apply {
                            startService(
                                Intent(
                                    this,
                                    VersioningService::class.java
                                )
                            ).also {
                                GeneralHelper.move(this, MainActivity::class.java, true)
                            }
                        }
                    }
                })
        )
    }
}