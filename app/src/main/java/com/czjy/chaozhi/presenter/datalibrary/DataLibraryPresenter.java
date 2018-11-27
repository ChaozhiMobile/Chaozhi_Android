package com.czjy.chaozhi.presenter.datalibrary;

import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.model.response.NewsResponse;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.presenter.datalibrary.contract.DataLibraryContract;
import com.czjy.chaozhi.presenter.setting.contract.SelectSubjectContract;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxResult;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DataLibraryPresenter extends RxPresenter<DataLibraryContract.View> implements DataLibraryContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public DataLibraryPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getDataLibraryList(int pid, int page, int pageSize) {
        Disposable disposable = mApiFactory.getHomeApi().getDataLibraryList(pid,page,pageSize)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<DataLibraryResponse>() {
                    @Override
                    public void accept(DataLibraryResponse dataLibraryResponse) throws Exception {
                        mView.showDataLibraryList(dataLibraryResponse);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

}
