package com.czjy.chaozhi.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.event.UpdateFgEvent;
import com.czjy.chaozhi.presenter.main.MainPresenter;
import com.czjy.chaozhi.presenter.main.contract.MainContract;
import com.czjy.chaozhi.ui.activity.setting.SelectSubjectActivity;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.ui.fragment.home.HomeFragment;
import com.czjy.chaozhi.ui.fragment.home.LearnFragment;
import com.czjy.chaozhi.ui.fragment.home.LimitlessFragment;
import com.czjy.chaozhi.ui.fragment.home.MineFragment;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.facebook.stetho.common.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private HomeFragment mHomeFragment;
    private LearnFragment mLearnFragment;
    private LimitlessFragment mLimitlessFragment;
    private MineFragment mMineFragment;
    private List<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int subjectId;
    private long exitTime;
    private int index;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String flag = intent.getStringExtra("flag");
        if ("支付成功".equals(flag)) {
            mNavigation.setSelectedItemId(R.id.navigation_learn);
        } else {
            mNavigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    protected void init() {
        initSp();
        initView();
        initData();
    }


    private void initSp() {
        subjectId = (int) SharedPreferencesUtils.getParam(mContext, Const.KEY_SUBJECT, -1);
        if (subjectId == -1) {
            Intent intent = new Intent(mContext, SelectSubjectActivity.class);
            startActivityForResult(intent, Const.CODE_REQ);
        }
    }

    private void initView() {
        initFragment();
        initNV();
    }

    private void initData() {

    }

    private void initNV() {
        mNavigation.setItemIconTintList(null);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color._8C8D99),
                getResources().getColor(R.color._C31A1F)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigation.setItemTextColor(csl);
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home://超职
                        switchFragment(0);
                        break;
                    case R.id.navigation_learn://学习
                        if (TextUtils.isEmpty(App.getInstance().getToken())) {
                            gotoLogin();
                        } else {
                            switchFragment(1);
                        }
                        break;
                    case R.id.navigation_wx://无限
                        switchFragment(2);
                        break;
                    case R.id.navigation_user://个人中心
                        if (TextUtils.isEmpty(App.getInstance().getToken())) {
                            gotoLogin();
                        } else {
                            switchFragment(3);
                        }
                        break;
                }
                return true;
            }
        });
    }


    private void gotoLogin() {
        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        startActivity(intent);
    }

    private void initFragment() {
        fragments = new LinkedList<>();
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
            mHomeFragment.setSubjectId(subjectId);
        }
        if (mLearnFragment == null) {
            mLearnFragment = LearnFragment.newInstance();
        }
        if (mLimitlessFragment == null) {
            mLimitlessFragment = LimitlessFragment.newInstance();
        }
        if (mMineFragment == null) {
            mMineFragment = MineFragment.newInstance();
        }
        fragments.add(mHomeFragment);
        fragments.add(mLearnFragment);
        fragments.add(mLimitlessFragment);
        fragments.add(mMineFragment);
        showIndexFragment();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }

    private void showIndexFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mHomeFragment.isAdded()) {
            transaction.add(R.id.frameLayout, mHomeFragment);
        }
        transaction.show(mHomeFragment).commit();
        mCurrentFragment = mHomeFragment;
        EventBus.getDefault().post(new UpdateFgEvent(0));
    }

    private void switchFragment(int index) {
        this.index = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mCurrentFragment);
        if (!fragments.get(index).isAdded()) {
            transaction.add(R.id.frameLayout, fragments.get(index));
        }
        transaction.show(fragments.get(index)).commitAllowingStateLoss();
        mCurrentFragment = fragments.get(index);
        EventBus.getDefault().post(new UpdateFgEvent(index));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CODE_REQ && resultCode == Const.CODE_RESULT) {
            subjectId = data.getIntExtra("subjectId", -1);
            if (mHomeFragment != null) {
                mHomeFragment.setSubjectId(subjectId);
                mHomeFragment.initData();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                App.getInstance().getActivityManager().finishAllActivity();
                finish();
                System.exit(0);
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
