package com.czjy.chaozhi.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.NoActionBarActivity;
import com.czjy.chaozhi.ui.adapter.GuideViewPagerAdapter;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by Weixf
 * Date on 2017/8/1
 * Description 引导页
 */
public class GuideActivity extends NoActionBarActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.guide_start)
    TextView ivGuideStart;
    //引导页面图片资源

    private GuideViewPagerAdapter mAdapter;
    private List<View> mViews;


    public static void actionStart(Context context) {
        Intent i = new Intent(context, GuideActivity.class);
        context.startActivity(i);
    }


    @OnClick(R.id.guide_start)
    public void onViewClicked() {
        SharedPreferencesUtils.setParam(mContext, "isFirst", false);
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            ivGuideStart.setVisibility(View.VISIBLE);
        } else {
            ivGuideStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        mViews = new ArrayList<>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        for (int i = 0; i < 4; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
        }
        mAdapter = new GuideViewPagerAdapter(mViews,mContext);
        vpGuide.setAdapter(mAdapter);
        vpGuide.addOnPageChangeListener(this);
    }
}
