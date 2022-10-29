package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositListResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityDetailDepositBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class DetailDepositActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailDepositBinding
    private lateinit var deposit: DepositListResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailDepositBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.data()
    }

    private fun data() {
        this.binding.apply {
            this.textTitle.text = "Isi Saldo [${deposit.method}]"
            this.textInvoiceId.text = deposit.depositId
            this.textTotalDeposit.text = Formatter.currency(deposit.quantity.toLong(), "ID", true)
            this.textTotalBalance.text = Formatter.currency(deposit.balance.toLong(), "ID", true)
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.deposit = bundle.getSerializable(Constant.Key.DEPOSIT_OBJ) as DepositListResponse
        }
    }

    private fun initialize() {
        this.binding.apply {
            Font.setInto(
                this@DetailDepositActivity,
                Font.Rubik.MEDIUM,
                this.textTotalDeposit,
                this.textInvoiceId,
                this.textTotalBalance,
                this.textNeedHelp
            )

            Font.setInto(
                this@DetailDepositActivity,
                Font.Rubik.REGULAR,
                this.textHelpTotalBalance,
                this.textAmountTitle,
                this.textDesc
            )

            Font.setInto(
                this@DetailDepositActivity,
                Font.Rubik.SEMI_BOLD,
                this.textTitle,
            )

            Glide.with(this@DetailDepositActivity).load(deposit.image).error(com.denzcoskun.imageslider.R.drawable.error).into(this.image)

            ViewHelper.makeClickable(this@DetailDepositActivity, this.imageBack, this.buttonHelp)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageBack) {
            this.finish()
        } else if (p0 == this.binding.buttonHelp) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.HELP)
            GeneralHelper.move(this, WebViewActivity::class.java, bundle, false)
        }
    }
}