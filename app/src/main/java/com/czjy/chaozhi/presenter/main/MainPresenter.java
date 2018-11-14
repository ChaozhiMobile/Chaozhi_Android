package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.bean.VersionBean;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.presenter.main.contract.MainContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxResult;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/29.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public MainPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void checkVersion(String device, String version) {
        Disposable disposable = mApiFactory.getHomeApi().checkVersion(device,version)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean versionBean) throws Exception {
                        mView.getVersion(versionBean);
                    }
                },new RxException<>(e->{
                    mView.toast("获取失败");
                }));
        addDispose(disposable);
    }
}
