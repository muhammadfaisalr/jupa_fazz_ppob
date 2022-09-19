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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class StartSessionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartSessionBinding

    private lateinit var session: String

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
        }
    }

    private fun initialize() {
        Font.setInto(this, Font.Rubik.MEDIUM, this.binding.textPleaseWait)
        Font.setInto(this, Font.Rubik.REGULAR, this.binding.textDesc)

        CompositeDisposable().add(
            ApiHelper
                .startSession(session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body  = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                GeneralHelper.move(this@StartSessionActivity, MainActivity::class.java, true)
                            } else {
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
                        BottomSheets.error(
                            this@StartSessionActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        startService(Intent(this@StartSessionActivity, VersioningService::class.java)).also { it ->
                            GeneralHelper.move(this@StartSessionActivity, MainActivity::class.java, true)
                        }
                    }
                })
        )

        //TODO:: Disini Cek Master Versionnya apakah beda dengan yg di lokal, kalau beda nanti di update data di db nya.
        /*        CompositeDisposable().add(
            ApiHelper
                .startSession(session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {

                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(this@StartSessionActivity, e, false, true)
                    }

                    override fun onComplete() {
                        GeneralHelper.move(this@StartSessionActivity, MainActivity::class.java, true)
                    }

                })
        )*/
    }
}