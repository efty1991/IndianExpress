package com.mobdice.indianexpress.network_call.web_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobdice.indianexpress.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity implements WVC_ToActivityCallBack {

    private static final String HOME_PAGE = "https://indianexpress.com/";

    @BindView(R.id.web_view)
    WebView myWebView;
    @BindView(R.id.progress_rl)
    RelativeLayout progress_rl;
    @BindView(R.id.heading)
    TextView title;

    @OnClick(R.id.progress_rl)
    public void onClickProgress() {
        progress_rl.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        String url = HOME_PAGE;
        String data = null;
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("URL");
            data = intent.getStringExtra("DATA");
        }
        if (url == null || "".equalsIgnoreCase(url))
            url = HOME_PAGE;
        String ttl = intent.getStringExtra("TITTLE");
        if (ttl != null)
            title.setText(Html.fromHtml(ttl));
        setUpWebView(url, data, data != null && !"".equalsIgnoreCase(data) ? true : false);
    }

    private void setUpWebView(String URL_LOAD, String data, boolean isDataLoad) {
        if (isDataLoad) {
//            loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + post.getContent(), "text/html", "UTF-8", null);
            int indexFirstParaEnd = data.indexOf("</p>");
            if (indexFirstParaEnd != -1) {
                String string1 = data.substring(0, indexFirstParaEnd + "</p>".length());
                String string2 = data.substring(indexFirstParaEnd + "</p>".length());
                Log.e("STRING_1", string1);
                Log.e("STRING_2", string2);
            }

//            String script = "<script> function calcIframeHeight() { var the_height = document.getElementById('iframecontent').contentWindow.document.body.scrollHeight; document.getElementById('iframecontent').height = the_height;}</script>";
            String script = "<script> function resizeIframe(obj) { obj.style.height = obj.contentWindow.document.body.scrollHeight;} </script>";
//            data = data.replace("<iframe ", "<iframe name=\"iframecontent\" id=\"iframecontent\" onLoad=\"calcIframeHeight();\" ");
            data = data.replace("<iframe ", "<iframe onload=\"resizeIframe(this)\" ");
            data = data.replace("overflow:hidden", "");
            data = script + "<style>img{display: inline;height: auto;max-width: 100%;} iframe{max-width: 100%;} a{display: inline;height: auto;max-width: 100%;white-space: pre-wrap;white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;}</style>" + data;
            Log.e("WEBVIEW_DATA", "" + data);
            myWebView.loadData(data, "text/html", "UTF-8");
        } else
            myWebView.loadUrl(URL_LOAD);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.setWebViewClient(new MyWebViewClient(this));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFinishedLoadingPage() {
        progress_rl.setVisibility(View.GONE);
    }
}
