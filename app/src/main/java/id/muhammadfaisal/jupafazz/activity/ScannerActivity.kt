package id.muhammadfaisal.jupafazz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var zXingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.zXingScannerView = ZXingScannerView(this)
        this.setContentView(this.zXingScannerView)

    }

    override fun handleResult(p0: com.google.zxing.?) {
        if (p0 != null) {
            Log.d(ScannerActivity::class.java.simpleName, "Scan Result is ${p0.text}")
            Toast.makeText(this, "Scan Result is ${p0.text}", Toast.LENGTH_SHORT).show()
        }
    }
}