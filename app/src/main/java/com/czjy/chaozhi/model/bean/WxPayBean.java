package com.czjy.chaozhi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huyg on 2018/11/8.
 */
public class WxPayBean implements Parcelable {


    /**
     * appid : wxa595f547a6eeeb65
     * partnerid : 1518175721
     * prepayid : wx081023236161274d8999a0020317188657
     * noncestr : 5be39e1ba1d0f
     * timestamp : 1541643803
     * package : Sign=WXPay
     * sign : D0B3758F18FA17F0EAC015180A1676DA
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private int timestamp;
    @SerializedName("package")
    private String packageX;
    private String sign;

    protected WxPayBean(Parcel in) {
        appid = in.readString();
        partnerid = in.readString();
        prepayid = in.readString();
        noncestr = in.readString();
        timestamp = in.readInt();
        packageX = in.readString();
        sign = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appid);
        dest.writeString(partnerid);
        dest.writeString(prepayid);
        dest.writeString(noncestr);
        dest.writeInt(timestamp);
        dest.writeString(packageX);
        dest.writeString(sign);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WxPayBean> CREATOR = new Creator<WxPayBean>() {
        @Override
        public WxPayBean createFromParcel(Parcel in) {
            return new WxPayBean(in);
        }

        @Override
        public WxPayBean[] newArray(int size) {
            return new WxPayBean[size];
        }
    };

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
