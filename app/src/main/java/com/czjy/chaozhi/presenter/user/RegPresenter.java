package com.czjy.chaozhi.presenter.user;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.model.http.BaseResponse;
import com.czjy.chaozhi.model.response.RegisterResponse;
import com.czjy.chaozhi.presenter.user.contract.RegContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/28.
 */
public class RegPresenter extends RxPresenter<RegContract.View> implements RegContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public RegPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void register(String phone, String code, String pwd, String name) {
        Disposable disposable = mApiFactory.getUserApi()
                .register(phone, code, pwd, name)
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()) {
                            mView.regSuccess();
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

    @Override
    public void getCode(String phone, String type) {
        Disposable disposable = mApiFactory.getUserApi()
                .getCode(phone, type)
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()) {
                            mView.toast("验证码发送成功");
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
