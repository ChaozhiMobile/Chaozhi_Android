package com.czjy.chaozhi.base;

/**
 * Created by huyg on 2018/9/28.
 */

public interface IPresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView();
}
