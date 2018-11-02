package com.czjy.chaozhi.presenter.user;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.model.response.LoginResponse;
import com.czjy.chaozhi.presenter.user.contract.LoginContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/28.
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public LoginPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void login(String phone, String pwd) {
        Disposable disposable = mApiFactory.getUserApi().login(phone, pwd)
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()) {
                            mView.loginSuccess(response);
                        } else {
                            mView.toast(response.getMsg());
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}
