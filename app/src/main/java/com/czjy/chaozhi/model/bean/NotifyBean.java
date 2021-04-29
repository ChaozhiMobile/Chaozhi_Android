package com.czjy.chaozhi.model.bean;

/**
 * author : hzb
 * e-mail :
 * time   : 2019/02/16
 * desc   : 小红点实体类
 * version: 2.7.0
 */
public class NotifyBean {

    /**
     * teacher_unread : 0 //我的班主任未读消息
     * msg_unread : 0 //我的消息未读个数
     */

    private int teacher_unread;
    private int msg_unread;

    public int getTeacher_unread() {
        return teacher_unread;
    }

    public void setTeacher_unread(int teacher_unread) {
        this.teacher_unread = teacher_unread;
    }

    public int getMsg_unread() {
        return msg_unread;
    }

    public void setMsg_unread(int msg_unread) {
        this.msg_unread = msg_unread;
    }
}
