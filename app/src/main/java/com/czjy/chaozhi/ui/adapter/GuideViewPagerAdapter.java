package com.czjy.chaozhi.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * Create by Weixf
 * Date on 2017/8/1
 * Description 引导页的ViewPager的适配器
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    //引导页面图片资源
    private  int[] pics = {R.drawable.img_welcome_1, R.drawable.img_welcome_2, R.drawable.img_welcome_3, R.drawable.img_welcome_4};
    private List<View> mViews;

    public GuideViewPagerAdapter(List<View> mViews) {
        this.mViews = mViews;
    }

    @Override
    public int getCount() {
        return mViews != null ? mViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView iv = (ImageView) mViews.get(position);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(mParams);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setBackgroundResource(pics[position]);
        container.addView(iv);
        return iv;
    }
}
