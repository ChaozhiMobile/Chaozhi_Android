package com.czjy.chaozhi.ui.activity.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.AgentBean;
import com.czjy.chaozhi.model.bean.WebBean;
import com.czjy.chaozhi.presenter.web.WebDetailPresenter;
import com.czjy.chaozhi.presenter.web.contract.WebDetailContract;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;

import butterknife.BindView;

/**
 * Created by huyg on 2018/10/6.
 */
public class WebDetailActivity extends BaseActivity<WebDetailPresenter> implements WebDetailContract.View {


    @BindView(R.id.webview)
    WebView mWebView;

    private String agentToken;
    private String url;
    private Intent mIntent = new Intent();

    public static void action(Context context, String url) {
        Intent intent = new Intent(context, WebDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initData();
        initWebView();


    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        }
    }

    private void initData() {
        AgentBean agentBean = new AgentBean();
        agentBean.setWifi((String) SharedPreferencesUtils.getParam(mContext, Const.KEY_WIFI, "0"));
        agentBean.setToken(App.getInstance().getToken());
        agentToken = new Gson().toJson(agentBean);
    }

    private void initWebView() {
        initSetting();
        mWebView.addJavascriptInterface(new JSBridge(), "webkit");
        mWebView.loadUrl(Const.H5_URL + url);

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void setActionBar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持javaScript
        settings.setDefaultTextEncodingName("utf-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + "&&" + agentToken);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.clearCache(true);

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
            mTitle.setText(view.getTitle());
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

    private class JSBridge {

        @JavascriptInterface
        public void open(String data) {
            WebBean webBean = new Gson().fromJson(data, WebBean.class);
            switch (webBean.getType()) {
                case "web":
                    mWebView.loadUrl(webBean.getUrl());
                    break;
                case "app":
                    switch (webBean.getTo()) {
                        case "home":
                            mIntent.setClass(mContext, MainActivity.class);
                            break;
                        case "login":
                            mIntent.setClass(mContext, LoginActivity.class);
                            break;
                    }
                    startActivity(mIntent);
                    break;
            }
        }

        @JavascriptInterface
        public void close(String data) {
            finish();
        }

    }
}
