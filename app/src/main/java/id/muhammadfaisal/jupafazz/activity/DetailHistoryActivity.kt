package id.muhammadfaisal.jupafazz.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import id.muhammadfaisal.jupafazz.databinding.ActivityDetailHistoryBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.Font
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class DetailHistoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailHistoryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
    }

    private fun initialize() {
        this.binding.let {
            Font.setInto(this, Font.Rubik.MEDIUM,
                it.textTitle,
                it.textProductName,
                it.textPaymentMethod,
                it.textTransactionIdTitle,
                it.textAmount,
                it.textDetailOrder,
                it.textDetailTransaction
            )

            Font.setInto(this, Font.Rubik.REGULAR,
                it.textDate,
                it.textUserId,
                it.textStatus,
                it.textPaymentMethod,
                it.textTotalPaidTitle,
                it.textTransactionId
            )
        }

        ViewHelper.makeClickable(this, this.binding.fabShare)
    }


    override fun onClick(p0: View?) {
        if (p0 == this.binding.fabShare) {
            this.share()
        }
    }

    private fun share() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Allow the Storage Permission", Toast.LENGTH_LONG).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 401)
        } else {

            ViewHelper.visible(this.binding.textTrademark)
            ViewHelper.gone(this.binding.linearShare)

            val isExpandTrx = this.binding.expandableDetailTransaction.isExpanded
            val isExpandOrder = this.binding.expandableDetailorder.isExpanded

            this.binding.expandableDetailTransaction.isExpanded = true
            this.binding.expandableDetailorder.isExpanded = true

            val loading = Loading(this)
            loading.setCancelable(false)
            loading.show()

            Handler(Looper.getMainLooper()).postDelayed({
                this.binding.cardReceipt.isDrawingCacheEnabled = true
                val b: Bitmap = this.binding.cardReceipt.drawingCache
                val random = Random().nextInt(100000 - 100 + 1) + 100000
                val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val location = "/jupafazz-receipt-share-${random}.png"
                val path = root.toString() + location
                val imageDir = File(root, location)

                try {
                    b.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(path))
                    Log.d(DetailHistoryActivity::class.java.simpleName, "Success Create Image File!")
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Log.d(DetailHistoryActivity::class.java.simpleName, "Failed to Create Image File! stacktrace : ")
                }

                val finalPath = FileInputStream(File(path))
                finalPath.close()

                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.type = "image/png"
                i.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageDir.absolutePath))
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(Intent.createChooser(i, "Bagikan"))

                loading.dismiss()
            }, 1000L)
        }
    }

    override fun onResume() {
        super.onResume()

        ViewHelper.gone(this.binding.textTrademark)
        ViewHelper.visible(this.binding.linearShare)
    }
}