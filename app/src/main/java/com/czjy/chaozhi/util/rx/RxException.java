package com.czjy.chaozhi.util.rx;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/4/24.
 */

public class RxException <T extends Throwable> implements Consumer<T> {



    private static final String TAG = "RxException";

//    private static final String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
//    private static final String CONNECTEXCEPTION = "网络连接异常，请检查您的网络状态";
//    private static final String UNKNOWNHOSTEXCEPTION = "网络异常，请检查您的网络状态";

    private static final String SOCKETTIMEOUTEXCEPTION = "检测到您的网络异常，请检查网络";
    private static final String CONNECTEXCEPTION = "检测到您的网络异常，请检查网络";
    private static final String UNKNOWNHOSTEXCEPTION = "检测到您的网络异常，请检查网络";
    private static final String OTHERERROREXCEPTION = "获取失败";

    private Consumer<? super Throwable> onError;
    public RxException(Consumer<? super Throwable> onError) {
        this.onError=onError;
    }

    /**
     * Consume the given value.
     *
     * @param t the value
     * @throws Exception on error
     */
    @Override
    public void accept(T t) throws Exception {
        Log.d(TAG,t.getMessage());
        if (t instanceof SocketTimeoutException) {
            onError.accept(new Throwable(SOCKETTIMEOUTEXCEPTION));
        } else if (t instanceof ConnectException) {
            onError.accept(new Throwable(CONNECTEXCEPTION));
        } else if (t instanceof UnknownHostException) {
            onError.accept(new Throwable(UNKNOWNHOSTEXCEPTION));
        } else {
//            onError.accept(t);
            onError.accept(new Throwable(OTHERERROREXCEPTION));
        }
    }
}
