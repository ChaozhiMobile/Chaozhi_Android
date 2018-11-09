package com.czjy.chaozhi.wxapi;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.SimpleActivity;
import com.czjy.chaozhi.model.bean.WxPayBean;
import com.czjy.chaozhi.util.ToastUtil;
import com.facebook.stetho.common.LogUtil;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends SimpleActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    private WxPayBean wxPayBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setActionBar() {
        mTitle.setText("支付");
    }

    @Override
    protected int getLayout() {
        return R.layout.pay_result;
    }

    @Override
    protected void init() {
        api = WXAPIFactory.createWXAPI(this, "wxa595f547a6eeeb65");
        api.handleIntent(getIntent(), this);
        initWx();
    }

    private void initWx() {
        Intent intent = getIntent();
        if (intent != null) {
            wxPayBean = intent.getParcelableExtra("wxpay");
        }

        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;

        if (!isPaySupported) {
            ToastUtil.toast(mContext,"未安装微信，不能使用微信支付");
            return;
        }
        if (wxPayBean != null) {
            PayReq req = new PayReq();
            req.appId = wxPayBean.getAppid();
            req.partnerId = wxPayBean.getPartnerid();
            req.prepayId = wxPayBean.getPrepayid();
            req.nonceStr = wxPayBean.getNoncestr();
            req.timeStamp = String.valueOf(wxPayBean.getTimestamp());
            req.packageValue = wxPayBean.getPackageX();
            req.sign = wxPayBean.getSign();
            boolean status = api.sendReq(req);

            LogUtil.i("微信支付参数："
                    +"\n"+wxPayBean.getAppid()
                    +"\n"+wxPayBean.getPartnerid()
                    +"\n"+wxPayBean.getPrepayid()
                    +"\n"+wxPayBean.getNoncestr()
                    +"\n"+String.valueOf(wxPayBean.getTimestamp())
                    +"\n"+wxPayBean.getPackageX()
                    +"\n"+wxPayBean.getSign());
            LogUtil.i("微信支付调用结果："+String.valueOf(status));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "微信支付errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {

            } else if (resp.errCode == -1) {

            }
        }
    }
}