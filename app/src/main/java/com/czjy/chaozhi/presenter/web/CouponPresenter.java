package com.czjy.chaozhi.presenter.web;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.presenter.web.contract.CouponContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/10/6.
 */
public class CouponPresenter extends RxPresenter<CouponContract.View> implements CouponContract.Presenter {

    @Inject
    public CouponPresenter(){

    }
}
