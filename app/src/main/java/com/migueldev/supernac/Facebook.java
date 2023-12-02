package com.migueldev.supernac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Facebook extends AppCompatActivity {
    WebView webfacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        getSupportActionBar().hide();
        webfacebook = (WebView) findViewById(R.id.webfacebook);
        webfacebook.setWebViewClient(new WebViewClient());
        webfacebook.loadUrl("https://www.facebook.com/supernac-supermercados-103494018984263/");
        WebSettings webSettings = webfacebook.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webfacebook.canGoBack()) {
            webfacebook.goBack();
        } else{
            super.onBackPressed();
        }}
}