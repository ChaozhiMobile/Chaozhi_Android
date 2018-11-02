package com.czjy.chaozhi.ui.fragment.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseFragment;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.AgentBean;
import com.czjy.chaozhi.model.event.UpdateFgEvent;
import com.czjy.chaozhi.presenter.main.LimitlessPresenter;
import com.czjy.chaozhi.presenter.main.contract.LimitlessContract;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by huyg on 2018/9/29.
 */
public class LimitlessFragment extends BaseFragment<LimitlessPresenter> implements LimitlessContract.View {

    @BindView(R.id.webview)
    WebView mWebView;

    private String agentToken;


    public static LimitlessFragment newInstance() {
        LimitlessFragment limitlessFragment = new LimitlessFragment();
        return limitlessFragment;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_limitless;
    }

    @Override
    protected void init() {
        initData();
        initView();
    }

    private void initView() {
        initWebView();
    }


    private void initData() {
        AgentBean agentBean = new AgentBean();
        agentBean.setToken(App.getInstance().getToken());
        agentBean.setWifi((boolean) SharedPreferencesUtils.getParam(mContext, Const.KEY_WIFI, false));
        agentToken = new Gson().toJson(agentBean);
    }

    private void initWebView() {
        initSetting();
        mWebView.loadUrl(Const.H5_URL + Const.ROUTER_INFINITE);
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

    @Subscribe
    public void onEvent(UpdateFgEvent event) {
        int index = event.index;
        if (index == 2) {
            mWebView.loadUrl(Const.H5_URL + Const.ROUTER_INFINITE);
        }
    }


}
