package com.czjy.chaozhi.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.czjy.chaozhi.App;
import com.czjy.chaozhi.widget.dialog.CircleProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyg on 2018/9/28.
 */

public abstract class SimpleActivity extends ToolbarActivity {


    private Unbinder mUnBinder;
    protected Activity mContext;
    private CircleProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        ((App) getApplication()).getActivityManager().pushActivity(this);
        mContext = this;
        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        if (mProgress!=null){
            mProgress.cancel();
        }
        ((App) getApplication()).getActivityManager().finishActivity(this);
    }


    public void showProgress() {
        if (mProgress == null) {
            mProgress = new CircleProgressDialog(this);
        }
        if (this.isFinishing()||this.isDestroyed()){
            return;
        }
        if (!mProgress.isShowing()) {
            mProgress.show();
        }

    }

    public void closeProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }


    protected abstract int getLayout();

    protected abstract void init();
}
