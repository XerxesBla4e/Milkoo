package com.example.life.Webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.life.R;


public class websiteActivity extends AppCompatActivity {

    private WebView webView;
    String FruitVeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        initViews();

        FruitVeg = getIntent().getStringExtra("fruitveg");
        String calorieSearch = "http://www.google.com/search?q=" + FruitVeg + "+calories";
       /* Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/search?q=" + url + "+calories"));*/
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(calorieSearch);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    private void initViews() {
        webView = findViewById(R.id.webview);
    }
}