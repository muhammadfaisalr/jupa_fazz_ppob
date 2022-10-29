package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.MutationAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.mutation.MutationResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityMutationBinding
import id.muhammadfaisal.jupafazz.databinding.ItemNonRvHeaderBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class MutationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMutationBinding
    private lateinit var bindingHeader: ItemNonRvHeaderBinding

    private lateinit var mutations: ArrayList<MutationResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMutationBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
        this.getMutation()
    }

    private fun getMutation() {
        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)

        CompositeDisposable().add(
            ApiHelper.getMutation(wa, session).subscribeWith(object: DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        val datas = body.data as ArrayList<*>
                        val gson = Gson()

                        datas.forEachIndexed { index, linkedTreeMap ->
                            val detail = gson.fromJson(gson.toJson(datas[index]), MutationResponse::class.java)
                            this@MutationActivity.mutations.add(detail)

                        }
                    }
                }

                override fun onError(e: Throwable) {
                    BottomSheets.error(this@MutationActivity, e, false, true)
                }

                override fun onComplete() {
                    binding.apply {
                        ViewHelper.gone(this.progressBar)

                        this.recyclerView.adapter = MutationAdapter(this@MutationActivity, mutations)
                    }
                }

            })
        )
    }

    private fun initialize() {
        this.mutations = ArrayList()

        this.bindingHeader = this.binding.header
        this.bindingHeader.apply {
            this.textTitle.text = getString(R.string.mutation)
            this.textSubtitle.text = getString(R.string.mutation_desc)

            ViewHelper.makeClickable(this@MutationActivity, this.imageBack)
        }

        this.binding.apply {
            this.recyclerView.addItemDecoration(DividerItemDecoration(this@MutationActivity, RecyclerView.VERTICAL))
            this.recyclerView.layoutManager = LinearLayoutManager(this@MutationActivity, RecyclerView.VERTICAL, false)
        }
    }

    /*private fun requestData() {
        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)

        CompositeDisposable().add(
            ApiHelper
                .depositList(wa, session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                val datas = body.data as ArrayList<*>
                                val gson = Gson()

                                datas.forEachIndexed { index, linkedTreeMap ->
                                    val detail = gson.fromJson(gson.toJson(datas[index]), MutationResponse::class.java)
                                    this@MutationActivity.mutations.add(detail)

                                }
                            } else {
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(this@MutationActivity)
                                    return
                                }

                                BottomSheets.error(this@MutationActivity, getString(R.string.something_wrong), body.message, false, true)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(this@MutationActivity, e, false, true)
                    }

                    override fun onComplete() {
//                        binding.apply {
//                            ViewHelper.gone(this.progressBar)
//
//                            this.recyclerView.adapter = DepositListAdapter(this@MutationActivity, this@MutationActivity.depositList)
//                        }
                    }

                })
        )
    }*/

    override fun onClick(p0: View?) {
        if (p0 == this.bindingHeader.imageBack) {
            this.back()
        }
    }

    private fun back() {
        this.finish()
    }
}