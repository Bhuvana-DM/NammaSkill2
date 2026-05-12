package com.example.nammaskill2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val webView = findViewById<WebView>(R.id.webViewMap)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Search for skill centers on Google Maps
        webView.loadUrl("https://www.google.com/maps/search/government+skill+development+center")
    }
}