package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Build
import android.os.Bundle
import android.preference.Preference
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.SuccessTransactionActivity
import id.muhammadfaisal.jupafazz.adapter.ProductDetailAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentDetailProductBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.listener.ProductListener
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.apache.commons.lang3.StringUtils
import retrofit2.Response

class DetailProductBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener,
    ProductListener {

    private lateinit var binding: FragmentDetailProductBottomSheetDialogBinding

    private lateinit var productEntity: ProductEntity
    private var productSelected: ProductEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentDetailProductBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.extract()
        this.initialize()
        this.setupRecycler()
    }

    private fun setupRecycler() {
        this.binding.let {
            it.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            it.recyclerView.adapter = ProductDetailAdapter(this.requireContext(), DatabaseHelper.productDao(requireContext()).getAllByBrandAndCategory(this.productEntity.brand, this.productEntity.category), Constant.CLS_NAME.DETAIL_PROD_BOTSHEET, this)
            (it.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun extract() {
        val bundle = this.requireArguments()
        this.productEntity = bundle.getSerializable(Constant.Key.PRODUCT_ENT) as ProductEntity
    }

    private fun initialize() {
        this.binding.let {
            val activity = (requireActivity() as AppCompatActivity)

            Font.setInto(activity, Font.Rubik.MEDIUM, it.textTitle, it.textPrice, it.buttonBuy, it.textSelectedPrice)
            Font.setInto(activity, Font.Rubik.REGULAR, it.textNote, it.inputBill, it.textTitlePrice, it.textSelectedProduct)

            this.configHint()

            it.textTitle.text = this.productEntity.category + " " + this.productEntity.brand

            ViewHelper.makeClickable(this, it.imageClose, it.buttonBuy, it.imageSelectedClose)
        }
    }

    private fun configHint() {
        var hint = getString(R.string.fill_phone_number)

        when (this.productEntity.category) {
            Constant.CATEGORY.PLN -> {
                hint = getString(R.string.fill_customer_id)
            }
            Constant.CATEGORY.GAMES -> {
                hint = getString(R.string.fill_customer_id)
            }
            Constant.CATEGORY.VOUCHER -> {
                hint = getString(R.string.fill_id)
            }
        }

        this.binding.inputBill.hint = hint
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageClose) {
            this.dismiss()
        } else if (p0 == this.binding.buttonBuy) {
            this.process()
        } else if (p0 == this.binding.imageSelectedClose) {
            this.closeSelected()
        }
    }

    private fun closeSelected() {
        this.binding.let {
            it.textPrice.text = "Rp-"
            ViewHelper.gone(it.relativeSelected)
            ViewHelper.visible(it.recyclerView)
        }

        this.productSelected = null
    }

    private fun process() {

        if (GeneralHelper.isInputEmpty(this.binding.inputBill)) {
            this.binding.inputBill.error = "Nomor Tujuan Belun Di Isi!"
            return
        }

        if (this.productSelected == null) {
            Toast.makeText(requireContext(), "Anda belum memilih produk!", Toast.LENGTH_SHORT).show()
            return
        }

        val loading = Loading(requireContext())
        loading.setCancelable(false)
        loading.show()

        val target = GeneralHelper.getInputValue(this.binding.inputBill)
        val wa = Preferences.get(requireContext(), Constant.Key.WHATSAPP) as String
        val session = Preferences.get(requireContext(), Constant.Key.SESSION) as String
        val sku = this.productSelected!!.sku

        var invoiceId = ""
        var isSuccess = false
        CompositeDisposable().add(
            ApiHelper
                .transaction(wa, session, sku, target)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                isSuccess = true
                                val data = body.data as Map<*, *>
                                invoiceId = data["order_id"].toString()
                            } else {
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(requireContext())
                                    return
                                }

                                Toast.makeText(requireContext(), "Oops, Pembelian Gagal! ${body.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error((requireActivity() as AppCompatActivity), e, false, true)
                        loading.dismiss()
                    }

                    override fun onComplete() {
                        loading.dismiss()
                        if (isSuccess) {
                            val bundle = Bundle()
                            bundle.putString(Constant.Key.INVOICE_ID, invoiceId)
                            requireActivity().apply {
                                GeneralHelper.move(this, SuccessTransactionActivity::class.java, bundle, true)
                            }
                        }
                    }

                })
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onProductClicked(productEntity: ProductEntity) {
        //On Product Clicked
        this.productSelected = productEntity

        this.binding.let {
            ViewHelper.visible(it.relativeSelected)
            ViewHelper.gone(it.recyclerView)

            val totalPrice = Formatter.currency(productEntity.price.toLong(), "ID", true)
            it.textPrice.text = totalPrice
            it.textSelectedProduct.text = productEntity.product
            it.textSelectedPrice.text = totalPrice
        }
    }
}