package com.czjy.chaozhi.di.module;


import com.czjy.chaozhi.model.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/9/28.
 */

@Module
public class AppModule {
    @Provides
    @Singleton
    ApiFactory provideRetrofitHelper() {
        return new ApiFactory();
    }
}
