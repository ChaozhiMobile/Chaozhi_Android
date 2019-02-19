package com.czjy.chaozhi.jpush;

/**
 * Create by Weixf
 * Date on 2017/6/23
 * Description 极光推送的类型
 */
public class PushTypeEnum {

    private int type;
    private String msg;

    private PushTypeEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
