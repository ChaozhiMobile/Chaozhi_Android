package com.czjy.chaozhi.presenter.main;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.model.response.HomeCategoryResponse;
import com.czjy.chaozhi.model.response.HomeResponse;
import com.czjy.chaozhi.model.response.NewsResponse;
import com.czjy.chaozhi.presenter.main.contract.HomeContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxResult;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/9/29.
 */
public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public HomePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getHomeCategoryData(int categoryId) {
        Disposable disposable = mApiFactory.getHomeApi().getHomeCategoryData(categoryId)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<HomeCategoryResponse>() {
                    @Override
                    public void accept(HomeCategoryResponse homeResponse) throws Exception {
                        mView.finishRefresh();
                        mView.showHomeCategoryData(homeResponse);
                    }
                },new RxException<>(e->{
                    mView.finishRefresh();
                    mView.toast("获取失败");
                }));
        addDispose(disposable);
    }

    @Override
    public void getHomeData() {
        Disposable disposable = mApiFactory.getHomeApi().getHomeData()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<HomeResponse>() {
                    @Override
                    public void accept(HomeResponse response) throws Exception {
                        mView.finishRefresh();
                        mView.showHomeData(response);
                    }
                },new RxException<>(e->{
                    mView.finishRefresh();
                    mView.toast("获取失败");
                }));
        addDispose(disposable);
    }

    @Override
    public void getNewsList(int categoryId,int page) {
        Disposable disposable = mApiFactory.getHomeApi().getNewsList(categoryId,page,10)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(NewsResponse newsResponse) throws Exception {
                        mView.showNewsList(newsResponse);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}
