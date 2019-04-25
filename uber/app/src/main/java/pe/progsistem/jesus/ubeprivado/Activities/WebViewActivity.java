package pe.progsistem.jesus.ubeprivado.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import pe.progsistem.jesus.ubeprivado.R;

public class WebViewActivity extends AppCompatActivity {
    String dir ="";
    String des ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent recibir = getIntent();
        dir = recibir.getStringExtra("dir");
        des = recibir.getStringExtra("des");


        setContentView(R.layout.activity_web_view);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
      //  TextView td = (TextView) findViewById(R.id.tv_info);
       // td.setText("direccion: "+dir+" -> destino:"+des);

        myWebView.loadUrl("https://www.google.com/maps/dir/"+dir+"/"+des+"/");
        //https://www.google.com/maps/dir/Lima/Ica/
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView mWebView;
        mWebView = (WebView) findViewById(R.id.webview);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}