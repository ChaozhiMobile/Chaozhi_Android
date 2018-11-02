package com.czjy.chaozhi;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.czjy.chaozhi.di.componet.AppComponent;
import com.czjy.chaozhi.di.componet.DaggerAppComponent;
import com.czjy.chaozhi.di.module.AppModule;
import com.czjy.chaozhi.manager.ActivityManager;
import com.facebook.stetho.Stetho;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by huyg on 2018/9/28
 */

public class App extends MultiDexApplication {


    private AppComponent mAppComponent;
    private ActivityManager activityManager = null;
    private static App instance;
    private String token;
    private String phone;

    static {
        //初始化上拉刷新及上拉加载
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((Context context, RefreshLayout layout) -> new MaterialHeader(context));
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((Context context, RefreshLayout layout) -> new ClassicsFooter(context));
    }


    public static synchronized App getInstance() {
        return instance;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    /**
     * 初始化Dagger所使用的连接器
     */
    private void initDaggerComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();
    }

    /**
     * 获取连接器
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activityManager = ActivityManager.getScreenManager();
        initDaggerComponent();
        Stetho.initializeWithDefaults(this);
        initCrash();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    private void initCrash() {
        if (BuildConfig.DEBUG){
            CrashReport.initCrashReport(getApplicationContext(), "9044b22d30", true);
        }else{
            CrashReport.initCrashReport(getApplicationContext(), "9044b22d30", false);
        }
    }

}
