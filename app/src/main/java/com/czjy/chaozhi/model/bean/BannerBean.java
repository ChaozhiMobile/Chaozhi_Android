package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/10/8.
 */
public class BannerBean {


    /**
     * id : 13
     * title : 1
     * content : 111
     * img : //test-aci-api.chaozhiedu.com/api/file/10791
     * flag : product
     * param : 8
     */

    private int id;
    private String title;
    private String content;
    private String img;
    private String flag;
    private String param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
