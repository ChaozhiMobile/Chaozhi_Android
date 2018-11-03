package com.czjy.chaozhi.di.componet;

import android.app.Activity;

import com.czjy.chaozhi.di.ActivityScope;
import com.czjy.chaozhi.di.module.ActivityModule;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.ui.activity.setting.SelectSubjectActivity;
import com.czjy.chaozhi.ui.activity.setting.SettingActivity;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.ui.activity.user.RegisterActivity;
import com.czjy.chaozhi.ui.activity.user.ResetPwdActivity;
import com.czjy.chaozhi.ui.activity.user.WelcomeActivity;
import com.czjy.chaozhi.ui.activity.web.WebDetailActivity;

import dagger.Component;

/**
 * Created by huyg on 2018/9/28.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(ResetPwdActivity resetPwdActivity);

    void inject(MainActivity mainActivity);

    void inject(SelectSubjectActivity selectSubjectActivity);

    void inject(SettingActivity settingActivity);

    void inject(WebDetailActivity webDetailActivity);
}
