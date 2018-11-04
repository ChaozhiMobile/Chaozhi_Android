package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/10/8.
 */
public class TeacherBean {

    /**
     * id : 9
     * name : 郭聪荣
     * photo : //test-aci-api.chaozhiedu.com/api/file/10751
     * info : 心理学硕士，心理专家。北京中医药大学心理讲师，共青团心理辅导员培训与督导师 。
     */

    private int id;
    private String name;
    private String photo;
    private String info;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        if (photo.contains("http")){
            this.photo = photo;
        }else{
            this.photo = "http:"+photo;
        }

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
