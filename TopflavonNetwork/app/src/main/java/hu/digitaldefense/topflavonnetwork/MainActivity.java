package hu.digitaldefense.topflavonnetwork;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private static ViewGroup webViewParentViewGroup = null;
    private static WebView webView = null;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if(webView != null){
            webViewParentViewGroup.removeView(webView);

            setContentView(R.layout.activity_minor);

            webViewParentViewGroup = (ViewGroup) findViewById(R.id.minorViewGroup);
            webViewParentViewGroup.addView(this.webView);
        } else {
            setContentView(R.layout.activity_main);

            webViewParentViewGroup = (ViewGroup) findViewById(R.id.mainViewGroup);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            webView = (WebView) findViewById(R.id.webview);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
            webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            webView.clearHistory();
            webView.clearFormData();
            webView.clearCache(true);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    Log.d(TAG, "Cleared application cookies: " + value);
                }
            });

            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new AppWebViewClients(this.progressBar));
            webView.addJavascriptInterface(new AppJavaScriptProxy(this), "sessionProxy");

            webView.evaluateJavascript("fromAndroid()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, "User logged in with account: " + value);
                }
            });

            webView.loadUrl(getResources().getString(R.string.base_url));

            // Locate base url of the frontend from the webapp.
            //webView.setWebChromeClient(new WebChromeClient());

            // Set our webclient
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //// TODO Auto-generated method stub
            //view.loadUrl(url);
            //return true;

            // Handle local URLs.
            if (Uri.parse(url).getHost().length() == 0) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //// TODO Auto-generated method stub
            //view.loadUrl(url);
            //return true;

            // Handle local URLs.
            if (Uri.parse(request.getUrl().toString()).getHost().length() == 0) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
            view.getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public class AppJavaScriptProxy {

        private Activity activity = null;

        public AppJavaScriptProxy(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void showMessage(String message) {

        }
    }
}
