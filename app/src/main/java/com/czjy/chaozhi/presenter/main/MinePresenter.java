package com.czjy.chaozhi.presenter.main;

import android.text.TextUtils;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.bean.NotifyBean;
import com.czjy.chaozhi.model.bean.PurchProduct;
import com.czjy.chaozhi.model.bean.PurchaseBean;
import com.czjy.chaozhi.model.bean.UserBean;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.main.contract.MineContract;
import com.czjy.chaozhi.util.OkHttpUtils;
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
        if (!TextUtils.isEmpty(App.getInstance().getToken())) {
            Disposable disposable = mApiFactory.getUserApi().getUserInfo("")
                    .compose(RxSchedulers.io_main())
                    .compose(RxResult.handleResult())
                    .subscribe(new Consumer<UserBean>() {
                        @Override
                        public void accept(UserBean userBean) throws Exception {
                            mView.showUserInfo(userBean);
                        }
                    }, new RxException<>(e -> {
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }

    @Override
    public void getNotifyInfo() {
        if (!TextUtils.isEmpty(App.getInstance().getToken())) {
            Disposable disposable = mApiFactory.getUserApi().getNotifyInfo("")
                    .compose(RxSchedulers.io_main())
                    .compose(RxResult.handleResult())
                    .subscribe(new Consumer<NotifyBean>() {
                        @Override
                        public void accept(NotifyBean notifyBean) throws Exception {
                            mView.showNotifyInfo(notifyBean);
                        }
                    }, new RxException<>(e -> {
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }
}
