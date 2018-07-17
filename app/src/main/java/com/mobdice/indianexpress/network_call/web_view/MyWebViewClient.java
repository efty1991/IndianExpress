package com.mobdice.indianexpress.network_call.web_view;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    private WVC_ToActivityCallBack wvc_toActivityCallBack;

    public MyWebViewClient(WVC_ToActivityCallBack wvc_toActivityCallBack) {
        this.wvc_toActivityCallBack = wvc_toActivityCallBack;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if (Uri.parse(url).getHost().equals("www.example.com")) {
        // This is my web site, so do not override; let my WebView load the page
//            return false;
//        }
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(intent);
        Log.e("WEB_VIEW:Here", "" + Uri.parse(url).getHost());
        return false;
    }

    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        Log.e("WEB_VIEW", "" + request.getUrl());
//        if (request.getUrl().toString().equalsIgnoreCase("https://indianexpress.com/article/india/justice-gogoi-ramnath-goenka-memorial-lecture-supreme-court-constitution-india-5257105/"))
//            return true;
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        wvc_toActivityCallBack.onFinishedLoadingPage();
        super.onPageFinished(view, url);
    }
}