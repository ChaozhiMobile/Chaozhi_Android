package com.czjy.chaozhi.model.http.api;

import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.model.bean.PurchProduct;
import com.czjy.chaozhi.model.bean.VersionBean;
import com.czjy.chaozhi.model.http.HttpResponse;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.model.response.HomeCategoryResponse;
import com.czjy.chaozhi.model.response.HomeResponse;
import com.czjy.chaozhi.model.response.NewsResponse;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.model.response.TeachPlanResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2018/10/8.
 */
public interface HomeApi {

    @FormUrlEncoded
    @POST("api/app/check-version")
    Observable<HttpResponse<VersionBean>> checkVersion(@Field("device") String device,
                                                       @Field("version") String version);

    @FormUrlEncoded
    @POST("api/app/home-category")
    Observable<HttpResponse<HomeCategoryResponse>> getHomeCategoryData(@Field("category_id") int categoryId);

    @POST("api/app/home")
    Observable<HttpResponse<HomeResponse>> getHomeData();

    @POST("api/category/list")
    Observable<HttpResponse<List<SubjectsResponse>>> getSubjects();

    @FormUrlEncoded
    @POST("api/course/plan")
    Observable<HttpResponse<List<TeachPlanResponse>>> getTeachPlan(@Field("pid") String pid,
                                                                   @Field("p") int p);

    /**
     * 已购课程
     */
    @FormUrlEncoded
    @POST("api/course/list")
    Observable<HttpResponse<List<PurchProduct>>> getPurchProducts(@Field("is_newest_info") int newInfo,
                                                                  @Field("is_progress") int progress);

    /**
     * 新闻列表
     */
    @FormUrlEncoded
    @POST("api/news/list")
    Observable<HttpResponse<NewsResponse>> getNewsList(
            @Field("category_id") int categoryId, @Field("p") int page,
            @Field("offset") int pageSize
    );

    /**
     * 资料库
     */
    @FormUrlEncoded
    @POST("api/course/information")
    Observable<HttpResponse<DataLibraryResponse>> getDataLibraryList(
            @Field("pid") int pid, @Field("p") int page,
            @Field("offset") int pageSize
    );
}
