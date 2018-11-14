package com.czjy.chaozhi.presenter.main.contract;

import com.czjy.chaozhi.base.IBaseView;
import com.czjy.chaozhi.base.IPresenter;
import com.czjy.chaozhi.model.bean.VersionBean;
import com.czjy.chaozhi.model.response.HomeCategoryResponse;
import com.czjy.chaozhi.model.response.HomeResponse;
import com.czjy.chaozhi.model.response.NewsResponse;

import retrofit2.http.Field;

/**
 * Created by huyg on 2018/9/29.
 */
public interface HomeContract {

    interface View extends IBaseView{
        void showHomeCategoryData(HomeCategoryResponse response);
        void showHomeData(HomeResponse response);
        void finishRefresh();
        void showNewsList(NewsResponse newsResponse);
    }

    interface Presenter extends IPresenter<View>{
        void getHomeCategoryData(int categoryId);
        void getHomeData();
        void getNewsList(int categoryId,int page);
    }
}
