package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.muhammadfaisal.jupafazz.databinding.FragmentQrBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences


class QrBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentQrBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentQrBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
        this.setQr()
    }

    private fun setQr() {
        val mWriter = MultiFormatWriter()
        val whatsapp = Preferences.getWhatsApp(requireContext())
        try {
            val mMatrix = mWriter.encode(whatsapp, BarcodeFormat.QR_CODE, 400, 400)
            val mEncoder = BarcodeEncoder()
            val mBitmap = mEncoder.createBitmap(mMatrix)
            this.binding.image.setImageBitmap(mBitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun initialize() {
        this.binding.apply {
            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, this.textTitle)
            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.REGULAR, this.textSubtitle)
        }
    }
}