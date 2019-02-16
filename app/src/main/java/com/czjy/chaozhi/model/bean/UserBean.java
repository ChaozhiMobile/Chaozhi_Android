package com.czjy.chaozhi.model.bean;

import com.czjy.chaozhi.global.Const;

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
     * "purchase": {
     *             "is_purchase": 1,
     *             "order_count": 13,
     *             "chat": [
     *                 {
     *                     "product_id": 10,
     *                     "product_name": "ACI注册心理咨询师-VIP保障实操班",
     *                     "chat_url": "http://kf-dev.chaozhiedu.com:88/index/index/home?visiter_id=45405&visiter_name=18268686511%28%E7%8E%8B%E5%B0%8F%E5%B0%8F%29&avatar=&business_id=admin&groupid=0&cle_info=eyJuYW1lIjoiMTgyNjg2ODY1MTEoXHU3MzhiXHU1YzBmXHU1YzBmKSIsImNsZV9udW0iOjQ1NDA1LCJ0ZWwiOiIxODI2ODY4NjUxMSIsImN0eXBlIjoiQUNJXHU2Y2U4XHU1MThjXHU1ZmMzXHU3NDA2XHU1NGE4XHU4YmUyXHU1ZTA4LVZJUFx1NGZkZFx1OTY5Y1x1NWI5ZVx1NjRjZFx1NzNlZCJ9"
     *                 }
     *             ]
     *         }
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
    private PurchaseBean purchase;

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
        if (head_img_url.contains("http")) {
            return head_img_url;
        } else {
            return Const.HTTP + head_img_url;
        }
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public PurchaseBean getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseBean purchase) {
        this.purchase = purchase;
    }
}
