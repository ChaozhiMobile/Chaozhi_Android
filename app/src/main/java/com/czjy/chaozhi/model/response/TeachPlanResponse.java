package com.czjy.chaozhi.model.response;

import com.czjy.chaozhi.model.bean.PlanSubject;

import java.util.List;

/**
 * Created by huyg on 2018/10/21.
 */
public class TeachPlanResponse {
    private int total;
    private List<PlanSubject> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PlanSubject> getRows() {
        return rows;
    }

    public void setRows(List<PlanSubject> rows) {
        this.rows = rows;
    }
}
