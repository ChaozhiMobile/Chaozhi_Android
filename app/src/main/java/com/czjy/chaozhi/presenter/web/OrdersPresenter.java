package com.czjy.chaozhi.presenter.web;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.presenter.web.contract.OrdersContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/10/6.
 */
public class OrdersPresenter extends RxPresenter<OrdersContract.View> implements OrdersContract.Presenter {

    @Inject
    public OrdersPresenter(){

    }
}
