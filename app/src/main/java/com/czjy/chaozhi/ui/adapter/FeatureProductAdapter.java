package com.czjy.chaozhi.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
        helper.setText(R.id.tv_comment,String.valueOf(item.getReview_num()));
        helper.setText(R.id.tv_money,item.getOriginal_price());
        helper.setText(R.id.tv_discount,item.getPrice());
        TextView tvDiscount = helper.getView(R.id.tv_discount);
        tvDiscount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        RatingBar ratingBar = helper.getView(R.id.ratingBar);
        ratingBar.setRating(item.getReview_star());
        ImageView imageView = helper.getView(R.id.item_product_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext,item.getImg(),imageView,mContext.getResources().getDrawable(R.mipmap.default_course));
    }
}
