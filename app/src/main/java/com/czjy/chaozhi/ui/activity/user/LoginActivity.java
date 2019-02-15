package com.czjy.chaozhi.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.response.LoginResponse;
import com.czjy.chaozhi.presenter.user.LoginPresenter;
import com.czjy.chaozhi.presenter.user.contract.LoginContract;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by huyg on 2018/9/28.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.app_logo)
    ImageView mLogo;
    @BindView(R.id.login_phone)
    EditText mPhone;
    @BindView(R.id.login_pwd)
    EditText mPwd;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.login_forget)
    TextView mForget;
    @BindView(R.id.login_register)
    TextView mRegister;

    @OnClick({R.id.login, R.id.login_forget, R.id.login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String phone = mPhone.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    toast("请输入密码");
                    return;
                }
                mPresenter.login(phone, pwd);
                break;

            case R.id.login_forget:
                mIntent.setClass(mContext, ResetPwdActivity.class);
                startActivity(mIntent);
                break;

            case R.id.login_register:
                mIntent.setClass(mContext, RegisterActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private Intent mIntent = new Intent();

    @Override
    protected void init() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.setClass(mContext, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }


    @Override
    public void loginSuccess(LoginResponse response) {
        SharedPreferencesUtils.setParam(mContext, Const.KEY_TOKEN, response.getToken());
        SharedPreferencesUtils.setParam(mContext, Const.KEY_PHONE, mPhone.getText().toString());
        App.getInstance().setToken(response.getToken());
        App.getInstance().setPhone(mPhone.getText().toString());
        setAlias();
        mIntent.setClass(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mIntent.setClass(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    /* 极光推送设置别名 */
    private void setAlias() {
        JPushInterface.setAlias(mContext, 0, mPhone.getText().toString());
    }
}
