package id.muhammadfaisal.jupafazz.activity

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityOtpBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class OtpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOtpBinding

    private lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityOtpBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.phone = bundle.getString(Constant.Key.WHATSAPP, "")
        }
    }

    private fun initialize() {
        this.setupTimer()
        this.binding.let {
            Font.apply {
                setInto(this@OtpActivity, Font.Rubik.MEDIUM, it.textVerificationTitle)
                setInto(this@OtpActivity, Font.Rubik.REGULAR, it.textVerification, it.otpView, it.buttonVerification)
            }

            it.textVerification.text = "Kami telah mengirimkan kode verifikasi ke nomor $phone"

            ViewHelper.makeClickable(this, it.buttonVerification, it.textResend)

            it.otpView.otpListener = object : OTPListener {
                override fun onInteractionListener() {

                }

                override fun onOTPComplete(otp: String) {
                    this@OtpActivity.startVerification(otp)
                }

            }
        }
    }

    private fun setupTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textResend.text = "Kirim Ulang(${millisUntilFinished / 1000})"
                binding.textResend.isEnabled = false
                binding.textResend.setTextColor(resources.getColor(com.denzcoskun.imageslider.R.color.grey_font))
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding.textResend.text = "Kirim Ulang"
                binding.textResend.isEnabled = true
                binding.textResend.setTextColor(resources.getColor(R.color.blue))
            }
        }.start()
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonVerification) {
            this.verification()
        } else if (p0 == this.binding.textResend) {
            this.resend()
        }
    }

    private fun verification() {
        if (this.binding.otpView.otp != null) {
            val otp = this.binding.otpView.otp!!
            if (otp.length == 6) {
                this.startVerification(otp)
            }
        }
    }

    private fun resend() {
        var isSuccess = true
        CompositeDisposable().add(
            ApiHelper
                .resendOtp(phone)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()!!

                        if (!body.isSuccess) {
                            isSuccess = false
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            this@OtpActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        if (isSuccess) {
                            setupTimer()
                        }
                    }

                })
        )
    }

    private fun startVerification(otp: String) {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        CompositeDisposable().add(
            ApiHelper
                .otpVerification(phone, otp)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        if (t.body() != null) {
                            val body = t.body()!!
                            if (body.isSuccess) {
                                GeneralHelper.move(this@OtpActivity, MainActivity::class.java, true)
                            } else {
                                binding.otpView.showError()
                                BottomSheets.error(
                                    this@OtpActivity,
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
                            this@OtpActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        loading.dismiss()
                    }
                })
        )
    }
}