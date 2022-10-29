package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositRequestResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityRequestDepositBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter
import org.apache.commons.lang3.StringUtils

class RequestDepositActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRequestDepositBinding

    private lateinit var deposit: DepositMethodResponse
    private lateinit var depositRequestResponse: DepositRequestResponse

    private lateinit var amount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityRequestDepositBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.data()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.amount = bundle.getString(Constant.Key.AMOUNT, "")
            this.deposit = bundle.getSerializable(Constant.Key.DEPOSIT_OBJ) as DepositMethodResponse
            this.depositRequestResponse = bundle.getSerializable(Constant.Key.DEPOSIT_REQ_OBJ) as DepositRequestResponse
        }
    }

    private fun initialize() {
        this.binding.apply {
            Font.setInto(
                this@RequestDepositActivity,
                Font.Rubik.MEDIUM,
                this.textAmount,
                this.textInvoiceId,
                this.textData,
                this.buttonDone,
                this.textNeedHelp
            )

            Font.setInto(
                this@RequestDepositActivity,
                Font.Rubik.REGULAR,
                this.textCopy,
                this.textCopy2,
                this.textAmountTitle,
                this.textDesc
            )

            Font.setInto(
                this@RequestDepositActivity,
                Font.Rubik.SEMI_BOLD,
                this.textTitle,
            )

            ViewHelper.makeClickable(this@RequestDepositActivity, this.buttonDone, this.imageBack, this.buttonHelp)
        }
    }

    private fun data() {
        this.binding.let {
            it.textInvoiceId.text = this.depositRequestResponse.depositId

            //it.textDesc.text = this.depositRequestResponse.depositId
            it.textData.text = this.depositRequestResponse.payment.data
            it.textType.text = StringUtils.capitalize(this.depositRequestResponse.payment.type)

            it.textTitle.text = it.textTitle.text.toString() + " [${depositRequestResponse.method}]"
            it.textAmount.text = Formatter.currency(this.amount.toLong(), "ID")

            if (this.depositRequestResponse.payment.type == "Link Invoice") {
                it.textCopy.text = getString(R.string.open_invoice)
                val data = depositRequestResponse.payment.data
                val span = SpannableString(data)
                span.setSpan(UnderlineSpan(), 0, data.length, 0)
                it.textData.text = span
            }

            Glide
                .with(this)
                .load(this.deposit.image)
                .error(com.denzcoskun.imageslider.R.drawable.error)
                .into(it.image)

            ViewHelper.makeClickable(this, it.textCopy, it.textCopy2, it.buttonHelp)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonDone) {
            Toast.makeText(this, "Silahkan cek secara berkala saldo anda. Lihat mutasi di Akun saya > Mutasi saldo", Toast.LENGTH_SHORT).show()
            GeneralHelper.move(this, MainActivity::class.java, true)
        } else if (p0 == this.binding.textCopy) {
            this.copyPayment()
        } else if (p0 == this.binding.imageBack) {
            this.finish()
        } else if (p0 == this.binding.textCopy2) {
            GeneralHelper.copy(this.binding.textAmount.text.toString(), this)
        } else if (p0 == this.binding.buttonHelp) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.HELP)
            GeneralHelper.move(this, WebViewActivity::class.java, bundle, false)
        }
    }

    private fun copyPayment() {
        if (this.binding.textCopy.text.contains("Invoice")) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, this.depositRequestResponse.payment.data)
            GeneralHelper.move(this, WebViewActivity::class.java, bundle,  false)
        } else {
            GeneralHelper.copy(this.depositRequestResponse.payment.data, this)
        }
    }
}