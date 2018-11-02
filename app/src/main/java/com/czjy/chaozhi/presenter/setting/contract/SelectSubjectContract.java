package com.czjy.chaozhi.presenter.setting.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.response.SubjectsResponse;

import java.util.List;

/**
 * Created by huyg on 2018/10/8.
 */
public interface SelectSubjectContract {


    interface View extends IBaseView{
        void showSubjects(List<SubjectsResponse> subjects);
    }

    interface Presenter extends IPresenter<View>{
        void getSubjectList();
    }


}
