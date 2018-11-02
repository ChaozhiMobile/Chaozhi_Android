package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/10/8.
 */
public class SubjectBean {

    /**
     * id : 34
     * name : 一级注册消防工程师
     * rank : 10
     * pid : 40
     */

    private int id;
    private String name;
    private int rank;
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
