package com.czjy.chaozhi.ui.fragment.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseFragment;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.ActivityBean;
import com.czjy.chaozhi.model.bean.BannerBean;
import com.czjy.chaozhi.model.bean.NewBean;
import com.czjy.chaozhi.model.bean.ProductBean;
import com.czjy.chaozhi.model.bean.TeacherBean;
import com.czjy.chaozhi.model.bean.VideoBean;
import com.czjy.chaozhi.model.event.UpdateFgEvent;
import com.czjy.chaozhi.model.response.HomeCategoryResponse;
import com.czjy.chaozhi.model.response.HomeResponse;
import com.czjy.chaozhi.model.response.NewsResponse;
import com.czjy.chaozhi.presenter.main.HomePresenter;
import com.czjy.chaozhi.presenter.main.contract.HomeContract;
import com.czjy.chaozhi.ui.activity.setting.SelectSubjectActivity;
import com.czjy.chaozhi.ui.activity.web.SimpleWebActivity;
import com.czjy.chaozhi.ui.activity.web.WebDetailActivity;
import com.czjy.chaozhi.ui.adapter.ActivityAdapter;
import com.czjy.chaozhi.ui.adapter.FeatureProductAdapter;
import com.czjy.chaozhi.ui.adapter.NewsAdapter;
import com.czjy.chaozhi.ui.adapter.decoration.DividerGridItemDecoration;
import com.czjy.chaozhi.util.CommonUtil;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;
import com.czjy.chaozhi.util.glide.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2018/9/29.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.home_banner)
    Banner mBanner;
    @BindView(R.id.subject_recycler)
    RecyclerView mSubjectRecycler;
    @BindView(R.id.class_teacher_avatar)
    ImageView mTeacherAvatar;
    @BindView(R.id.class_name)
    TextView mClassName;
    @BindView(R.id.class_teacher)
    TextView mClassTeacher;
    @BindView(R.id.public_class_try)
    TextView mClassTry;
    @BindView(R.id.day_news_recycler)
    RecyclerView mNewsRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.public_class_layout)
    ConstraintLayout mClassLayout;
    @BindView(R.id.activity_recycler)
    RecyclerView mAcRecycler;
    @BindView(R.id.activity_layout)
    ConstraintLayout mAcLayout;
    @BindView(R.id.teacher_layout)
    ConstraintLayout mTeacherLayout;
    @BindView(R.id.home_teachers)
    LinearLayout mTeacherScroll;
    @BindView(R.id.attention_subject_layout)
    ConstraintLayout mSubjectLayout;
    @BindView(R.id.day_news_layout)
    ConstraintLayout mNewsLayout;


    @OnClick({R.id.home_menu, R.id.subject_more_layout, R.id.public_subject_more_layout, R.id.public_class_try})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_menu:
                Intent intent = new Intent(mContext, SelectSubjectActivity.class);
                startActivityForResult(intent, Const.CODE_REQ);
                break;
            case R.id.subject_more_layout:
                WebDetailActivity.action(mContext, Const.ROUTER_STORE + subjectId, "");
                break;
            case R.id.public_subject_more_layout:
                WebDetailActivity.action(mContext, Const.ROUTER_STORE_FREE, "");
                break;
            case R.id.public_class_try:
                SimpleWebActivity.action(mContext,mVideoBean.getSrc(),mVideoBean.getTitle());
                //WebDetailActivity.action(mContext, Const.ROUTER_DEMO + mVideoBean.getSrc());
                break;
        }
    }

    private int subjectId;
    private List<String> imgPaths;
    private FeatureProductAdapter mFeatureAdapter;
    private ActivityAdapter mActivityAdapter;
    private NewsAdapter mNewAdapter;
    private List<ProductBean> mProducts;
    private List<ActivityBean> mActivitys;
    private List<NewBean> newBeans;

    private ImageView mTeacherImg;
    private TextView mTeacherName;
    private TextView mTeacherPostion;
    private VideoBean mVideoBean;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        initBanner();
        initAdapter();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setEnableRefresh(false);
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    private void initAdapter() {
        mFeatureAdapter = new FeatureProductAdapter(R.layout.item_feature_product, mProducts);
        mFeatureAdapter.setOnItemClickListener(this);
        mSubjectRecycler.setLayoutManager(new GridLayoutManager(mContext, 2));
        mSubjectRecycler.addItemDecoration(new DividerGridItemDecoration(CommonUtil.dp2px(mContext, 1), getResources().getColor(R.color._F0F0F0)));
        mSubjectRecycler.setAdapter(mFeatureAdapter);
        mActivityAdapter = new ActivityAdapter(R.layout.item_home_activity, mActivitys);
        mAcRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mActivityAdapter = new ActivityAdapter(R.layout.item_home_activity, mActivitys);
        mAcRecycler.setAdapter(mActivityAdapter);
        mActivityAdapter.setOnItemClickListener(this);
        mNewAdapter = new NewsAdapter(R.layout.item_home_news, newBeans);
        mNewsRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mNewAdapter.setOnItemClickListener(this);
        mNewsRecycler.setAdapter(mNewAdapter);
    }

    private void initBanner() {
        mBanner.setImageLoader(new GlideImageLoader());
    }

    public void initData() {
        mPresenter.getHomeData();

        if (subjectId == -1) {
            return;
        }
        mPresenter.getNewsList(subjectId, 1);
        mPresenter.getHomeCategoryData(subjectId);
    }


    //试听课程
    private void showVideos(List<VideoBean> videos) {
        if (videos != null && videos.size() > 0) {
            mClassLayout.setVisibility(View.VISIBLE);
            VideoBean videoBean = videos.get(0);
            this.mVideoBean = videoBean;
            if (videoBean != null) {
                CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, videoBean.getImg(), mTeacherAvatar, getResources().getDrawable(R.drawable.default_course));
                mClassName.setText(videoBean.getTitle());
                mClassTeacher.setText("主讲讲师："+videoBean.getTeacher());
            }
        } else {
            mClassLayout.setVisibility(View.GONE);
        }
    }

    private void showTeachers(List<TeacherBean> teachers) {
        if (teachers != null && teachers.size() > 0) {
            mTeacherLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mTeacherScroll.removeAllViews();
            for (TeacherBean teacherBean : teachers) {
                View view = initTeacherView();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebDetailActivity.action(mContext, Const.ROUTER_TEACHER_DETAIL + teacherBean.getId(),teacherBean.getName());
                    }
                });
                mTeacherScroll.addView(view, params);
                mTeacherName.setText(teacherBean.getName());
                mTeacherPostion.setText(teacherBean.getInfo());
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, teacherBean.getPhoto(), mTeacherImg);
            }

        } else {
            mTeacherLayout.setVisibility(View.GONE);
        }

    }

    private View initTeacherView() {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.include_home_teacher, null);
        mTeacherImg = constraintLayout.findViewById(R.id.teacher_img);
        mTeacherName = constraintLayout.findViewById(R.id.teacher_name);
        mTeacherPostion = constraintLayout.findViewById(R.id.teacher_position);
        return constraintLayout;
    }

    private void showProducts(List<ProductBean> products) {
        if (products != null && products.size() > 0) {
            mSubjectLayout.setVisibility(View.VISIBLE);
            mFeatureAdapter.setNewData(products);
        } else {
            mSubjectLayout.setVisibility(View.GONE);
        }
    }


    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public void showHomeCategoryData(HomeCategoryResponse response) {
        List<ProductBean> products = response.getProducts();
        List<TeacherBean> teachers = response.getTeachers();
        List<VideoBean> videos = response.getVideos();
        showProducts(products);
        showTeachers(teachers);
        showVideos(videos);
    }

    @Override
    public void showHomeData(HomeResponse response) {
        List<BannerBean> banners = response.getBanners();
        List<ActivityBean> activitys = response.getActivitys();
        showBanners(banners);
        showActivity(activitys);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }

    @Override
    public void showNewsList(NewsResponse newsResponse) {
        List<NewBean> newBeans = newsResponse.getRows();
        if (newBeans != null && newBeans.size() > 0) {
            mNewsLayout.setVisibility(View.VISIBLE);
            mNewAdapter.setNewData(newBeans);
        }else{
            mNewsLayout.setVisibility(View.GONE);
        }
    }

    private void showActivity(List<ActivityBean> activitys) {
        if (activitys != null && activitys.size() > 0) {
            mAcLayout.setVisibility(View.VISIBLE);
            mActivityAdapter.setNewData(activitys);
        } else {
            mAcLayout.setVisibility(View.GONE);
        }
    }

    private void showBanners(List<BannerBean> banners) {
        if (banners != null && banners.size() > 0) {
            imgPaths = new ArrayList<>();
            for (BannerBean bannerBean : banners) {
                imgPaths.add(bannerBean.getImg());
            }
            mBanner.setVisibility(View.VISIBLE);
            mBanner.setImages(imgPaths);
            mBanner.start();
        } else {
            mBanner.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.public_class_try)
    public void onViewClicked() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CODE_REQ && resultCode == Const.CODE_RESULT) {
            subjectId = data.getIntExtra("subjectId", -1);
            setSubjectId(subjectId);
            initData();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //每日新知
        if (adapter instanceof NewsAdapter) {
            List<NewBean> newBeans = adapter.getData();
            NewBean newBean = newBeans.get(position);
            if (newBean != null) {
                WebDetailActivity.action(mContext, Const.ROUTER_NEWS + newBean.getId(), "");
            }
        }
        //课程
        else if (adapter instanceof FeatureProductAdapter) {
            List<ProductBean> productBeans = adapter.getData();
            ProductBean productBean = productBeans.get(position);
            if (productBean != null) {
                WebDetailActivity.action(mContext, Const.ROUTER_PRODUCT + productBean.getId(),"");
            }
        }
        //活动
        else if (adapter instanceof ActivityAdapter) {
            List<ActivityBean> activityBeans = adapter.getData();
            ActivityBean activityBean = activityBeans.get(position);
            if (activityBean != null) {
                WebDetailActivity.action(mContext, Const.ROUTER_NEWS + activityBean.getId(), "");
            }
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(UpdateFgEvent event) {
        int index = event.index;
        if (index == 0) {
            initData();
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

}
