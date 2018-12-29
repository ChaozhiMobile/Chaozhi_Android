package com.czjy.chaozhi.model.http.api;

import com.czjy.chaozhi.model.bean.PurchaseBean;
import com.czjy.chaozhi.model.bean.UserBean;
import com.czjy.chaozhi.model.http.BaseResponse;
import com.czjy.chaozhi.model.http.HttpResponse;
import com.czjy.chaozhi.model.response.LoginResponse;
import com.czjy.chaozhi.model.response.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2018/9/27.
 */
public interface UserApi {


    @FormUrlEncoded
    @POST("api/user/login")
    Observable<LoginResponse> login(@Field("phone") String phone,
                                    @Field("password") String password);



    @FormUrlEncoded
    @POST("api/user/reg")
    Observable<RegisterResponse> register(@Field("phone") String phone,
                                          @Field("captcha") String captcha,
                                          @Field("password") String password,
                                          @Field("name") String name);

    @FormUrlEncoded
    @POST("api/user/reset")
    Observable<BaseResponse> reset(@Field("phone") String phone,
                                   @Field("password") String password,
                                   @Field("captcha") String captcha);

    @FormUrlEncoded
    @POST("api/user/info")
    Observable<HttpResponse<UserBean>> getUserInfo(@Field("data") String data);

    @FormUrlEncoded
    @POST("api/phone-captcha")
    Observable<BaseResponse> getCode(@Field("phone") String phone,
                                     @Field("type") String type);

    /**
     * 报班状态
     */
    @FormUrlEncoded
    @POST("api/user/purchase-status")
    Observable<HttpResponse<PurchaseBean>> getPurchaseStatus(@Field("data") String data);
}
