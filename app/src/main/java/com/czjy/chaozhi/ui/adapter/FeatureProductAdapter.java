package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.ProductBean;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by huyg on 2018/10/16.
 */
public class FeatureProductAdapter extends BaseQuickAdapter<ProductBean,BaseViewHolder> {

    public FeatureProductAdapter(int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
        helper.setText(R.id.item_product_text,item.getName());
        ImageView imageView = helper.getView(R.id.item_product_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext,"http:"+item.getImg(),imageView,mContext.getResources().getDrawable(R.drawable.img_feature_product));
    }
}
