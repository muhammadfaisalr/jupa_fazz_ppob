package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.BannerAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.banner.BannerResponse
import id.muhammadfaisal.jupafazz.api.model.mutation.MutationResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityBannerBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class BannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBannerBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var outletBanners: ArrayList<BannerResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityBannerBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
        this.requestData()
    }

    private fun requestData() {
        var isSuccess = false
        CompositeDisposable().add(
            ApiHelper.getOutletBanners().subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        if (body.isSuccess) {
                            isSuccess = true
                            val datas = body.data as ArrayList<*>
                            val gson = Gson()

                            datas.forEachIndexed { index, linkedTreeMap ->
                                val detail = gson.fromJson(gson.toJson(datas[index]), BannerResponse::class.java)
                                this@BannerActivity.outletBanners.add(detail)

                            }
                        } else {
                            if (GeneralHelper.isSessionExpire(body.message)) {
                                GeneralHelper.sessionExpired(this@BannerActivity)
                                return
                            }

                            BottomSheets.error(this@BannerActivity, getString(R.string.something_wrong), body.message, false, true)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    BottomSheets.error(this@BannerActivity, e, false, true)
                }

                override fun onComplete() {
                    ViewHelper.gone(binding.progressBar)

                    if (isSuccess) {
                        this@BannerActivity.binding.apply {
                            this.recyclerView.adapter = BannerAdapter(this@BannerActivity, this@BannerActivity.outletBanners)
                        }
                    }
                }

            })
        )
    }

    private fun initialize() {
        this.outletBanners = ArrayList()

        this.bindingHeader = this.binding.layoutHeader
        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.banner)
            ViewHelper.gone(this.textSubtitle)
        }

        this.binding.apply {
            this.recyclerView.layoutManager = LinearLayoutManager(this@BannerActivity, RecyclerView.VERTICAL, false)
            this.recyclerView.addItemDecoration(DividerItemDecoration(this@BannerActivity, RecyclerView.VERTICAL))
        }
    }
}