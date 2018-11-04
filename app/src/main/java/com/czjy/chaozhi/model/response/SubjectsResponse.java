package com.czjy.chaozhi.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by huyg on 2018/10/8.
 */
public class SubjectsResponse {


    /**
     * id : 38
     * img : //test-aci-api.chaozhiedu.com/api/file/10806
     * name : 职业教育
     * rank : 3
     * pid : 0
     * sub-count : 4
     * children : []
     */

    private int id;
    private String img;
    private String name;
    private int rank;
    private int pid;
    @SerializedName("sub-count")
    private int subCount;
    private List<Subject> children;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        if (img.contains("http")) {
            return img;
        } else {
            return "http:" + img;
        }
    }

    public void setImg(String img) {
        this.img = img;
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

    public int getSubcount() {
        return subCount;
    }

    public void setSubcount(int subcount) {
        this.subCount = subcount;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
    }
}
