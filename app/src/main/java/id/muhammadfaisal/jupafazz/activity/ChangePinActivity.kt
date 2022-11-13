package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityChangePasswordBinding
import id.muhammadfaisal.jupafazz.databinding.ActivityChangePinBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class ChangePinActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangePinBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityChangePinBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.bindingHeader = this.binding.header
        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.change_pin)
            this.textSubtitle.text = getString(R.string.change_pin_desc)

            Font.setInto(this@ChangePinActivity, Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto(this@ChangePinActivity, Font.Rubik.REGULAR, this.textSubtitle)

            this.imageBack.setOnClickListener(this@ChangePinActivity)
        }

        this.binding.apply {
            ViewHelper.makeClickable(this@ChangePinActivity, this.buttonProcess)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.bindingHeader.imageBack) {
            this.finish()
        } else if (p0 == this.binding.buttonProcess) {
            this.process()
        }
    }

    private fun process() {
        if (GeneralHelper.isInputEmpty(this.binding.inputOldPin, this.binding.inputNewPin, this.binding.inputConfirmNewPin)) {
            Toast.makeText(this, "Pastikan semua form telah diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val input = GeneralHelper.getInputValues(this.binding.inputOldPin, this.binding.inputNewPin, this.binding.inputConfirmNewPin)

        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)

        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        var isSuccess = true

        CompositeDisposable().add(
            ApiHelper
                .changePin(wa, session, input[0].toString(), input[1].toString(), input[2].toString())
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()
                        if (body != null) {
                            //Only check if not success
                            if (!body.isSuccess) {
                                isSuccess = false
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(this@ChangePinActivity)
                                    return
                                }

                                BottomSheets.error(
                                    this@ChangePinActivity,
                                    getString(R.string.something_wrong),
                                    body.message,
                                    false,
                                    true
                                )
                            }
                        } else {
                            isSuccess = false
                            Toast.makeText(this@ChangePinActivity, "Terjadi Kesalahan, kami sedang memperbaikinya.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            this@ChangePinActivity,
                            e,
                            false,
                            true
                        )
                    }

                    override fun onComplete() {
                        loading.dismiss()

                        if (isSuccess) {
                            finish()
                        }
                    }
                })
        )
    }
}