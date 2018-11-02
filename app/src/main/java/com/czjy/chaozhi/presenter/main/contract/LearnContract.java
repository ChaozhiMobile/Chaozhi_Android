package com.czjy.chaozhi.presenter.main.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.bean.PurchProduct;

import java.util.List;

/**
 * Created by huyg on 2018/9/29.
 */
public interface LearnContract {

    interface View extends IBaseView {
        void showPurchProducts(List<PurchProduct> purchProducts);
        void finishRefresh();
    }

    interface Presenter extends IPresenter<View> {
        void getPurchProducts();
    }

}
