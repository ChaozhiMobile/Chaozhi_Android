package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/10/6.
 */
public class AgentBean {


    private String token;
    private boolean isWifi;

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
