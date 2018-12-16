package com.czjy.chaozhi.model.bean;

public class DataLibraryBean {

    /**
     * file_id : 10382
     * file_name : 心理咨询师教材上1部分
     * file : //test-aci-api.chaozhiedu.com/api/file/4hb6vgkbtp8-141455
     */

    private int file_id;
    private String file_name;
    private String file;
    private String file_localurl;
    private String file_size;
    private int progress=-1;

    public DataLibraryBean(int file_id, String file_name, String file, String file_localurl, String file_size) {
        this.file_id = file_id;
        this.file_name = file_name;
        this.file = file;
        this.file_localurl = file_localurl;
        this.file_size = file_size;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile_localurl() {
        return file_localurl;
    }

    public void setFile_localurl(String file_localurl) {
        this.file_localurl = file_localurl;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
}
