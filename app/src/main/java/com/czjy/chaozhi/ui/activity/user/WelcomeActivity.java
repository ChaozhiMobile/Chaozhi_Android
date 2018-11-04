package com.czjy.chaozhi.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.presenter.user.WelcomePresenter;
import com.czjy.chaozhi.presenter.user.contract.WelcomeContract;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.util.SharedPreferencesUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huyg on 2018/9/28.
 */
public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    private Disposable mDisposable;
    private String token;
    private Intent mIntent = new Intent();

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        initToken();
        boolean isFirst = (boolean) SharedPreferencesUtils.getParam(mContext,"isFirst",true);
        if (isFirst){
            mIntent.setClass(mContext, GuideActivity.class);
            startActivity(mIntent);
            finish();
        }else{
            mDisposable = Observable.timer(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            mIntent.setClass(mContext, MainActivity.class);
                            startActivity(mIntent);
                            finish();
                        }
                    });
        }

    }

    private void initToken() {
        token = (String) SharedPreferencesUtils.getParam(mContext, Const.KEY_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            App.getInstance().setToken(token);
        }
    }

    private void initView() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
