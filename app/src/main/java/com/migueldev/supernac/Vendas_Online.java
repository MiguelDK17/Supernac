package com.migueldev.supernac;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Vendas_Online extends AppCompatActivity {
    WebView wvwsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vendas_online);
        wvwsite = (WebView) findViewById(R.id.WvWsite);
        wvwsite.setWebViewClient(new WebViewClient());
        wvwsite.loadUrl("http://localhost/conex%c3%a3o_web/");
        WebSettings webSettings = wvwsite.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (wvwsite.canGoBack()) {
            wvwsite.goBack();
        } else{
        super.onBackPressed();
    }}
}
