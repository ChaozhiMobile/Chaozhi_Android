package com.czjy.chaozhi.util.excep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.ui.activity.MainActivity;
import com.czjy.chaozhi.ui.activity.user.WelcomeActivity;

/**
 * Created by huyg on 2018/9/2.
 */
public class UnCeHandler implements Thread.UncaughtExceptionHandler {


    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CatchExcep";
    App application;

    public UnCeHandler(App application){
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(App.getInstance(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }.start();
    }

}
