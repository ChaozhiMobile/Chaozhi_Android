package com.czjy.chaozhi.presenter.user.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;

/**
 * Created by huyg on 2018/9/28.
 */
public interface ResetContract {

    interface View extends IBaseView {
        void resetSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void resetPwd(String phone, String code, String pwd);

        void getCode(String phone, String type);

    }


}
