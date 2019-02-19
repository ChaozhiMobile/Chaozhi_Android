package com.czjy.chaozhi.global;

/**
 * Created by huyg on 2018/9/28.
 */
public class Const {

    /**
     * 接口地址【文档：http://101.201.222.8/showdoc/web/#/1 密码：abc123】
     * 测试账号：18268686511/15737936517/15068850958 密码：123456 112233
     * 客服系统：http://kf-dev.chaozhiedu.com:88 admin/admin/qwer1234
     */

    public static final String BASE_URL = "http://test-aci-api.chaozhiedu.com/";
    public static final String H5_URL = "http://mtest.chaozhiedu.com/";
//    public static final String BASE_URL = "https://aci-api.chaozhiedu.com/";
//    public static final String H5_URL = "https://m.chaozhiedu.com/";
    public static final String PDF_URL = "https://m.chaozhiedu.com/static/pdf.html?http:";
    public static final String HTTP = "http:";

    //我的报考资料
    public static final String ROUTER_APPLY  = "#/hybrid/me/apply";
    //我的优惠券
    public static final String ROUTER_COUPON  = "#/hybrid/coupon/";
    //我的消息
    public static final String ROUTER_MESSAGE = "#/hybrid/message/";
    //我的订单
    public static final String ROUTER_ORDERS = "#/hybrid/orders/";
    //我的收藏
    public static final String ROUTER_MY_FAV = "#/hybrid/me/fav";
    //个人中心
    public static final String ROUTER_INFO = "#/hybrid/me/info/";
    //问题反馈
    public static final String ROUTER_FEEDBACK = "#/hybrid/feedback/";
    //学习题库
    public static final String ROUTER_LIBRARY = "#/hybrid/study/library/";
    //学习资料
    public static final String ROUTER_DOC = "#/hybrid/study/doc/";
    //学习直播
    public static final String ROUTER_LIVE = "#/hybrid/study/live/";
    //学习录播
    public static final String ROUTER_VIDEO = "#/hybrid/study/video/";
    //无限
    public static final String ROUTER_INFINITE = "#/hybrid/Infinite";
    //首页-每日新知
    public static final String ROUTER_NEWS = "#/hybrid/infinite/news/";
    //首页-课程
    public static final String ROUTER_PRODUCT = "#/hybrid/store/product/";
    //首页-推荐课程列表【课程分类id】
    public static final String ROUTER_STORE = "#/hybrid/store/";
    //首页-更多公开课
    public static final String ROUTER_STORE_FREE = "#/hybrid/store/free";
    //首页-讲师详情【讲师id】
    public static final String ROUTER_TEACHER_DETAIL = "#/hybrid/teacher/";
    //关于
    public static final String ROUTER_ABOUT = "#/hybrid/chaozhi/about";


    /**
     * sp
     */
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_WIFI = "wifi";


    /**
     * intent
     */
    public static final String KEY_URL = "url";
    public static final String KEY_WEB_TITLE = "title";

    /**
     * 短信验证type
     */
    public static final String CODE_TYPE_REG = "reg";
    public static final String CODE_TYPE_RESET= "reset";


    public static final int CODE_REQ= 1111;
    public static final int CODE_RESULT= 9999;

}
