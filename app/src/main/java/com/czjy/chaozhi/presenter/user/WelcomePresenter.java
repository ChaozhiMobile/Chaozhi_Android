package com.czjy.chaozhi.presenter.user;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.user.contract.WelcomeContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/9/28.
 */
public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public WelcomePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }



}
