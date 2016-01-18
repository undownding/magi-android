package me.undownding.magi

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.text.contains

class MainActivity : AppCompatActivity() {

    companion object {
        val MAGI_INDEX_URL = "http://magi.peak-labs.com/"
    }

    private lateinit var webView: WebView
    private val client = MagiWebViewClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        webView.setWebViewClient(client)
        webView.settings.javaScriptEnabled = true
        handleIntent(intent)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else if (!webView.url.equals(MAGI_INDEX_URL)) {
            // I don't think need this, do what super do instead.
            // webView.loadUrl(MAGI_INDEX_URL)
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_VIEW.equals(intent?.action) && intent?.dataString != null) {
            webView.loadUrl(
                    "http://magi.peak-labs.com/box?zoom=0.9&erp=1&background=%23f9f9f9&callback=magibox&q=${Uri.parse(intent?.dataString).getQueryParameter("q")}"
            )
        } else {
            webView.loadUrl(MAGI_INDEX_URL)
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

        override fun onPageFinished(view: WebView?, url: String?) {
            if (MainActivity.MAGI_INDEX_URL.equals(url)) {
                view?.clearHistory()
            }
            super.onPageFinished(view, url)
        }
    }
}
