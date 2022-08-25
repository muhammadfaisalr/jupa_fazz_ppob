package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.FragmentErrorBottomSheetDialogBinding

class ErrorBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentErrorBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentErrorBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}