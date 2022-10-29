package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivitySplashScreenBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Preferences

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySplashScreenBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        Handler(Looper.getMainLooper()).postDelayed({
            val session = Preferences.get(this, Constant.Key.SESSION) as String?
            val phone = Preferences.get(this, Constant.Key.WHATSAPP) as String?

            if (session != null && phone != null) {
                val bundle = Bundle()
                bundle.putString(Constant.Key.SESSION, session)
                bundle.putString(Constant.Key.WHATSAPP, phone)
                GeneralHelper.move(this, StartSessionActivity::class.java, bundle, true)
            }

            GeneralHelper.move(this, LoginActivity::class.java, true)
        }, 3000L)
    }
}