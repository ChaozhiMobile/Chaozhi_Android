package com.czjy.chaozhi.di.module;

import android.app.Activity;


import com.czjy.chaozhi.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/9/28.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
