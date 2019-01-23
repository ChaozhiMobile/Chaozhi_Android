package com.czjy.chaozhi.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.BuildConfig;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.VersionBean;
import com.czjy.chaozhi.model.event.UpdateEvent;
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
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.witget.dialog.UpdateDialogFragment;
import com.facebook.stetho.common.LogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
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
    private static final String TAG = "MainActivity";

    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APK_PATH = "/apk_cache";
    private static final int REQ_UPDATE = 999;

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
        initUpdate();
    }

    @Override
    public void getVersion(VersionBean versionBean) {

        String url = versionBean.getUrl();
        String title = versionBean.getTitle();
        String message = versionBean.getNote();
        String version = versionBean.getVersion();
        int grade = versionBean.getGrade();

        LogUtil.i("版本更新===标题：" + title + "\n"
                + "内容：" + message + "\n"
                + "版本：" + version + "\n"
                + "升级：" + grade + "\n"
                + "地址：" + url + "\n");

        switch (grade) {
            case 1://不升级
                return;
            case 2://提示更新
            case 3://强制更新
                if (!TextUtils.isEmpty(url)) {
                    UpdateDialogFragment.getInstance(message, grade, url).show(getSupportFragmentManager(), "Show");
                }
                break;
        }
    }

    @Subscribe
    public void onEvent(UpdateEvent updateEvent) {
        LogUtil.i("下载地址：" + updateEvent.url);
        if (updateEvent.url.endsWith(".apk")) {
            updateApp(updateEvent.url);
        } else {
            if (isAvilible(this, "com.tencent.android.qqdownloader")) {
                launchAppDetail(getApplicationContext(), "com.czjy.chaozhi", "com.tencent.android.qqdownloader");
            } else {
                Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.czjy.chaozhi");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        }
    }

    private void updateApp(String url) {
        File f = new File(ACCOUNT_DIR + APK_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        File downloadFile = new File(f.getAbsoluteFile() + "/" + System.currentTimeMillis() + "temp1.apk");
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("下载更新");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        FileDownloader.getImpl().create(url)
                .setPath(downloadFile.getAbsolutePath(), false)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setMessage("连接中...");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "isContinue" + isContinue);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress((int) (((float) soFarBytes / totalBytes) * 100));

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.d(TAG, "blockComplete");
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        Log.d(TAG, "retry");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        if (downloadFile.exists()) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", new File(downloadFile.getAbsolutePath()));
                                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setDataAndType(Uri.parse("file://" + downloadFile.getAbsolutePath()), "application/vnd.android.package-archive");
                            }
                            MainActivity.this.startActivityForResult(intent, REQ_UPDATE);
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        progressDialog.dismiss();
                        initUpdate();
                        ToastUtil.toast(getApplicationContext(), "更新出错");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d(TAG, "warn");
                    }
                }).start();
    }

    private void initUpdate() {
        PackageManager manager = mContext.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (name.isEmpty()) {
            name = "1.1.1";
        }
        mPresenter.checkVersion("android", name);
    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }


    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        EventBus.getDefault().postSticky(new UpdateFgEvent(index));
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
        if (requestCode == REQ_UPDATE) { //apk下载完成回调
            initUpdate();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        CrashReport.testJavaCrash();
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
