package com.czjy.chaozhi.model.response;

import com.czjy.chaozhi.model.http.BaseResponse;

/**
 * Created by huyg on 2018/9/28.
 */
public class RegisterResponse extends BaseResponse{
    private String token;
    private String expired;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
