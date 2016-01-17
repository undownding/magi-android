package me.undownding.magi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    private val MAGI_INDEX_URL = "http://magi.peak-labs.com/"

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(MAGI_INDEX_URL)
    }
}
