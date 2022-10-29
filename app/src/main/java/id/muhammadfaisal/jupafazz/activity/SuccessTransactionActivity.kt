package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.transaction.DetailTransactionResponse
import id.muhammadfaisal.jupafazz.databinding.ActivitySuccessTransactionBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class SuccessTransactionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySuccessTransactionBinding

    private lateinit var invoiceId: String
    private lateinit var detailTransactionResponse: DetailTransactionResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySuccessTransactionBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.data()
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.invoiceId = bundle.getString(Constant.Key.INVOICE_ID, "")
        }
    }

    private fun initialize() {
        GeneralHelper.playNotification(this)

        this.binding.let {
            Font.setInto(
                this,
                Font.Rubik.REGULAR,
                it.textInvoiceIdTitle,
                it.textDateTitle,
                it.textAmountTitle,
                it.textTargetTitle,
                it.textProductNameTitle,
            )

            Font.setInto(
                this,
                Font.Rubik.MEDIUM,
                it.textInvoiceId,
                it.textDate,
                it.textAmount,
                it.textProductName,
                it.textTarget,
                it.textStatus,
                it.buttonBack
            )

            Font.setInto(
                this,
                Font.Rubik.SEMI_BOLD,
                it.textSuccessTitle
            )

            ViewHelper.makeClickable(this, it.buttonBack, it.buttonDetail)
        }
    }

    private fun data() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)
        CompositeDisposable().add(
            ApiHelper
                .detailTransaction(wa, session, invoiceId)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()!!

                        if (body.isSuccess) {
                            val data = body.data as Map<*, *>
                            val gson = Gson()
                            val detail = gson.fromJson(
                                gson.toJson(data["detail"]),
                                DetailTransactionResponse::class.java
                            )

                            detailTransactionResponse = detail
                        } else {
                            Toast.makeText(
                                this@SuccessTransactionActivity,
                                "Terjadi Kesalahan ${body.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(this@SuccessTransactionActivity, e, false, true)
                    }

                    override fun onComplete() {
                        loading.dismiss()
                        this@SuccessTransactionActivity.apply {
                            this.binding.apply {
                                this.textInvoiceId.text = invoiceId
                                this.textProductName.text = detailTransactionResponse.product
                                this.textStatus.text = detailTransactionResponse.sn
                                this.textTarget.text = detailTransactionResponse.target
                                this.textDate.text = detailTransactionResponse.createDate
                                this.textAmount.text = Formatter.currency(detailTransactionResponse.price.toLong(), "ID", true)
                            }
                        }
                    }

                })
        )
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonBack) {
            this.finish()
        } else if (p0 == this.binding.buttonDetail) {
            this.detail()
        }
    }

    private fun detail() {
        val bundle = Bundle()
        bundle.putSerializable(Constant.Key.DETAIL_TRX, this.detailTransactionResponse)
        bundle.putSerializable(Constant.Key.INVOICE_ID, this.invoiceId)
        GeneralHelper.move(this, DetailHistoryActivity::class.java, bundle, true)
    }
}