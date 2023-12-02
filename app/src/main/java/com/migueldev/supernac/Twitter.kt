package com.migueldev.supernac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class Twitter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter)
        supportActionBar?.hide()
        var webtwitter:WebView
        webtwitter = findViewById(R.id.webtwitter)
        webtwitter.settings.javaScriptEnabled
        webtwitter.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webtwitter.loadUrl("https://mobile.twitter.com/SupernacS")
    }
}