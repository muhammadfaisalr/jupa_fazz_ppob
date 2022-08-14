package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivityLoginBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Font

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.binding.let {
            Font.apply {
                setInto(this@LoginActivity, Font.Rubik.SEMI_BOLD, it.textTitle)
                setInto(this@LoginActivity, Font.Rubik.REGULAR, it.textSubtitle, it.textRegisterTitle)
                setInto(this@LoginActivity, Font.Rubik.MEDIUM, it.buttonLogin, it.textRegister, it.textVersion)
            }

            ViewHelper.makeClickable(this, it.buttonLogin)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonLogin) {
            GeneralHelper.move(this, MainActivity::class.java, true)
        }
    }
}