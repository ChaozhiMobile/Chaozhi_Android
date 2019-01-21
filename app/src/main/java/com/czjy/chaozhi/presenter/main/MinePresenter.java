package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.bean.PurchProduct;
import com.czjy.chaozhi.model.bean.PurchaseBean;
import com.czjy.chaozhi.model.bean.UserBean;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.main.contract.MineContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxResult;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/29.
 */
public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public MinePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getUserInfo() {
        Disposable disposable = mApiFactory.getUserApi().getUserInfo("")
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean userBean) throws Exception {
                        mView.showUserInfo(userBean);
                    }
                }, new RxException<>(e -> {
                    mView.toast("获取失败");
                }));
        addDispose(disposable);
    }
}
