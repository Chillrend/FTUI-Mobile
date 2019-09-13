package org.ftui.mobile;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.ftui.mobile.utils.JSInterceptor;

public class SSOActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sso);

        WebViewClient eventClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:HtmlViewer.showHTML" +
                        "(document.getElementsByTagName('pre')[0].innerHTML);");
            }
        };

        webView = findViewById(R.id.SSOWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(eventClient);
        webView.loadUrl("http://pengaduan.ccit-solution.com/api/ssologin");
        webView.addJavascriptInterface(new JSInterceptor(this), "HtmlViewer");
    }
}
