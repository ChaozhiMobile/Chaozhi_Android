package com.czjy.chaozhi.model.response;

import com.czjy.chaozhi.model.bean.ActivityBean;
import com.czjy.chaozhi.model.bean.BannerBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huyg on 2018/10/8.
 */
public class HomeResponse {

    @SerializedName("banner_list")
    private List<BannerBean> banners;

    @SerializedName("activity_list")
    private List<ActivityBean> activitys;


    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<ActivityBean> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<ActivityBean> activitys) {
        this.activitys = activitys;
    }
}
