package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/10/21.
 */
public class TeachPlan {

    /**
     * time : 2018-10-19 10:28:15
     * view_url : http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDA1MjY3NSwiY291cnNlX2lkIjoiMTQwNzk3IiwidWlkIjoxMDIzOCwibmlja25hbWUiOiIxODIqKioqNjUxMSIsInJvbGUiOiJ1c2VyIiwiZXhwaXJlIjoyODgwMCwib3B0aW9ucyI6IntcInRpbWVzXCI6MTB9Iiwic2lnbiI6ImQwMGMzN2UwZDUwNTNiMTY4ZTU5YzE5NDg5MzA2MmU0In0
     * tid : 1
     * name : 第1节课
     */

    private String time;
    private String view_url;
    private int tid;
    private String name;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getView_url() {
        return view_url;
    }

    public void setView_url(String view_url) {
        this.view_url = view_url;
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
}
