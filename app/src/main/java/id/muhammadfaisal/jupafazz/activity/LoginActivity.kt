package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.login.LoginRequest
import id.muhammadfaisal.jupafazz.databinding.ActivityLoginBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.apache.commons.lang3.StringUtils
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {

        val session = Preferences.get(this, Constant.Key.SESSION) as String?
        val phone = Preferences.get(this, Constant.Key.WHATSAPP) as String?

        if (session != null && phone != null) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.SESSION, session)
            bundle.putString(Constant.Key.WHATSAPP, phone)
            GeneralHelper.move(this, StartSessionActivity::class.java, bundle, true)
        }

        this.binding.let {
            Font.apply {
                setInto(this@LoginActivity, Font.Rubik.SEMI_BOLD, it.textTitle)
                setInto(this@LoginActivity, Font.Rubik.REGULAR, it.textSubtitle, it.textRegisterTitle)
                setInto(this@LoginActivity, Font.Rubik.MEDIUM, it.buttonLogin, it.textRegister, it.textVersion)
            }

            ViewHelper.makeClickable(this, it.buttonLogin, it.textRegister)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonLogin) {
            this.login()
        } else if (p0 == this.binding.textRegister) {
            GeneralHelper.move(this, RegisterActivity::class.java, false)
        }
    }

    private fun login() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val isEmpty = GeneralHelper.isInputEmpty(this.binding.inputPassword, this.binding.inputPhoneNumber)

        if (isEmpty) {
            Toast.makeText(this, "Pastikan semua data sudah terisi", Toast.LENGTH_SHORT).show()
            return
        }

        val values = GeneralHelper.getInputValues(this.binding.inputPhoneNumber, this.binding.inputPassword)

        //Get value by index when input to param
        val phone = "62" + values[0]
        val password = values[1]!!

        val loginRequest = LoginRequest(phone, password)
        var isSuccess = false

        var session = ""
        CompositeDisposable().add(
            ApiHelper
                .login(loginRequest)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        if (t.body() != null) {
                            val body = t.body()!!
                            if (body.isSuccess) {
                                isSuccess = true
                                val data = body.data as Map<*, *>
                                session = data["session"].toString()
                            } else {
                                loading.dismiss()
                                BottomSheets.error(
                                    this@LoginActivity,
                                    getString(R.string.something_wrong),
                                    body.message,
                                    isShowReason = true,
                                    isCancelable = true
                                )
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            this@LoginActivity,
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true,
                        )
                    }

                    override fun onComplete() {
                        loading.dismiss()

                        if (isSuccess || session.isNotEmpty()) {

                            Preferences.save(this@LoginActivity, Constant.Key.SESSION, session)

                            val bundle = Bundle()
                            bundle.putString(Constant.Key.SESSION, session)
                            bundle.putString(Constant.Key.WHATSAPP, phone)
                            GeneralHelper.move(this@LoginActivity, StartSessionActivity::class.java, bundle, true)
                        }
                    }
                })
        )
    }
}