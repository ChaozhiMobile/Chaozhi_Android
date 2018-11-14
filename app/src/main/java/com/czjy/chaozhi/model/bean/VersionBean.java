package com.czjy.chaozhi.model.bean;

public class VersionBean {

    /**
     * version : 1.2.0
     * grade : 3
     * title : 超值教育
     * note : 1、全新版本;2、海量题库
     * url : https://www.chaozhiedu.com
     */

    private String version;
    private int grade;
    private String title;
    private String note;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
