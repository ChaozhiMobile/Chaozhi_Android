package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.presenter.main.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/9/29.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {


    @Inject
    public MainPresenter() {

    }

}
