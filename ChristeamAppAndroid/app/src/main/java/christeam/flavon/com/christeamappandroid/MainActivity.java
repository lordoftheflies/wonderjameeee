package christeam.flavon.com.christeamappandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Reference to web-view.
        myWebView = (WebView) findViewById(R.id.webView);
        // Setup for use Polymer
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        // Locate base url of the frontend from the webapp.
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.loadUrl(getResources().getString(R.string.base_url));

        // Set our webclient
        myWebView.setWebViewClient(new FrontEndViewClient());
    }

    private class FrontEndViewClient extends WebViewClient {
        

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // Handle local URLs.
            if (Uri.parse(url).getHost().length() == 0) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
    }
}
