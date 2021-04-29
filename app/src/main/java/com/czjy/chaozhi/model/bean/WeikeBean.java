package com.czjy.chaozhi.model.bean;

import com.czjy.chaozhi.global.Const;

/**
 * author : hzb
 * e-mail :
 * time   : 2019/04/11
 * desc   : 微课model
 * version: 3.0.0
 */
public class WeikeBean {

    /**
     * id : 15
     * video : 6c405168211646a0be8ab4d020275e48
     * cover : //test-aci-api.chaozhiedu.com/api/file/11102
     * title : 2M
     * teacher_id : 9
     * play_num : 4
     * teacher_name : 郭聪荣
     * play_url : https://outin-5e71f4ec537b11e98d2e00163e1c7426.oss-cn-shanghai.aliyuncs.com/sv/2eff08b6-16a0a884801/2eff08b6-16a0a884801.mp4?Expires=1555047849&OSSAccessKeyId=LTAItL9Co9nUDU5r&Signature=v0%2BLfyHnNrQ4XyUMV0w2NX5KcQg%3D
     */

    private int id;
    private String video;
    private String cover;
    private String title;
    private int teacher_id;
    private int play_num;
    private String teacher_name;
    private String play_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCover() {
        if (cover.contains("http")) {
            return cover;
        } else {
            return Const.HTTP + cover;
        }
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getPlay_num() {
        return play_num;
    }

    public void setPlay_num(int play_num) {
        this.play_num = play_num;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }
}
