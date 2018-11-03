package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.ActivityBean;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by huyg on 2018/10/16.
 */
public class ActivityAdapter extends BaseQuickAdapter<ActivityBean,BaseViewHolder> {

    public ActivityAdapter(int layoutResId, @Nullable List<ActivityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityBean item) {
        ImageView imageView = helper.getView(R.id.item_activity_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, "http:"+item.getImg(),imageView);
        helper.setText(R.id.item_activity_title,item.getTitle());
        helper.setText(R.id.item_activity_content,item.getSubtitle());
    }
}
