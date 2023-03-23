package com.example.ejercicio_dp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    String Url = "https://www.dportenis.mx/";
    SwipeRefreshLayout swipeRefreshLayout;
    WebView myWebView;
    final Context context = this;

    private final int PERMISO_REQUEST_ESTADO = 0;
    private final int PERMISO_REQUEST_ALMACENAMIENTO_EXTERNO = 0;
    private final int PERMISO_REQUEST_CAMARA = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    @Override
    protected void onCreate(Bundle saceInstancesStates){
        super.onCreate(saceInstancesStates);
        setContentView(R.layout.activity_main);
        myWebView = (WebView)findViewById(R.id.webview);
        assert myWebView != null;

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDatabaseEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setAllowFileAccess(true);
        if(Build.VERSION.SDK_INT >= 32){
            webSettings.setMixedContentMode(0);
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }else if(Build.VERSION.SDK_INT >= 19){
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }else if(Build.VERSION.SDK_INT < 19){
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new MyWebChrome());
        myWebView.setVerticalScrollBarEnabled(false);

        myWebView.loadUrl(Url);
    }
    private class MyWebChrome extends WebViewClient{
         @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
             super.onPageStarted(view, url, favicon);
         }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
            handler.proceed();
        }
         @Override
        public void onPageFinished(WebView view, String url){
             findViewById(R.id.loaderwebview).setVisibility(View.GONE);
             findViewById(R.id.webview).setVisibility(View.VISIBLE);
         }
    }
}