package com.czjy.chaozhi.presenter.web;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.presenter.web.contract.WebDetailContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/10/6.
 */
public class WebDetailPresenter extends RxPresenter<WebDetailContract.View> implements WebDetailContract.Presenter {

    @Inject
    public WebDetailPresenter(){

    }
}
