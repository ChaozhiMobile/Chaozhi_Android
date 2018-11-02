package com.czjy.chaozhi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by huyg on 2018/10/21.
 */
public class PurchProduct implements Parcelable {


    /**
     * id : 557
     * product_id : 9
     * product_name : ACI注册心理咨询师-高效私教取证班
     * category_id : 2
     * category_name : ACI注册国际心理咨询师
     * price : 488000
     * endtime : 2018-08-19 20:12:35
     * product_img : //test-aci-api.chaozhiedu.com/api/file/41hfax0f0aym-211608
     * user_time : 100
     * user_question : 100
     * product_sub_name : 理论知识与实操同步学习
     * newest_info : {"live_list":[{"id":38,"category_id":2,"product_id":"31,8,9,10","live_id":141575,"live_name":"普通心理学第一节","live_st":"2018-07-02 19:00:00","live_et":"2018-07-02 21:00:00","create_at":"2018-07-04 10:57:37","live_url":"http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDEwOTI5OSwiY291cnNlX2lkIjoxNDE1NzUsInVpZCI6MTAyMzgsIm5pY2tuYW1lIjoiMTgyKioqKjY1MTEiLCJyb2xlIjoidXNlciIsImV4cGlyZSI6Mjg4MDAsIm9wdGlvbnMiOiJ7XCJ0aW1lc1wiOjEwfSIsInNpZ24iOiI2MDJkYzM2NzMxZTVlMTkzYzc1ZTRmMzJhZjFlNDI1NSJ9","status":-1,"teacher":"小跃"}],"learn_course_list":[{"id":457,"tid":1,"name":"消防工程师一级公开课","view_url":"http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDEwOTI5OSwiY291cnNlX2lkIjoxNDA3OTcsInVpZCI6MTAyMzgsIm5pY2tuYW1lIjoiMTgyKioqKjY1MTEiLCJyb2xlIjoidXNlciIsImV4cGlyZSI6Mjg4MDAsIm9wdGlvbnMiOiJ7XCJ0aW1lc1wiOjEwfSIsInNpZ24iOiJjNDQ4NjBmMDZkM2IyODU4M2MwNjlkMGExZmM4ZWI5NyJ9","ut":"2018-06-29 15:02:20"}]}
     */

    private int id;
    private int product_id;
    private String product_name;
    private int category_id;
    private String category_name;
    private int price;
    private String endtime;
    private String product_img;
    private int user_time;
    private int user_question;
    private String product_sub_name;
    private NewestInfoBean newest_info;

    protected PurchProduct(Parcel in) {
        id = in.readInt();
        product_id = in.readInt();
        product_name = in.readString();
        category_id = in.readInt();
        category_name = in.readString();
        price = in.readInt();
        endtime = in.readString();
        product_img = in.readString();
        user_time = in.readInt();
        user_question = in.readInt();
        product_sub_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(product_id);
        dest.writeString(product_name);
        dest.writeInt(category_id);
        dest.writeString(category_name);
        dest.writeInt(price);
        dest.writeString(endtime);
        dest.writeString(product_img);
        dest.writeInt(user_time);
        dest.writeInt(user_question);
        dest.writeString(product_sub_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PurchProduct> CREATOR = new Creator<PurchProduct>() {
        @Override
        public PurchProduct createFromParcel(Parcel in) {
            return new PurchProduct(in);
        }

        @Override
        public PurchProduct[] newArray(int size) {
            return new PurchProduct[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public int getUser_time() {
        return user_time;
    }

    public void setUser_time(int user_time) {
        this.user_time = user_time;
    }

    public int getUser_question() {
        return user_question;
    }

    public void setUser_question(int user_question) {
        this.user_question = user_question;
    }

    public String getProduct_sub_name() {
        return product_sub_name;
    }

    public void setProduct_sub_name(String product_sub_name) {
        this.product_sub_name = product_sub_name;
    }

    public NewestInfoBean getNewest_info() {
        return newest_info;
    }

    public void setNewest_info(NewestInfoBean newest_info) {
        this.newest_info = newest_info;
    }

    public static class NewestInfoBean {
        private List<LiveListBean> live_list;
        private List<LearnCourseListBean> learn_course_list;

        public List<LiveListBean> getLive_list() {
            return live_list;
        }

        public void setLive_list(List<LiveListBean> live_list) {
            this.live_list = live_list;
        }

        public List<LearnCourseListBean> getLearn_course_list() {
            return learn_course_list;
        }

        public void setLearn_course_list(List<LearnCourseListBean> learn_course_list) {
            this.learn_course_list = learn_course_list;
        }

        public static class LiveListBean {
            /**
             * id : 38
             * category_id : 2
             * product_id : 31,8,9,10
             * live_id : 141575
             * live_name : 普通心理学第一节
             * live_st : 2018-07-02 19:00:00
             * live_et : 2018-07-02 21:00:00
             * create_at : 2018-07-04 10:57:37
             * live_url : http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDEwOTI5OSwiY291cnNlX2lkIjoxNDE1NzUsInVpZCI6MTAyMzgsIm5pY2tuYW1lIjoiMTgyKioqKjY1MTEiLCJyb2xlIjoidXNlciIsImV4cGlyZSI6Mjg4MDAsIm9wdGlvbnMiOiJ7XCJ0aW1lc1wiOjEwfSIsInNpZ24iOiI2MDJkYzM2NzMxZTVlMTkzYzc1ZTRmMzJhZjFlNDI1NSJ9
             * status : -1
             * teacher : 小跃
             */

            private int id;
            private int category_id;
            private String product_id;
            private int live_id;
            private String live_name;
            private String live_st;
            private String live_et;
            private String create_at;
            private String live_url;
            private int status;
            private String teacher;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public int getLive_id() {
                return live_id;
            }

            public void setLive_id(int live_id) {
                this.live_id = live_id;
            }

            public String getLive_name() {
                return live_name;
            }

            public void setLive_name(String live_name) {
                this.live_name = live_name;
            }

            public String getLive_st() {
                return live_st;
            }

            public void setLive_st(String live_st) {
                this.live_st = live_st;
            }

            public String getLive_et() {
                return live_et;
            }

            public void setLive_et(String live_et) {
                this.live_et = live_et;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getLive_url() {
                return live_url;
            }

            public void setLive_url(String live_url) {
                this.live_url = live_url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTeacher() {
                return teacher;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }
        }

        public static class LearnCourseListBean {
            /**
             * id : 457
             * tid : 1
             * name : 消防工程师一级公开课
             * view_url : http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDEwOTI5OSwiY291cnNlX2lkIjoxNDA3OTcsInVpZCI6MTAyMzgsIm5pY2tuYW1lIjoiMTgyKioqKjY1MTEiLCJyb2xlIjoidXNlciIsImV4cGlyZSI6Mjg4MDAsIm9wdGlvbnMiOiJ7XCJ0aW1lc1wiOjEwfSIsInNpZ24iOiJjNDQ4NjBmMDZkM2IyODU4M2MwNjlkMGExZmM4ZWI5NyJ9
             * ut : 2018-06-29 15:02:20
             */

            private int id;
            private int tid;
            private String name;
            private String view_url;
            private String ut;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getView_url() {
                return view_url;
            }

            public void setView_url(String view_url) {
                this.view_url = view_url;
            }

            public String getUt() {
                return ut;
            }

            public void setUt(String ut) {
                this.ut = ut;
            }
        }
    }
}
