package com.czjy.chaozhi.model.response;

import com.czjy.chaozhi.model.bean.DataLibraryBean;

import java.util.List;

public class DataLibraryResponse {

    /**
     * total : 9
     * rows : [{"file_id":10382,"file_name":"心理咨询师教材上1部分","file":"//test-aci-api.chaozhiedu.com/api/file/4hb6vgkbtp8-141455"}]
     */

    private int total;
    private List<DataLibraryBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataLibraryBean> getRows() {
        return rows;
    }

    public void setRows(List<DataLibraryBean> rows) {
        this.rows = rows;
    }
}
