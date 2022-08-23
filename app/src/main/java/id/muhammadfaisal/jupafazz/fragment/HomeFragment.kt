package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import id.muhammadfaisal.jupafazz.adapter.MenuAdapter
import id.muhammadfaisal.jupafazz.databinding.FragmentHomeBinding
import id.muhammadfaisal.jupafazz.dummy.Dummy
import id.muhammadfaisal.jupafazz.utils.Font

class HomeFragment : Fragment() {

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
    }

    private fun initialize() {
        this.imageSlides = ArrayList()

        this.dummyImageSlide()

        this.binding.let {
            it.textNews.isSelected = true

            it.imageSlider.setImageList(this.imageSlides)


            Font.apply {

                //Set Into Semi-Bold Rubik Font
                setInto(
                    (requireActivity() as AppCompatActivity),
                    Font.Rubik.SEMI_BOLD,
                    it.textTitle
                )

                //Set Into Medium Rubik Font
                setInto(
                    (requireActivity() as AppCompatActivity),
                    Font.Rubik.MEDIUM,
                    it.textGreeting,
                    it.textBalance,
                    it.textTitleChoicesMenu,
                    it.textAddMenu
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

                it.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 4)
                it.recyclerView.adapter = MenuAdapter(requireContext(), Dummy.getMenus())
            }
        }
    }

    private fun dummyImageSlide() {
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/04/THUMBNAIL_600x328px-copy-20.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/08/Thumbnail_600x3282.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2022/01/Thumbnail-600x328-10-1.jpg", ScaleTypes.FIT))
        this.imageSlides.add(SlideModel("https://ecs7.tokopedia.net/img/blog/promo/2021/11/600x328-22.jpg", ScaleTypes.FIT))
    }
}