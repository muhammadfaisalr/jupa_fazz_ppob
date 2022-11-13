package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.NotificationAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.api.model.notification.NotificationResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentNotificationBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding

    private lateinit var notifications: ArrayList<NotificationResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentNotificationBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
        this.data()
    }

    private fun initialize() {
        this.notifications = ArrayList()
        this.binding.apply {
            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, this.textTitle)
            this.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun data() {

        val wa = Preferences.getWhatsApp(requireContext())
        val session = Preferences.getSession(requireContext())

        CompositeDisposable().add(
            ApiHelper.notifications(wa, session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (!body.isSuccess) {
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(requireContext())
                                    return
                                }

                                BottomSheets.error(
                                    (requireActivity() as AppCompatActivity),
                                    getString(R.string.something_wrong),
                                    body.message,
                                    false,
                                    true
                                )

                                return
                            }

                            //if body return success, go here
                            val datas = body.data as ArrayList<*>
                            val gson = Gson()

                            datas.forEachIndexed { index, linkedTreeMap ->
                                val detail = gson.fromJson(gson.toJson(datas[index]), NotificationResponse::class.java)
                                this@NotificationFragment.notifications.add(detail)

                            }

                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            (requireActivity() as AppCompatActivity),
                            e,
                            false,
                            true
                        )
                    }

                    override fun onComplete() {
                        binding.apply {
                            ViewHelper.gone(this.linearProgress)
                            this.recyclerView.adapter = NotificationAdapter(requireContext(), notifications)
                        }
                    }
                })
        )
    }
}