package com.czjy.chaozhi.presenter.user.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;

/**
 * Created by huyg on 2018/9/28.
 */
public interface RegContract {

    interface View extends IBaseView{
        void regSuccess();
    }

    interface Presenter extends IPresenter<View>{
        void register(String phone,String code,String pwd,String name);
        void getCode(String phone,String type);
    }


}
