package com.czjy.chaozhi.model.bean;

import com.czjy.chaozhi.global.Const;

/**
 * Created by huyg on 2018/10/8.
 */
public class VideoBean {

    /**
     * img : https://www.chaozhiedu.com/static/images/vedio-pic.png
     * title : 婚姻公开课
     * teacher : 小跃老师
     * time : 43:12
     * src : https://open.talk-fun.com/playout/PTk2OigjbipoIi8.html?st=VpPmVb32XJ80sVZcwYVATA&e=1539772257&from=cms101341
     */

    private String img;
    private String title;
    private String teacher;
    private String time;
    private String src;

    public String getImg() {
        if (img.contains("http")) {
            return img;
        } else {
            return Const.HTTP + img;
        }
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
