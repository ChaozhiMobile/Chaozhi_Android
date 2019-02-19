package com.czjy.chaozhi.presenter.setting;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.base.RxPresenter;
import com.czjy.chaozhi.model.http.ApiFactory;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.presenter.setting.contract.SelectSubjectContract;
import com.czjy.chaozhi.util.OkHttpUtils;
import com.czjy.chaozhi.util.rx.RxException;
import com.czjy.chaozhi.util.rx.RxResult;
import com.czjy.chaozhi.util.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2018/10/8.
 */
public class SelectSubjectPresenter extends RxPresenter<SelectSubjectContract.View> implements SelectSubjectContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public SelectSubjectPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getSubjectList() {
        Disposable disposable = mApiFactory.getHomeApi().getSubjects()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<SubjectsResponse>>() {
                    @Override
                    public void accept(List<SubjectsResponse> subjectsResponses) throws Exception {
                        mView.showSubjects(subjectsResponses);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}
