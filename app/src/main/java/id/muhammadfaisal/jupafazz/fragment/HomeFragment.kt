package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.smarteist.autoimageslider.SliderViewAdapter
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentHomeBinding
import id.muhammadfaisal.jupafazz.utils.Font

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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
        this.binding.let {
            it.textNews.isSelected = true
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
                    it.textBalance
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
            }
        }
    }
}