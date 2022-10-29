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
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.transaction.DetailTransactionResponse
import id.muhammadfaisal.jupafazz.databinding.ActivityDetailHistoryBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class DetailHistoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailHistoryBinding

    private var detail: DetailTransactionResponse? = null
    private lateinit var invoiceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailHistoryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()

        if (this.detail != null) {
            this.data()
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.detail = bundle.getSerializable(Constant.Key.DETAIL_TRX) as DetailTransactionResponse?
            this.invoiceId = bundle.getString(Constant.Key.INVOICE_ID, "")

            if (this.detail == null) {
                this.getDetail()
            }
        }
    }

    private fun getDetail() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val wa = Preferences.getWhatsApp(this)
        val session = Preferences.getSession(this)
        CompositeDisposable().add(
            ApiHelper
                .detailTransaction(wa, session, invoiceId)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()!!

                        if (body.isSuccess) {
                            val data = body.data as Map<*, *>
                            val gson = Gson()
                            val detail = gson.fromJson(
                                gson.toJson(data["detail"]),
                                DetailTransactionResponse::class.java
                            )

                            this@DetailHistoryActivity.detail = detail
                        } else {
                            Toast.makeText(
                                this@DetailHistoryActivity,
                                "Terjadi Kesalahan ${body.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(this@DetailHistoryActivity, e, false, true)
                    }

                    override fun onComplete() {
                        loading.dismiss()
                        this@DetailHistoryActivity.data()
                    }

                })
        )
    }


    private fun initialize() {
        this.binding.let {
            Font.setInto(this, Font.Rubik.MEDIUM,
                it.textTitle,
                it.textProductName,
                it.textTransactionIdTitle,
                it.textAmount,
                it.textDetailOrder,
                it.textDetailTransaction,
                it.textStatus,
                it.textTarget
            )

            Font.setInto(this, Font.Rubik.REGULAR,
                it.textDate,
                it.textUser,
                it.textStatusTitle,
                it.textTargetTitle,
                it.textTotalPaidTitle,
                it.textTransactionId,
                it.textShare
            )
        }

        ViewHelper.makeClickable(this, this.binding.fabShare, this.binding.imageBack)
    }

    private fun data() {
        this.binding.let {
            it.textDate.text = this.detail!!.createDate
            it.textUser.text = this.detail!!.wa
            it.textTarget.text = this.detail!!.target
            it.textStatus.text = this.detail!!.sn
            it.textTransactionId.text = this.invoiceId
            it.textProductName.text = this.detail!!.product
        }
    }


    override fun onClick(p0: View?) {
        if (p0 == this.binding.fabShare) {
            this.share()
        } else if (p0 == this.binding.imageBack) {
            this.finish()
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