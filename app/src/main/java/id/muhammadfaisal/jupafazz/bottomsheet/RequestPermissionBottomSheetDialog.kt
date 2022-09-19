package id.muhammadfaisal.jupafazz.bottomsheet

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentRequestPermissionBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import kotlin.random.Random

class RequestPermissionBottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentRequestPermissionBottomSheetDialogBinding

    private lateinit var permissions: Array<out String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentRequestPermissionBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.extract()
        this.initialize()
    }

    private fun request() {
        ActivityCompat.requestPermissions(requireActivity(), this.permissions, 101)
        this.dismiss()
    }

    private fun extract() {
        if (this.arguments != null) {
            this.permissions = this.requireArguments().getStringArray(Constant.Key.PERMISSIONS)!!
        }
    }

    private fun initialize() {
        this.binding.apply {
             Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.REGULAR, this.textDesc)

            ViewHelper.makeClickable(this@RequestPermissionBottomSheetDialog, this.buttonGrant)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonGrant) {
            this.request()
        }
    }
}