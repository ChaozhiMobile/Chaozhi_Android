package com.czjy.chaozhi.presenter.setting;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.setting.contract.SettingContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/10/8.
 */
public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public SettingPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }





}
