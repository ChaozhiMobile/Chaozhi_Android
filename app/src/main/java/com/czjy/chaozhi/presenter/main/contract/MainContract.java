package com.czjy.chaozhi.presenter.main.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.bean.VersionBean;

/**
 * Created by huyg on 2018/9/29.
 */
public interface MainContract {

    interface View extends IBaseView{
        void getVersion(VersionBean versionBean);
    }

    interface Presenter extends IPresenter<View>{
        void checkVersion(String device, String version);
    }
}
