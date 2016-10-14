package hu.digitaldefense.topflavonnetwork;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private static ViewGroup viewGroup = null;
    private static ViewGroup nonVideoLayout = null;
    private static ViewGroup videoLayout = null;
    private static VideoEnabledWebView webView = null;
    private static VideoEnabledWebChromeClient webChromeClient = null;
    private static ProgressBar progressBar = null;
    private static View loadingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (webView != null) {
            viewGroup.removeView(nonVideoLayout);
            viewGroup.removeView(videoLayout);

            setContentView(R.layout.activity_minor);

            viewGroup = (ViewGroup) findViewById(R.id.minorViewGroup);

            //nonVideoLayout = (ViewGroup) findViewById(R.id.nonVideoLayout);
            //videoLayout = (ViewGroup) findViewById(R.id.videoLayout);

            viewGroup.addView(nonVideoLayout);
            viewGroup.addView(videoLayout);
        } else {
            setContentView(R.layout.activity_main);

            viewGroup = (ViewGroup) findViewById(R.id.mainViewGroup);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            nonVideoLayout = (ViewGroup) findViewById(R.id.nonVideoLayout);
            videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
            loadingView = getLayoutInflater().inflate(R.layout.view_video_loading, null); // Your own view, read class comments

            webView = (VideoEnabledWebView) findViewById(R.id.webView);
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

            // Initialize the VideoEnabledWebChromeClient and set event handlers
            //View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
            webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
            {
                // Subscribe to standard events, such as onProgressChanged()...
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    // Your code...
                }
            };
            webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
                @Override
                public void toggledFullscreen(boolean fullscreen) {
                    // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                    if (fullscreen) {
                        WindowManager.LayoutParams attrs = getWindow().getAttributes();
                        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                        attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        getWindow().setAttributes(attrs);
                        if (android.os.Build.VERSION.SDK_INT >= 14) {
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                        }
                    } else {
                        WindowManager.LayoutParams attrs = getWindow().getAttributes();
                        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                        attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        getWindow().setAttributes(attrs);
                        if (android.os.Build.VERSION.SDK_INT >= 14) {
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        }
                    }

                }
            });
            webView.setWebChromeClient(webChromeClient);
            webView.setWebViewClient(new AppWebViewClients(progressBar, webView));
            webView.addJavascriptInterface(new AppJavaScriptProxy(), "sessionProxy");

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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
//            this.webView.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (webChromeClient != null && !webChromeClient.onBackPressed()) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                // Close app (presumably)
                super.onBackPressed();
            }
        }
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

    /**
     * Created by lordoftheflies on 2016.10.09..
     */
    public class AppJavaScriptProxy {

        public AppJavaScriptProxy() {
        }

        @JavascriptInterface
        public void updateAccountId(String value) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.subscription_path);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("accountId", value);
            params.put("subscriptionId", WonderjamFirebaseInstanceIDService.getToken());
            Log.d(TAG, "User logged in with account: " + value);
            Log.d(TAG, "Current Firebase token: " + WonderjamFirebaseInstanceIDService.getToken());
// Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "Notification token synchronized: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Notification token synchronization failed: " + error);
                        }
                    });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}
