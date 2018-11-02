package com.czjy.chaozhi.model.bean;

/**
 * Created by huyg on 2018/9/29.
 */
public class MineItem {

    private int sourceId;
    private String item;
    private boolean hasDot;

    public MineItem(int sourceId, String item, boolean hasDot) {
        this.sourceId = sourceId;
        this.item = item;
        this.hasDot = hasDot;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isHasDot() {
        return hasDot;
    }

    public void setHasDot(boolean hasDot) {
        this.hasDot = hasDot;
    }
}
