package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentErrorBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import org.apache.commons.lang3.StringUtils

class ErrorBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentErrorBottomSheetDialogBinding

    private lateinit var title: String
    private lateinit var desc: String

    private var isShowReason: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentErrorBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.extract()
        this.initialize()
    }

    private fun extract() {
        val bundle = this.arguments

        if (bundle != null) {
            this.title = bundle.getString(Constant.Key.TITLE, "")
            this.desc = bundle.getString(Constant.Key.DESCRIPTION, "")
            this.isShowReason = bundle.getBoolean(Constant.Key.IS_SHOW_REASON)
        }
    }

    private fun initialize() {
        this.binding.apply {
            Font.setInto(
                (requireActivity() as AppCompatActivity),
                Font.Rubik.MEDIUM,
                this.textTitle,
                this.buttonOk
            )
            Font.setInto(
                (requireActivity() as AppCompatActivity),
                Font.Rubik.REGULAR,
                this.textDesc,
            )

            this.textTitle.text = title
            this.textDesc.text = StringUtils.capitalize(desc)

            if (!isShowReason) {
            }

            ViewHelper.makeClickable(this@ErrorBottomSheetDialogFragment, this.buttonOk)
        }
    }

    override fun onClick(view: View?) {
        if (view == this.binding.buttonOk) {
            this.dismiss()
        }
    }

    private fun reason() {

    }
}