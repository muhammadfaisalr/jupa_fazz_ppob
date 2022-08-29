package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivityOtpBinding
import id.muhammadfaisal.jupafazz.databinding.ActivityRegisterBinding

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityOtpBinding.inflate(this.layoutInflater)
        setContentView(R.layout.activity_otp)
    }
}