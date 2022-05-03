package com.example.mantraprototipo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Rutinas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://medlineplus.gov/spanish/ency/patientinstructions/000874.htm");
    }
}