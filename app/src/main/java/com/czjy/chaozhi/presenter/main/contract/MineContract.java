package com.czjy.chaozhi.presenter.main.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.bean.NotifyBean;
import com.czjy.chaozhi.model.bean.PurchaseBean;
import com.czjy.chaozhi.model.bean.UserBean;

/**
 * Created by huyg on 2018/9/29.
 */
public interface MineContract {

    interface View extends IBaseView{
        void showUserInfo(UserBean userBean);
        void showNotifyInfo(NotifyBean notifyBean);
    }

    interface Presenter extends IPresenter<View>{
        void getUserInfo();
        void getNotifyInfo();
    }
}
