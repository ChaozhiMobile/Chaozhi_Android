package com.czjy.chaozhi.presenter.datalibrary.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.presenter.setting.contract.SelectSubjectContract;

import java.util.List;

public interface DataLibraryContract {

    interface View extends IBaseView {
        void finishRefresh();
        void showDataLibraryList(DataLibraryResponse dataLibraryResponse);
    }

    interface Presenter extends IPresenter<View> {
        void getDataLibraryList(int pid,int page,int pageSize);
    }

}
