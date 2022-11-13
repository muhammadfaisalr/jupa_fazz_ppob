package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityTransferConfirmationBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.fragment.HomeFragment
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class TransferConfirmationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTransferConfirmationBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var scanResult: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityTransferConfirmationBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.data()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.scanResult = bundle.getString(Constant.Key.SCAN_RESULT, "")
        }
    }

    private fun initialize() {
        this.bindingHeader = this.binding.layoutHeader
        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.send_money_full)
            this.textSubtitle.text = getString(R.string.desc_send_money)

            Font.setInto(this@TransferConfirmationActivity, Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto(this@TransferConfirmationActivity, Font.Rubik.REGULAR, this.textTitle)
        }

        this.binding.apply {
            ViewHelper.gone(this.textSendTo)

            this.buttonProcess.isEnabled = false

            Font.setInto(
                this@TransferConfirmationActivity,
                Font.Rubik.REGULAR,
                this.textSendToTitle,
                this.textAmountTitle
            )

            Font.setInto(
                this@TransferConfirmationActivity,
                Font.Rubik.MEDIUM,
                this.textSendTo,
                this.textPrefixRp,
                this.inputAmount
            )
        }

        ViewHelper.makeClickable(this, this.bindingHeader.imageBack, this.binding.buttonBack)
    }

    private fun data() {
        val whatsapp = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)

        CompositeDisposable().add(
            ApiHelper.transferInquiry(whatsapp, session, scanResult)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            val data = body.data

                            this@TransferConfirmationActivity.binding.apply {
                                this.textSendTo.text = ""
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(this@TransferConfirmationActivity, e, false, true)
                    }

                    override fun onComplete() {
                        this@TransferConfirmationActivity.binding.apply {
                            ViewHelper.visible(this.textSendTo)
                            ViewHelper.gone(this.linearProgress)

                            this.buttonProcess.isEnabled = true
                        }
                    }
                })
        )
    }


    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonBack || p0 == this.bindingHeader.imageBack) {
            finish()
        }
    }
}