package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentDetailProductBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter
import org.apache.commons.lang3.StringUtils

class DetailProductBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailProductBottomSheetDialogBinding

    private lateinit var productEntity: ProductEntity

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
    }

    private fun extract() {
        val bundle = this.requireArguments()
        this.productEntity = bundle.getSerializable(Constant.Key.PRODUCT_ENT) as ProductEntity
    }

    private fun initialize() {
        this.binding.let {
            val activity = (requireActivity() as AppCompatActivity)

            Font.setInto(activity, Font.Rubik.MEDIUM, it.textTitle, it.textPrice, it.buttonBuy)
            Font.setInto(activity, Font.Rubik.REGULAR, it.textNote, it.inputBill, it.textTitlePrice)

            this.configHint()

            it.textTitle.text = this.productEntity.product
            it.textNote.text = StringUtils.capitalize(this.productEntity.note)
            it.textPrice.text = Formatter.currency(this.productEntity.price.toLong(), "ID", true)

            ViewHelper.makeClickable(this, it.imageClose)
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
        }
    }
}