package com.czjy.chaozhi.ui.activity.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.presenter.setting.SettingPresenter;
import com.czjy.chaozhi.presenter.setting.contract.SettingContract;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.ui.activity.user.ResetPwdActivity;
import com.czjy.chaozhi.ui.activity.web.SimpleWebActivity;
import com.czjy.chaozhi.ui.activity.web.WebDetailActivity;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2018/10/8.
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {


    @BindView(R.id.setting_wifi)
    SwitchButton mWifi;
    @BindView(R.id.setting_about)
    LinearLayout mAbout;
    @BindView(R.id.setting_modify_pwd)
    LinearLayout mModifyPwd;
    @BindView(R.id.setting_version)
    TextView mVersion;
    @BindView(R.id.setting_logout)
    TextView mLogout;

    private Intent mIntent = new Intent();

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        mWifi.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked==true) {
                    SharedPreferencesUtils.setParam(mContext, Const.KEY_WIFI, "1");
                } else {
                    SharedPreferencesUtils.setParam(mContext, Const.KEY_WIFI, "0");
                }
            }
        });
        initVersion();

    }

    private void initVersion() {
        PackageManager manager = mContext.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mVersion.setText(String.format("V%s", name));
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("系统设置");
    }


    @OnClick({R.id.setting_about, R.id.setting_modify_pwd, R.id.setting_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_about:
                WebDetailActivity.action(mContext,Const.ROUTER_ABOUT);
                break;
            case R.id.setting_modify_pwd:
                mIntent.setClass(mContext,ResetPwdActivity.class);
                startActivity(mIntent);
                break;
            case R.id.setting_logout:
                App.getInstance().setToken("");
                SharedPreferencesUtils.setParam(mContext,Const.KEY_TOKEN,"");
                mIntent.setClass(mContext,LoginActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }
}
