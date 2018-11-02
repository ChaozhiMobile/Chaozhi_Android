package com.czjy.chaozhi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.di.componet.DaggerFragmentComponent;
import com.czjy.chaozhi.di.componet.FragmentComponent;
import com.czjy.chaozhi.di.module.FragmentModule;
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.witget.dialog.CircleProgressDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyg on 2018/9/28.
 */

public abstract class BaseFragment<T extends IPresenter> extends RxFragment implements IBaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder mUnBinder;
    private CircleProgressDialog mProgress;
    private String TAG = "BaseFragment";

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, mView);
        mPresenter.attachView(this);
        init();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe
    public void onEvent(Object object) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showProgress() {
        mProgress = new CircleProgressDialog(getActivity());
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

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void init();


}
