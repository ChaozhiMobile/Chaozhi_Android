package com.czjy.chaozhi.presenter.web;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.presenter.web.contract.MessageContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2018/10/6.
 */
public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {

    @Inject
    public MessagePresenter(){

    }
}
