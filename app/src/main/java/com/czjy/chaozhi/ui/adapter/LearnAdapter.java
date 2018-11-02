package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.PurchProduct;

import java.util.List;

/**
 * Created by huyg on 2018/10/21.
 */
public class LearnAdapter extends BaseQuickAdapter<PurchProduct.NewestInfoBean.LearnCourseListBean,BaseViewHolder> {

    public LearnAdapter(int layoutResId, @Nullable List<PurchProduct.NewestInfoBean.LearnCourseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchProduct.NewestInfoBean.LearnCourseListBean item) {
        helper.setText(R.id.learn_plan_title,item.getName());
        helper.setText(R.id.learn_plan_time,item.getUt());
    }

}
