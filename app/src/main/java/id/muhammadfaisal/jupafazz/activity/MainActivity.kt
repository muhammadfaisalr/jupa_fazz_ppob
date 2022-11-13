package id.muhammadfaisal.jupafazz.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivityMainBinding
import id.muhammadfaisal.jupafazz.fragment.AccountFragment
import id.muhammadfaisal.jupafazz.fragment.HistoryFragment
import id.muhammadfaisal.jupafazz.fragment.HomeFragment
import id.muhammadfaisal.jupafazz.fragment.NotificationFragment
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    View.OnClickListener {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.checkPermissions()
        this.initialize()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            BottomSheets.requestPermission(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), false)
        }
    }

    private fun initialize() {
        this.inflate(HomeFragment())

        this.binding.let {
            it.bottomNavBar.menu[2].isEnabled = false
            it.bottomNavBar.setOnItemSelectedListener(this)

            ViewHelper.makeClickable(this, it.fab)
        }
    }

    private fun inflate(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.title.equals(getString(R.string.history))) {
            this.inflate(HistoryFragment())
        } else if (item.title.equals(getString(R.string.home))) {
            this.inflate(HomeFragment())
        } else if (item.title.equals(getString(R.string.account))) {
            this.inflate(AccountFragment())
        } else if (item.title.equals(getString(R.string.notification))) {
            this.inflate(NotificationFragment())
        }

        item.isChecked = true
        return false
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.fab) {
            GeneralHelper.move(this, CreateTransactionActivity::class.java, false)
        }
    }
}