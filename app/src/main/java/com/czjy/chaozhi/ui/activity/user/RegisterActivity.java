package com.czjy.chaozhi.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.presenter.user.RegPresenter;
import com.czjy.chaozhi.presenter.user.contract.RegContract;
import com.czjy.chaozhi.ui.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/28.
 */
public class RegisterActivity extends BaseActivity<RegPresenter> implements RegContract.View {

    @BindView(R.id.register_phone)
    EditText mPhone;
    @BindView(R.id.register_code)
    EditText mCode;
    @BindView(R.id.register_get_code)
    TextView mGetCode;
    @BindView(R.id.register_pwd)
    EditText mPwd;
    @BindView(R.id.register_pwd_repeat)
    EditText mPwdRepeat;
    @BindView(R.id.register_name)
    EditText mName;
    @BindView(R.id.register_submit)
    TextView mSubmit;
    private Disposable mDisposable;

    @OnClick({R.id.register_get_code, R.id.register_submit})
    public void onViewClicked(View view) {
        String phone = mPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.register_get_code:
                if (TextUtils.isEmpty(phone)){
                    toast("请输入手机号");
                    return;
                }
                mGetCode.setEnabled(false);
                mPresenter.getCode(phone, Const.CODE_TYPE_REG);
                mGetCode.setBackgroundColor(getResources().getColor(R.color._C8C8C8));
                mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                mGetCode.setText(String.format("%d秒", 60 - aLong));
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                mGetCode.setEnabled(true);
                                mGetCode.setText("获取验证码");
                                mGetCode.setBackgroundColor(getResources().getColor(R.color._C31A1F));
                            }
                        }).subscribe();
                break;
            case R.id.register_submit:
                String code = mCode.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                String pwdRepeat = mPwdRepeat.getText().toString().trim();
                String name = mName.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入手机号");
                    return;
                }

                if (TextUtils.isEmpty(code)) {
                    toast("请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    toast("请输入密码");
                    return;
                }

                if (TextUtils.isEmpty(pwdRepeat)) {
                    toast("请再次输入密码");
                    return;
                }

                if (!pwd.equals(pwdRepeat)) {
                    toast("密码输入不一致");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    toast("请输入姓名");
                    return;
                }
                mPresenter.register(phone, code, pwd, name);
                break;
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("");
    }


    @Override
    public void regSuccess() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable!=null){
            mDisposable.dispose();
        }
    }
}
