package com.czjy.chaozhi.ui.activity.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.AgentBean;
import com.czjy.chaozhi.model.bean.WebBean;
import com.czjy.chaozhi.model.bean.WebPayBean;
import com.czjy.chaozhi.model.bean.WxPayBean;
import com.czjy.chaozhi.presenter.web.WebDetailPresenter;
import com.czjy.chaozhi.presenter.web.contract.WebDetailContract;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.util.AuthResult;
import com.czjy.chaozhi.util.PayResult;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.util.Utils;
import com.czjy.chaozhi.wxapi.WXPayEntryActivity;
import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by huyg on 2018/10/6.
 */
public class WebDetailActivity extends BaseActivity<WebDetailPresenter> implements WebDetailContract.View {


    private static final int PAY_TYPE = 1;
    @BindView(R.id.webview)
    WebView mWebView;

    private String agentToken;
    private String url;
    private Intent mIntent = new Intent();
    private String orderInfo;
    private Runnable payRunnable;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int TAKE_PHOTO = 1111;
    private ValueCallback<Uri[]> mFilePathCallback;
    private IWXAPI api;
    private static final int REQ_CODE = 9999;

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
        initPay();

    }

    private void initPay() {
        payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        api = WXAPIFactory.createWXAPI(this, "wxb4ba3c02aa476ea1", true);
        api.registerApp("wxb4ba3c02aa476ea1");
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    LogUtil.i("支付宝支付结果：" + resultStatus);

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        // mWebView.loadUrl("javascript:fn_pay()");

                        ToastUtil.toast(mContext, "支付成功");

                        // 跳转首页-学习页面
                        mIntent.setClass(mContext, MainActivity.class);
                        mIntent.putExtra("flag", "支付成功");
                        startActivity(mIntent);

                    } else {
                        ToastUtil.toast(mContext, "支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                    } else {
                        // 其他状态值则为授权失败
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

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
        if (url.contains("http")) {
            mWebView.loadUrl(url);
            LogUtil.i("H5 Url：" + url);
        } else {
            mWebView.loadUrl(Const.H5_URL + url);
            LogUtil.i("H5 Url：" + Const.H5_URL + url);
        }
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
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + "&&" + agentToken);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.clearCache(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;
                getPhoto();
                return true;
            }
        });
        LogUtil.i("H5 UserAgent：" + settings.getUserAgentString());
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

            LogUtil.i("H5调原生返回值：" + data);

            WebBean webBean = new Gson().fromJson(data, WebBean.class);
            switch (webBean.getType()) {
                case "web":
                    WebDetailActivity.action(mContext, webBean.getUrl());
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

        @JavascriptInterface
        public void pay(String data) {

            LogUtil.i("H5调原生返回值：" + data);

            if (!TextUtils.isEmpty(data)) {
                WebPayBean webPayBean = new Gson().fromJson(data, WebPayBean.class);
                switch (webPayBean.getPayType()) {
                    case "alipay":
                        orderInfo = webPayBean.getPayStr();
                        if (!TextUtils.isEmpty(orderInfo)) {
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                        break;
                    case "wechat":

                        String payStr = webPayBean.getPayStr();
                        WxPayBean wxPayBean = new Gson().fromJson(payStr, WxPayBean.class);
                        Intent intent = new Intent(mContext, WXPayEntryActivity.class);
                        intent.putExtra("wxpay", wxPayBean);
                        startActivityForResult(intent, REQ_CODE);
                        break;
                }
            }

        }

    }

    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO) {
            if (data != null) {
                uploadImgFromSysPhotos(resultCode, data);
            }
        }
    }

    private void uploadImgFromSysPhotos(int resultCode, Intent intent) {

        if (mFilePathCallback != null) {//5.0+

            Uri[] uris = new Uri[1];

            uris[0] = intent == null || resultCode != RESULT_OK ? null

                    : intent.getData();

            if (uris[0] != null) {

                mFilePathCallback.onReceiveValue(uris);

            }

            mFilePathCallback = null;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
