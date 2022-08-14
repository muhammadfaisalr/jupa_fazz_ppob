package id.muhammadfaisal.jupafazz.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivityMainBinding
import id.muhammadfaisal.jupafazz.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.inflate(HomeFragment())
    }

    private fun inflate(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.commit()
    }
}