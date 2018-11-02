package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.main.contract.LimitlessContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/9/29.
 */
public class LimitlessPresenter extends RxPresenter<LimitlessContract.View> implements LimitlessContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public LimitlessPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

}
