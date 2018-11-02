package com.czjy.chaozhi.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.di.componet.ActivityComponent;
import com.czjy.chaozhi.di.componet.DaggerActivityComponent;
import com.czjy.chaozhi.di.module.ActivityModule;
import com.czjy.chaozhi.model.event.BaseEvent;
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.witget.dialog.CircleProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyg on 2018/9/28.
 */

public abstract class BaseActivity<T extends IPresenter> extends ToolbarActivity implements IBaseView {


    @Inject
    protected T mPresenter;
    protected Activity mContext;
    protected Unbinder mUnbinder;
    private CircleProgressDialog mProgress;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        ((App) getApplication()).getActivityManager().pushActivity(this);
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initSavedInstance(savedInstanceState);
        init();
    }


    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((App) getApplication()).getAppComponent())
                .build();
    }





    @Subscribe
    public void onEvent(BaseEvent event){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
        ((App) getApplication()).getActivityManager().finishActivity(this);
    }


    public void initSavedInstance(Bundle savedInstanceState) {

    }

    @Override
    public void showProgress() {
        mProgress = new CircleProgressDialog(this);
        if (mProgress != null && !mProgress.isShowing()) {
            mProgress.show();
        }

    }

    @Override
    public void closeProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @Override
    public void toast(String message) {
        ToastUtil.toast(mContext, message);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected abstract void init();

    protected abstract void initInject();

    protected abstract int getLayoutId();


}
