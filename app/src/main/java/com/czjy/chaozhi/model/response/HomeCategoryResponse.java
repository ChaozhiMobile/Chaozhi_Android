package com.czjy.chaozhi.model.response;

import com.czjy.chaozhi.model.bean.ProductBean;
import com.czjy.chaozhi.model.bean.TeacherBean;
import com.czjy.chaozhi.model.bean.VideoBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huyg on 2018/10/8.
 */
public class HomeCategoryResponse {

    @SerializedName("feature_product_list")
    private List<ProductBean> products;
    @SerializedName("try_video_list")
    private List<VideoBean> videos;
    @SerializedName("teacher_list")
    private List<TeacherBean> teachers;


    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public List<VideoBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoBean> videos) {
        this.videos = videos;
    }

    public List<TeacherBean> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherBean> teachers) {
        this.teachers = teachers;
    }
}
