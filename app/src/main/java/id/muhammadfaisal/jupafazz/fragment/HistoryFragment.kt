package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.HistoryAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.mutation.MutationResponse
import id.muhammadfaisal.jupafazz.api.model.transaction.TransactionListResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentHistoryBinding
import id.muhammadfaisal.jupafazz.dummy.Dummy
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var histories: ArrayList<TransactionListResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this.binding = FragmentHistoryBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
        this.requestData()
    }

    private fun initialize() {
        this.histories = ArrayList()
        this.binding.let {
            it.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            Font.setInto((this.requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, it.textTitle)
        }
    }

    private fun requestData() {
        val wa = Preferences.getWhatsApp(requireContext())
        val session = Preferences.getSession(requireContext())

        CompositeDisposable().add(
            ApiHelper.getHistory(wa, session).subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        if (body.isSuccess) {
                            val datas = body.data as ArrayList<*>
                            val gson = Gson()

                            datas.forEachIndexed { index, linkedTreeMap ->
                                val detail = gson.fromJson(gson.toJson(datas[index]), TransactionListResponse::class.java)
                                this@HistoryFragment.histories.add(detail)

                            }
                        } else {
                            if (GeneralHelper.isSessionExpire(body.message)) {
                                GeneralHelper.sessionExpired(requireContext())
                                return
                            }

                            BottomSheets.error((requireActivity() as AppCompatActivity), getString(R.string.something_wrong), body.message, false, true)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    BottomSheets.error((requireActivity() as AppCompatActivity), e, false, true)
                }

                override fun onComplete() {
                    ViewHelper.gone(this@HistoryFragment.binding.linearProgress)
                    this@HistoryFragment.binding.apply {
                        this.recyclerView.adapter = HistoryAdapter(requireContext(), histories)
                    }
                }

            })
        )
    }
}