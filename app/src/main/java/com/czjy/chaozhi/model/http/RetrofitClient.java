package com.czjy.chaozhi.model.http;


import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.http.converter.StringConverterFactory;
import com.czjy.chaozhi.model.http.interceptor.LoggingInterceptor;
import com.czjy.chaozhi.model.http.interceptor.ParamsInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by huyg on 2018/9/28.
 */

public class RetrofitClient {

    private static final int HTTP_CONNECT_TIMEOUT = 10;
    private static final int HTTP_WRITE_TIMEOUT = 10;
    private static final int HTTP_READ_TIMEOUT = 20;
    private Retrofit mRetrofit;

    private static final class RetrofitClientHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return RetrofitClientHolder.INSTANCE;
    }

    private RetrofitClient() {
        createRetrofit();
    }

    private void createRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.addInterceptor(new ParamsInterceptor());
        //builder.addInterceptor(new CacheInterceptor());

        builder.addInterceptor(new LoggingInterceptor());

        //设置超时
        builder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(builder.build())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T create(Class<?> clazz) {
        return (T) mRetrofit.create(clazz);
    }

}
