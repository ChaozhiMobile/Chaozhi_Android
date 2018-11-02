package com.czjy.chaozhi.di.componet;

import android.app.Activity;


import com.czjy.chaozhi.di.FragmentScope;
import com.czjy.chaozhi.di.module.FragmentModule;
import com.czjy.chaozhi.ui.fragment.home.HomeFragment;
import com.czjy.chaozhi.ui.fragment.home.LearnFragment;
import com.czjy.chaozhi.ui.fragment.home.LimitlessFragment;
import com.czjy.chaozhi.ui.fragment.home.MineFragment;

import dagger.Component;

/**
 * Created by huyg on 2018/9/28.
 */


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MineFragment mineFragment);

    void inject(HomeFragment homeFragment);

    void inject(LearnFragment learnFragment);

    void inject(LimitlessFragment limitlessFragment);
}
