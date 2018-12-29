package com.czjy.chaozhi.ui.fragment.home;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseFragment;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.MineItem;
import com.czjy.chaozhi.model.bean.PurchaseBean;
import com.czjy.chaozhi.model.bean.UserBean;
import com.czjy.chaozhi.model.event.UpdateFgEvent;
import com.czjy.chaozhi.presenter.main.MinePresenter;
import com.czjy.chaozhi.presenter.main.contract.MineContract;
import com.czjy.chaozhi.ui.activity.datalibrary.MyDataLibraryActivity;
import com.czjy.chaozhi.ui.activity.setting.SettingActivity;
import com.czjy.chaozhi.ui.activity.web.WebDetailActivity;
import com.czjy.chaozhi.ui.adapter.MineAdapter;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;
import com.facebook.stetho.common.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2018/9/29.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.mine_title)
    TextView mTitle;
    @BindView(R.id.mine_avatar)
    ImageView mAvatar;
    @BindView(R.id.mine_phone)
    TextView mPhone;
    @BindView(R.id.mine_info)
    ConstraintLayout mInfo;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @OnClick({R.id.mine_info})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_info:
                WebDetailActivity.action(mContext,Const.ROUTER_INFO,"");
                break;
        }
    }

    private List<MineItem> mItems;
    private List<PurchaseBean.ChatBean> chatBeans;
    private LinearLayoutManager mManager;
    private MineAdapter mAdapter;
    private Intent mIntent = new Intent();


    public static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        initItem();
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getUserInfo();
    }

    private void initData() {
        mPresenter.getUserInfo();
        mPresenter.getPurchaseStatus();
    }

    private void initItem() {
        mItems = new ArrayList<>();
        mItems.add(new MineItem(R.mipmap.ic_course, "课程订单", false));
        mItems.add(new MineItem(R.mipmap.ic_message, "我的消息", true));
        mItems.add(new MineItem(R.mipmap.ic_fav, "我的收藏", true));
        mItems.add(new MineItem(R.mipmap.ic_coupon, "我的下载", false));
        mItems.add(new MineItem(R.mipmap.ic_feedback, "问题反馈", false));
        mItems.add(new MineItem(R.mipmap.ic_setting, "系统设置", false));

        chatBeans = new ArrayList<>();
    }

    private void initView() {
        mPhone.setText((String)SharedPreferencesUtils.getParam(mContext,Const.KEY_PHONE,""));
        initRecycler();
    }

    private void initRecycler() {
        mManager = new LinearLayoutManager(mContext);
        mAdapter = new MineAdapter(R.layout.item_mine, mItems);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    @OnClick(R.id.mine_info)
    public void onViewClicked() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        MineItem mineItem = mItems.get(position);

        if (mineItem.getItem().equals("我的班主任")) {
            PurchaseBean.ChatBean chatBean = chatBeans.get(0);
            WebDetailActivity.action(mContext,chatBean.getChat_url(),mineItem.getItem());
        }
        if (mineItem.getItem().equals("报考资料")) {
            WebDetailActivity.action(mContext,Const.ROUTER_APPLY,mineItem.getItem());
        }
        if (mineItem.getItem().equals("课程订单")) {
            WebDetailActivity.action(mContext,Const.ROUTER_ORDERS,mineItem.getItem());
        }
        if (mineItem.getItem().equals("我的消息")) {
            WebDetailActivity.action(mContext,Const.ROUTER_MESSAGE,mineItem.getItem());
        }
        if (mineItem.getItem().equals("我的收藏")) {
            WebDetailActivity.action(mContext,Const.ROUTER_MY_FAV,mineItem.getItem());
        }
        if (mineItem.getItem().equals("我的下载")) {
            MyDataLibraryActivity.action(mContext);
        }
        if (mineItem.getItem().equals("问题反馈")) {
            WebDetailActivity.action(mContext,Const.ROUTER_FEEDBACK,mineItem.getItem());
        }
        if (mineItem.getItem().equals("系统设置")) {
            mIntent.setClass(mContext,SettingActivity.class);
            startActivity(mIntent);
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(UpdateFgEvent event){
        int index = event.index;
        if (index==3){
            initView();
            initData();
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    public void showUserInfo(UserBean userBean) {
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext,userBean.getHead_img_url(),mAvatar,mContext.getResources().getDrawable(R.mipmap.ic_red_mine));
    }

    @Override
    public void showPurchaseStatus(PurchaseBean purchaseBean) {
        if (purchaseBean.getIs_purchase() == 1 && purchaseBean.getChat().size()>0) { //已报班
            chatBeans = purchaseBean.getChat();
            MineItem mineItem = mItems.get(0);
            if (!mineItem.getItem().equals("我的班主任")) {
                mItems.add(0, new MineItem(R.mipmap.ic_chat, "我的班主任", false));
                mItems.add(1, new MineItem(R.mipmap.ic_examdata, "报考资料", true));
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
