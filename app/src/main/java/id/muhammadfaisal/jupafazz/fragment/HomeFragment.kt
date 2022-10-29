package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.util.StringUtil
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import id.muhammadfaisal.jupafazz.activity.MethodDepositActivity
import id.muhammadfaisal.jupafazz.adapter.MenuAdapter
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentHomeBinding
import id.muhammadfaisal.jupafazz.dummy.Dummy
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.apache.commons.lang3.StringUtils
import retrofit2.Response

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var imageSlides : ArrayList<SlideModel>

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
                    it.textTitleBalance
                )

                ViewHelper.makeClickable(this@HomeFragment, it.layoutTopUp)
            }
        }
    }

    private fun imageSlide() {
        CompositeDisposable().add(
            ApiHelper.getBanners().subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        if (body.isSuccess){
                            try {
                                (body.data as java.util.ArrayList<*>).forEachIndexed{ index, data ->
                                    this@HomeFragment.imageSlides.add(SlideModel(body.data[index].toString(), ScaleTypes.FIT))
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Gagal Menampilkan Banner [${body.message}]", Toast.LENGTH_SHORT).show()
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
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/04/THUMBNAIL_600x328px-copy-20.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/08/Thumbnail_600x3282.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/01/Thumbnail-600x328-10-1.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2021/11/600x328-22.jpg", ScaleTypes.FIT))
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.layoutTopUp) {
            GeneralHelper.move(requireContext(), MethodDepositActivity::class.java, false)
        }
    }
}