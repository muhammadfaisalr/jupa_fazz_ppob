package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.ProductDetailAdapter
import id.muhammadfaisal.jupafazz.adapter.ProductTitleAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.product.ProductResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityCreateTransactionBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class CreateTransactionActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityCreateTransactionBinding

    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCreateTransactionBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.data()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.category = bundle.getString(Constant.Key.CATEGORY, "")
        }
    }

    private fun initialize() {
        this.binding.let {
            Font.setInto(this, Font.Rubik.MEDIUM, it.textLastTransactionTitle)
            Font.setInto(this, Font.Rubik.REGULAR, it.inputSearch)


            it.inputSearch.addTextChangedListener(this)
        }
    }

    private fun data() {

        val categories = if (category.isEmpty()) {
            DatabaseHelper.productDao(this).getCategories()
        } else {
            DatabaseHelper.productDao(this).getCategories(category)
        }

        this.binding.apply {
            this.recyclerProduct.layoutManager = LinearLayoutManager(this@CreateTransactionActivity, RecyclerView.VERTICAL, false)
            this.recyclerProduct.adapter = ProductTitleAdapter(this@CreateTransactionActivity, categories)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        val productDao = DatabaseHelper.productDao(this)

        if (p0 != null) {
            if (p0.isNotEmpty()) {
                val s = p0.toString()

                val products = if (category.isNotEmpty()) {
                    productDao.query(Formatter.queryFormat(s), category)
                } else {
                    productDao.query(Formatter.queryFormat(s))
                }

                this.binding.recyclerProduct.adapter = ProductDetailAdapter(this, products)
            } else {
                this.data()
            }
        }
    }
}