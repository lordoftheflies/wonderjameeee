package hu.digitaldefense.topflavonnetwork;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by lordoftheflies on 2016.10.09..
 */
public class AppJavaScriptProxy {

    private Activity activity = null;

    public AppJavaScriptProxy(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void showMessage(String message) {

    }
}
