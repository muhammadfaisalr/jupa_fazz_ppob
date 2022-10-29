package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.MethodDepositAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.api.model.transaction.DetailTransactionResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityMethodDepositBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class MethodDepositActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMethodDepositBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var depositMethods: ArrayList<DepositMethodResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMethodDepositBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
        this.request()
    }

    private fun request() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        if (this.depositMethods.isNotEmpty()) {
            this.depositMethods.clear()
        }

        CompositeDisposable().add(
            ApiHelper
                .depositMethod(Preferences.getWhatsApp(this), Preferences.getSession(this))
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                val datas = body.data as ArrayList<*>
                                val gson = Gson()

                                datas.forEachIndexed { index, linkedTreeMap ->
                                    val detail = gson.fromJson(gson.toJson(datas[index]), DepositMethodResponse::class.java)
                                    this@MethodDepositActivity.depositMethods.add(detail)

                                }
                            } else {
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(this@MethodDepositActivity)
                                    return
                                }

                                BottomSheets.error(
                                    this@MethodDepositActivity,
                                    getString(R.string.something_wrong),
                                    body.message,
                                    isShowReason = false,
                                    isCancelable = true
                                )
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            this@MethodDepositActivity,
                            e,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        loading.dismiss()
                        this@MethodDepositActivity.binding.apply {
                            this.recyclerView.adapter = MethodDepositAdapter(
                                this@MethodDepositActivity,
                                this@MethodDepositActivity.depositMethods
                            )
                        }
                    }

                })
        )
    }

    private fun initialize() {
        this.depositMethods = ArrayList()

        this.bindingHeader = this.binding.layout
        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.top_up)
            this.textSubtitle.text = getString(R.string.top_up_desc)

            Font.setInto(this@MethodDepositActivity, Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto(this@MethodDepositActivity, Font.Rubik.REGULAR, this.textSubtitle)
        }

        this.binding.apply {
            this.recyclerView.layoutManager =
                LinearLayoutManager(this@MethodDepositActivity, RecyclerView.VERTICAL, false)
            this.recyclerView.addItemDecoration(DividerItemDecoration(this@MethodDepositActivity, RecyclerView.VERTICAL))
        }

        ViewHelper.makeClickable(this, this.bindingHeader.imageBack, this.binding.exfabHistory)
    }

    override fun onClick(p0: View?) {
        if (p0 == this.bindingHeader.imageBack) {
            finish()
        } else if (p0 == this.binding.exfabHistory) {
            GeneralHelper.move(this, DepositHistoryActivity::class.java, false)
        }
    }
}