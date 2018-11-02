package com.czjy.chaozhi.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;


import com.czjy.chaozhi.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/9/28.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity ProvideActivity(){
        return mFragment.getActivity();
    }
}
