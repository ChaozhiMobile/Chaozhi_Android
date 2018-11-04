package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/9/28.
 */
public class UserBean {


    /**
     * id : 10207
     * status : 0
     * buy_flag : 1
     * phone : 18657467190
     * cn_name :
     * sex : M
     * birthday : 1988-08-08
     * email :
     * province : 浙江省
     * city : 宁波市
     * county : 鄞州区
     * addr : 鄞县大道
     * head_img : 0
     * nickname : test
     * autograph : null
     * brief : null
     * head_img_url :
     */

    private int id;
    private int status;
    private int buy_flag;
    private String phone;
    private String cn_name;
    private String sex;
    private String birthday;
    private String email;
    private String province;
    private String city;
    private String county;
    private String addr;
    private int head_img;
    private String nickname;
    private Object autograph;
    private Object brief;
    private String head_img_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBuy_flag() {
        return buy_flag;
    }

    public void setBuy_flag(int buy_flag) {
        this.buy_flag = buy_flag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getHead_img() {
        return head_img;
    }

    public void setHead_img(int head_img) {
        this.head_img = head_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getAutograph() {
        return autograph;
    }

    public void setAutograph(Object autograph) {
        this.autograph = autograph;
    }

    public Object getBrief() {
        return brief;
    }

    public void setBrief(Object brief) {
        this.brief = brief;
    }

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        if (head_img_url.contains("http")) {
            this.head_img_url = head_img_url;
        } else {
            this.head_img_url = "http:" + head_img_url;

        }

    }
}
