package com.example.mantraprototipo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class foro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www3.paho.org/hq/index.php?option=com_content&view=article&id=15659:oms-foro-de-salud-mental&Itemid=42050&lang=es");
    }
}