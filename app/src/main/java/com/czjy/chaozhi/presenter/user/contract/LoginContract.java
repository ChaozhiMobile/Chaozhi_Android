package com.czjy.chaozhi.presenter.user.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.response.LoginResponse;

/**
 * Created by huyg on 2018/9/28.
 */
public interface LoginContract {

    interface View extends IBaseView{
        void loginSuccess(LoginResponse response);
    }

    interface Presenter extends IPresenter<View>{
        void login(String phone,String pwd);
    }

}
