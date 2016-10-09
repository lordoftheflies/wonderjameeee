package hu.digitaldefense.topflavonnetwork;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by lordoftheflies on 2016.10.09..
 */
public class AppWebViewClients extends WebViewClient {

    private ProgressBar progressBar;

    public AppWebViewClients(ProgressBar progressBar, View view) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

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

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.getUrl().getHost().length() == 0) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
        view.getContext().startActivity(intent);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }
}
