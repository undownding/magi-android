package me.undownding.magi

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.text.contains

class MainActivity : AppCompatActivity() {

    private val MAGI_INDEX_URL = "http://magi.peak-labs.com/"

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        webView.setWebViewClient(MagiWebViewClient())
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(MAGI_INDEX_URL)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private class MagiWebViewClient: WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            val result = !(url?.contains("i?q=")?: false)
            if (result) { // Not search page
                view?.context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else { // Search by app self
                view?.loadUrl(
                        "http://magi.peak-labs.com/box?zoom=0.9&erp=1&background=%23f9f9f9&callback=magibox&q=${Uri.parse(url).getQueryParameter("q")}"
                )
            }
            return true
        }

    }
}
