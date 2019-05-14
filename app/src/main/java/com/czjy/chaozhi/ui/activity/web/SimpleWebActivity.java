package com.czjy.chaozhi.ui.activity.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.SimpleActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.AgentBean;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;

import butterknife.BindView;

/**
 * Created by huyg on 2018/10/6.
 */
public class SimpleWebActivity extends SimpleActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    private String agentToken;
    private String url;
    private String title;

    public static void action(Context context, String url, String title) {
        Intent intent = new Intent(context, SimpleWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_simple_web;
    }

    @Override
    protected void init() {
        initIntent();
        initData();
        initWebView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent!=null){
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");

            if (!title.isEmpty()) {
                mTitle.setText(title);
            }
        }
    }

    private void initData() {
        AgentBean agentBean = new AgentBean();
        agentBean.setToken(App.getInstance().getToken());
        agentBean.setWifi((String) SharedPreferencesUtils.getParam(mContext, Const.KEY_WIFI, "0"));
        agentToken = new Gson().toJson(agentBean);
    }

    private void initWebView() {
        initSetting();
        mWebView.loadUrl(url);
    }

    @Override
    public void setActionBar() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持javaScript
        settings.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setDefaultTextEncodingName("utf-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + "&&" + agentToken);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        LogUtil.i("UserAgent："+settings.getUserAgentString());
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            closeProgress();
            if (title.isEmpty()) {
                mTitle.setText(view.getTitle());
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
