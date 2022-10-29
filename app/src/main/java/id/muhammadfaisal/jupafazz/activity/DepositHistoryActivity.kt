package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.DepositListAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositListResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityDepositHistoryBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class DepositHistoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDepositHistoryBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var deposits: ArrayList<DepositListResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDepositHistoryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
        this.data()
    }

    private fun initialize() {
        this.deposits = ArrayList()
        this.bindingHeader = this.binding.header

        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.deposit_history)

            ViewHelper.gone(this.textSubtitle)
            ViewHelper.makeClickable(this@DepositHistoryActivity, this.imageBack)
        }

        this.binding.apply {
            this.recyclerView.layoutManager = LinearLayoutManager(this@DepositHistoryActivity, RecyclerView.VERTICAL, false)
            this.recyclerView.addItemDecoration(DividerItemDecoration(this@DepositHistoryActivity, RecyclerView.VERTICAL))
        }
    }

    private fun data() {
        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)

        CompositeDisposable().add(
            ApiHelper.depositList(wa, session).subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        if (body.isSuccess) {
                            val datas = body.data as ArrayList<*>
                            val gson = Gson()

                            datas.forEachIndexed { index, linkedTreeMap ->
                                val detail = gson.fromJson(gson.toJson(datas[index]), DepositListResponse::class.java)
                                this@DepositHistoryActivity.deposits.add(detail)

                            }
                        } else {
                            if (GeneralHelper.isSessionExpire(body.message)) {
                                GeneralHelper.sessionExpired(this@DepositHistoryActivity)
                                return
                            }

                            BottomSheets.error(this@DepositHistoryActivity, getString(R.string.something_wrong), body.message, false, true)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    BottomSheets.error(this@DepositHistoryActivity, e, false, true)
                }

                override fun onComplete() {
                    ViewHelper.gone(binding.progressBar)
                    this@DepositHistoryActivity.binding.apply {
                        this.recyclerView.adapter = DepositListAdapter(this@DepositHistoryActivity, this@DepositHistoryActivity.deposits)
                    }
                }

            })
        )
    }


    override fun onClick(p0: View?) {
        if (p0 == this.bindingHeader.imageBack) {
            this.finish()
        }
    }
}