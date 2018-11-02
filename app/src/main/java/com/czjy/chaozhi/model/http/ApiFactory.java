package com.czjy.chaozhi.model.http;


import com.czjy.chaozhi.model.http.api.HomeApi;
import com.czjy.chaozhi.model.http.api.UserApi;

/**
 * Created by huyg on 2018/9/28.
 */

public class ApiFactory {

    private UserApi mUserApi;
    private HomeApi mHomeApi;

    public UserApi getUserApi() {
        return mUserApi;
    }

    public HomeApi getHomeApi(){
        return mHomeApi;
    }

    public ApiFactory() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        mUserApi = retrofitClient.create(UserApi.class);
        mHomeApi = retrofitClient.create(HomeApi.class);
    }

}
