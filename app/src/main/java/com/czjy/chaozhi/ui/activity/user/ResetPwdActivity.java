package com.czjy.chaozhi.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.presenter.user.ResetPresenter;
import com.czjy.chaozhi.presenter.user.contract.ResetContract;
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
public class ResetPwdActivity extends BaseActivity<ResetPresenter> implements ResetContract.View {

    @BindView(R.id.forget_title)
    TextView mTitle;
    @BindView(R.id.forget_phone)
    EditText mPhone;
    @BindView(R.id.forget_code)
    EditText mCode;
    @BindView(R.id.forget_get_code)
    TextView mGetCode;
    @BindView(R.id.forget_pwd)
    EditText mPwd;
    @BindView(R.id.forget_submit)
    TextView mSubmit;

    private Disposable mDisposable;


    @Override
    protected void init() {
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void setActionBar() {

    }


    @OnClick({R.id.forget_submit, R.id.forget_get_code})
    public void onViewClicked(View view) {
        String phone = mPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.forget_submit:

                String code = mCode.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入手机号");
                }

                if (TextUtils.isEmpty(code)) {
                    toast("请输入验证码");
                }

                if (TextUtils.isEmpty(pwd)) {
                    toast("请输入密码");
                }
                mPresenter.resetPwd(phone, code, pwd);
                break;
            case R.id.forget_get_code:
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入手机号");
                    return;
                }
                mGetCode.setEnabled(false);
                mPresenter.getCode(phone, Const.CODE_TYPE_RESET);
                mGetCode.setBackgroundColor(getResources().getColor(R.color._C8C8C8));
                mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (!isFinishing()) {
                                    mGetCode.setText(String.format("%d秒", 60 - aLong));
                                }

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
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }

    }

    @Override
    public void resetSuccess() {
//        Intent intent = new Intent(mContext, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
