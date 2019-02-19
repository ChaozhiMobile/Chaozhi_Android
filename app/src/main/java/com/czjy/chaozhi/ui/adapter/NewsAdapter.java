package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.NewBean;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by huyg on 2018/10/21.
 */
public class NewsAdapter extends BaseQuickAdapter<NewBean,BaseViewHolder> {

    public NewsAdapter(int layoutResId, @Nullable List<NewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewBean item) {
        ImageView imageView = helper.getView(R.id.item_new_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext,item.getImg(),imageView);
        helper.setText(R.id.item_new_title,item.getTitle());
        helper.setText(R.id.item_new_content,Html.fromHtml(item.getSubtitle()));
        helper.setText(R.id.tv_time,item.getCt());
    }
}
