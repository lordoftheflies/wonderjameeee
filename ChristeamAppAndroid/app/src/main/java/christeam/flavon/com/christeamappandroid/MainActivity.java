package christeam.flavon.com.christeamappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
        // Locate base url of the frontend from the webapp.
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.loadUrl(getResources().getString(R.string.base_url));
    }
}
