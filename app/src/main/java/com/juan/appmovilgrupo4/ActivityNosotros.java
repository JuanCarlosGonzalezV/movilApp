package com.juan.appmovilgrupo4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ActivityNosotros extends AppCompatActivity {

    Button btnVuelve;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        btnVuelve = findViewById(R.id.btn_atras);
        webView = findViewById(R.id.my_web_view);
        //webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("file:///android_asset/")) {
                    // Permitir la carga de recursos locales
                    return false;
                }
                // Cualquier otra URL será manejada por el WebView
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webSettings.setJavaScriptEnabled(true);
        //String url = "https://acortar.link/iwymhq";
        //String url = "file:///android_asset/pagina.html";
        String url = "https://insolvenciacolombia.com/calculadora-de-credito-amortizacion/";
        webView.loadUrl(url);

        btnVuelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });


    }

}