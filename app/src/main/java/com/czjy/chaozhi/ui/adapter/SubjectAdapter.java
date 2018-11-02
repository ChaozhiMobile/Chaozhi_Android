package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.response.SubjectsResponse;

import java.util.List;

/**
 * Created by huyg on 2018/10/8.
 */
public class SubjectAdapter extends BaseQuickAdapter<SubjectsResponse,BaseViewHolder> {



    public SubjectAdapter(int layoutResId, @Nullable List<SubjectsResponse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectsResponse item) {
        helper.setText(R.id.item_subject_name,item.getName());
        if (item.isSelect()){
            helper.setVisible(R.id.item_subject_img,true);
            helper.setTextColor(R.id.item_subject_name,mContext.getResources().getColor(R.color._C31A1F));
        }else{
            helper.setVisible(R.id.item_subject_img,false);
            helper.setTextColor(R.id.item_subject_name,mContext.getResources().getColor(R.color._24253D));
        }

    }
}
