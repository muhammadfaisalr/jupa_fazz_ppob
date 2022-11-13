package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.register.RegisterRequest
import id.muhammadfaisal.jupafazz.databinding.ActivityRegisterBinding
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

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityRegisterBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.binding.let {
            it.textVersion.text = "v" + GeneralHelper.getAppsVersion()
            Font.apply {
                setInto(
                    this@RegisterActivity,
                    Font.Rubik.REGULAR,
                    it.inputName,
                    it.inputPhoneNumber,
                    it.inputPassword,
                    it.inputConfirmPassword,
                    it.textRegisterTitle,
                    it.textVersion
                )

                setInto(
                    this@RegisterActivity,
                    Font.Rubik.MEDIUM,
                    it.buttonRegister,
                    it.textLogin
                )
            }
            ViewHelper.makeClickable(this, it.buttonRegister)
        }
    }

    override fun onClick(p0: View?) {
        p0!!
        if (p0 == this.binding.buttonRegister) {
            this.register()
        }
    }

    private fun register() {
        val isEmpty = GeneralHelper.isInputEmpty(
            this.binding.inputName,
            this.binding.inputPhoneNumber,
            this.binding.inputPassword,
            this.binding.inputConfirmPassword
        )

        if (isEmpty) {
            Toast.makeText(this, "Pastikan semua data terisi dengan benar!.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val name = this.binding.inputName.text.toString()
        val phone = "62" + this.binding.inputPhoneNumber.text.toString()
        val password = this.binding.inputPassword.text.toString()
        val confirmPassword = this.binding.inputConfirmPassword.text.toString()

        val isMatch = GeneralHelper.isStringMatch(password, confirmPassword)

        if (!isMatch) {
            Toast.makeText(this, "Password tidak sama!.", Toast.LENGTH_SHORT).show()
            return
        }

        //Process Register
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val registerRequest = RegisterRequest(name, phone, password, confirmPassword)
        var isSuccess = false;

        CompositeDisposable().add(
            ApiHelper
                .register(registerRequest)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        if (t.body() != null) {
                            val body = t.body()!!
                            if (body.isSuccess) {
                                isSuccess = true
                            } else {
                                loading.dismiss()

                                BottomSheets.error(
                                    this@RegisterActivity,
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
                            this@RegisterActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        if (isSuccess) {
                            loading.dismiss()

                            val bundle = Bundle()
                            bundle.putString(Constant.Key.WHATSAPP, phone)
                            GeneralHelper.move(
                                this@RegisterActivity,
                                OtpActivity::class.java,
                                bundle,
                                false
                            )
                        }
                    }
                })
        )
    }
}