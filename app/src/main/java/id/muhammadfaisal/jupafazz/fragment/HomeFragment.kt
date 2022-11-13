package id.muhammadfaisal.jupafazz.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.util.StringUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.BannerActivity
import id.muhammadfaisal.jupafazz.activity.MainActivity
import id.muhammadfaisal.jupafazz.activity.MethodDepositActivity
import id.muhammadfaisal.jupafazz.activity.ScannerActivity
import id.muhammadfaisal.jupafazz.adapter.MenuAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentHomeBinding
import id.muhammadfaisal.jupafazz.dummy.Dummy
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.service.VersioningService
import id.muhammadfaisal.jupafazz.utils.*
import id.muhammadfaisal.jupafazz.utils.Formatter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.apache.commons.lang3.StringUtils
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var zxingScannerView: ZXingScannerView

    private lateinit var imageSlides: ArrayList<SlideModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHomeBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
        this.data()
        this.category()
    }

    private fun data() {
        val balance = Preferences.get(requireContext(), Constant.Key.BALANCE) as String
        val name = Preferences.get(requireContext(), Constant.Key.NAME) as String

        this.binding.apply {
            this.textBalance.text = Formatter.currency(balance.toLong(), "ID", true)
            this.textGreeting.text = "Hi, ${StringUtils.capitalize(name)}"
        }
    }


    private fun initialize() {
        this.imageSlides = ArrayList()
        this.zxingScannerView = ZXingScannerView(requireContext())

        this.imageSlide()

        this.binding.let {
            it.textNews.isSelected = true

            Font.apply {

                //Set Into Medium Rubik Font
                setInto(
                    (requireActivity() as AppCompatActivity),
                    Font.Rubik.MEDIUM,
                    it.textGreeting,
                    it.textBalance,
                    it.textTitleChoicesMenu,
                )

                //Set Into Regular Rubik Font
                setInto(
                    (requireActivity() as AppCompatActivity),
                    Font.Rubik.REGULAR,
                    it.textTopUp,
                    it.textSend,
                    it.textScan,
                    it.textNews,
                    it.textTitleBalance,
                    it.buttonSpanduk
                )

                ViewHelper.makeClickable(
                    this@HomeFragment,
                    it.layoutTopUp,
                    it.layoutSend,
                    it.buttonSpanduk
                )

                it.swipe.setOnRefreshListener(this@HomeFragment)
            }
        }
    }

    private fun imageSlide() {
        CompositeDisposable().add(
            ApiHelper.getBanners()
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                try {
                                    (body.data as java.util.ArrayList<*>).forEachIndexed { index, data ->
                                        this@HomeFragment.imageSlides.add(
                                            SlideModel(
                                                body.data[index].toString(),
                                                ScaleTypes.FIT
                                            )
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Gagal Menampilkan Banner [${body.message}]",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error((requireActivity() as AppCompatActivity), e, false, true)
                    }

                    override fun onComplete() {
                        this@HomeFragment.binding.apply {
                            this.imageSlider.setImageList(imageSlides)
                        }
                    }

                })
        )
    }

    private fun category() {
        val productDao = DatabaseHelper.productDao(requireContext())
        this.binding.let {
            it.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 4)
            it.recyclerView.adapter = MenuAdapter(requireContext(), productDao.getCategories())
        }
    }

    private fun dummyImageSlide() {
        this.imageSlides.add(
            SlideModel(
                "https://ecs7.tokopedia.net/img/blog/promo/2022/04/THUMBNAIL_600x328px-copy-20.jpg",
                ScaleTypes.FIT
            )
        )
        this.imageSlides.add(
            SlideModel(
                "https://ecs7.tokopedia.net/img/blog/promo/2022/08/Thumbnail_600x3282.jpg",
                ScaleTypes.FIT
            )
        )
        this.imageSlides.add(
            SlideModel(
                "https://ecs7.tokopedia.net/img/blog/promo/2022/01/Thumbnail-600x328-10-1.jpg",
                ScaleTypes.FIT
            )
        )
        this.imageSlides.add(
            SlideModel(
                "https://ecs7.tokopedia.net/img/blog/promo/2021/11/600x328-22.jpg",
                ScaleTypes.FIT
            )
        )
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.layoutTopUp) {
            GeneralHelper.move(requireContext(), MethodDepositActivity::class.java, false)
        } else if (p0 == this.binding.buttonSpanduk) {
            GeneralHelper.move(requireContext(), BannerActivity::class.java, false)
        } else if (p0 == this.binding.layoutSend) {
            BottomSheets.error(
                requireActivity() as AppCompatActivity,
                getString(R.string.something_wrong),
                "Mohon maaf, fitur sedang dalam pengembangan tahap akhir.",
                false,
                true
            )
//            GeneralHelper.move(requireContext(), ScannerActivity::class.java, false)
        }
    }

    override fun onResume() {
        super.onResume()
        this.balance()
    }

    private fun balance() {
        val wa = Preferences.getWhatsApp(requireContext())
        val session = Preferences.getSession(requireContext())
        var balance = ""

        CompositeDisposable().add(
            ApiHelper
                .userDetail(wa, session)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                val data = body.data as Map<*, *>
                                this@HomeFragment.apply {
                                    try {
                                        balance = data["balance"]!! as String
                                        binding.textBalance.text = Formatter.currency(balance.toLong(), "ID", true)
                                        Log.d(HomeFragment::class.java.simpleName, "BERHASIL UPDATE SALDO")
                                    } catch (e: Exception) {

                                    }
                                }
                            } else {
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    GeneralHelper.sessionExpired(requireContext())
                                    return
                                }

                                BottomSheets.error(
                                    (requireActivity() as AppCompatActivity),
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
                            (requireActivity() as AppCompatActivity),
                            getString(R.string.something_wrong),
                            e.message!!,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        try {
                            Preferences.save(requireContext(), Constant.Key.BALANCE, balance)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        this@HomeFragment.binding.apply {
                            this.swipe.isRefreshing = false
                        }
                    }
                })
        )
    }

    override fun onRefresh() {
        this.balance()
    }
}