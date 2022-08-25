package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentAccountBinding
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Font.Companion.setInto

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAccountBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
    }

    private fun initialize() {
        requireActivity().apply {
            (this as AppCompatActivity)
            Font.let {
                setInto(
                    this,
                    Font.Rubik.REGULAR,
                    binding.textTitleAccountSetting,
                    binding.textTitleOtherMenu
                )

                setInto(
                    this,
                    Font.Rubik.MEDIUM,
                    binding.textAccountInformation,
                    binding.textTopUpBalance,
                    binding.textMutationBalance,
                    binding.textChangePassword,
                    binding.textSK,
                    binding.textPriceList,
                    binding.textPrivacyPolicy,
                    binding.textFaq,
                    binding.textHelp
                )
            }
        }
    }
}