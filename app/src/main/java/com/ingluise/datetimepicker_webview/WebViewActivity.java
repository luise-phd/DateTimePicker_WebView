package com.ingluise.datetimepicker_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView wv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        pb = findViewById(R.id.progressBar);
        wv = findViewById(R.id.webView);
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        //Habilita el Zoom
        ws.setBuiltInZoomControls(true);
        //Oculta los botones de zoom, haciendo que solo funcione con gestos
        ws.setDisplayZoomControls(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if(isOnline(this)) {
            wv.loadUrl("https://mintic.gov.co/portal/inicio/");
            wv.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    pb.setProgress(0);
                    pb.setVisibility(View.VISIBLE);
                    WebViewActivity.this.setProgress(progress * 1000);

                    pb.incrementProgressBy(progress);

                    if (progress == 100) {
                        pb.setVisibility(View.GONE);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Necesitas conexión a internet", Toast.LENGTH_LONG).show();
            wv.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        }
        else {
            new androidx.appcompat.app.AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("MinTic")
                .setMessage("¿Desea salir de la aplicación?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
        }
    }
}