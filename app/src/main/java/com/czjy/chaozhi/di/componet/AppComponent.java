package com.czjy.chaozhi.di.componet;


import com.czjy.chaozhi.di.module.AppModule;
import com.czjy.chaozhi.model.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by huyg on 2018/9/28.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    ApiFactory ApiFactory();
}
