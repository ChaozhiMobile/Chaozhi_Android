package com.czjy.chaozhi.base;

/**
 * Created by huyg on 2018/9/28.
 */

public class BasePresenter<T extends IBaseView> implements IPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
