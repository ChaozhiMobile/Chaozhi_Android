package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.bean.PurchProduct;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.main.contract.LearnContract;
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
public class LearnPresenter extends RxPresenter<LearnContract.View> implements LearnContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public LearnPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getPurchProducts() {
        Disposable disposable = mApiFactory.getHomeApi().getPurchProducts(1,1)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<PurchProduct>>() {
                    @Override
                    public void accept(List<PurchProduct> purchProducts) throws Exception {
                        mView.finishRefresh();
                        mView.showPurchProducts(purchProducts);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast("获取失败");
                }));
        addDispose(disposable);
    }
}
