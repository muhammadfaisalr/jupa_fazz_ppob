package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.Result
import id.muhammadfaisal.jupafazz.databinding.ActivityScannerBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var binding: ActivityScannerBinding

    private lateinit var zxingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityScannerBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.zxingScannerView = ZXingScannerView(this)
        this.zxingScannerView.setAutoFocus(true)
        this.zxingScannerView.setResultHandler(this)

        this.binding.frameCamera.addView(this.zxingScannerView)
    }

    override fun onStart() {
        super.onStart()

        this.zxingScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        this.zxingScannerView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        this.zxingScannerView.startCamera()
    }

    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.SCAN_RESULT, p0.toString())
            GeneralHelper.move(this, TransferConfirmationActivity::class.java, bundle, false)
        }
    }
}