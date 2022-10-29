package id.muhammadfaisal.jupafazz.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import id.muhammadfaisal.jupafazz.databinding.ActivityWebViewBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant

class WebViewActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWebViewBinding

    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityWebViewBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.url()
        this.initialize()
    }

    private fun url() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.url = bundle.getString(Constant.Key.URL, "")
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initialize() {
        this.binding.apply {
            this.swipe.isRefreshing = true
            this.webView.loadUrl(url)
            this.webView.settings.javaScriptEnabled = true
            this.webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url!!.startsWith("whatsapp:")) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                        return true
                    } else if (url.startsWith("mailto:")) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                        return true
                    }
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    swipe.isRefreshing = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    swipe.isRefreshing = false
                }
            }
        }

        ViewHelper.makeClickable(this, this.binding.imageClose)
    }

    override fun onBackPressed() {
        if (this.binding.webView.canGoBack()) {
            this.binding.webView.goBack()
        } else {
            finish()
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageClose) {
            finish()
        }
    }
}