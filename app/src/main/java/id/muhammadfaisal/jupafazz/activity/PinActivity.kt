package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityPinBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class PinActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPinBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var processTo: String
    private lateinit var wa: String
    private lateinit var session: String

    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityPinBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
    }

    private fun extract() {
        this.bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.processTo = bundle!!.getString(Constant.Key.PROCESS_TO, "")

            if (this.processTo == "") {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initialize() {
        this.bindingHeader = this.binding.layoutHeader
        this.wa = Preferences.getWhatsApp(this)
        this.session = Preferences.getSession(this)

        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.input_pin)
            this.textSubtitle.text = getString(R.string.input_pin_desc)

            Font.setInto(this@PinActivity, Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto(this@PinActivity, Font.Rubik.REGULAR, this.textSubtitle)

            ViewHelper.makeClickable(this@PinActivity, this.imageBack)
        }

        this.binding.apply {
            ViewHelper.makeClickable(this@PinActivity, this.buttonVerification)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.bindingHeader.imageBack) {
            finish()
        } else if (p0 == this.binding.buttonVerification) {
            this.process()
        }
    }

    private fun process() {
        if (this.processTo == Constant.ProcessTo.PURCHASE_PRODUCT) {
            this.purchaseProduct()
        }
    }

    private fun purchaseProduct() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        var invoiceId = ""
        var isSuccess = false

        var sku = ""
        var target = ""
        var pin = this.binding.otpView.otp!!

        if (this.bundle != null) {
            val bundle = bundle!!
            sku = bundle.getString(Constant.Key.SKU, "")
            target = bundle.getString(Constant.Key.TARGET, "")
        }

        if (sku == "" || target == "") {
            Toast.makeText(this, "Terjadi Kesalahan, Silahkan Coba Kembali!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        if (pin == "" || pin.length < 6) {
            Toast.makeText(this, "Harap masukan pin", Toast.LENGTH_SHORT).show()
            return
        }

        CompositeDisposable().add(
            ApiHelper
                .transaction(wa, session, sku, pin, target)
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
                                    GeneralHelper.sessionExpired(this@PinActivity)
                                    return
                                }

                                Toast.makeText(this@PinActivity, "Oops, Pembelian Gagal! ${body.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error((this@PinActivity as AppCompatActivity), e, false, true)
                        loading.dismiss()
                    }

                    override fun onComplete() {
                        loading.dismiss()
                        if (isSuccess) {
                            val bundle = Bundle()
                            bundle.putString(Constant.Key.INVOICE_ID, invoiceId)
                            this@PinActivity.apply {
                                GeneralHelper.move(this, SuccessTransactionActivity::class.java, bundle, true)
                            }
                        }
                    }

                })
        )

    }
}