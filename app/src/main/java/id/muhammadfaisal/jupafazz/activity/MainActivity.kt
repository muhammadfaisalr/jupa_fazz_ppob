package id.muhammadfaisal.jupafazz.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ActivityMainBinding
import id.muhammadfaisal.jupafazz.fragment.HistoryFragment
import id.muhammadfaisal.jupafazz.fragment.HomeFragment
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    View.OnClickListener {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
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