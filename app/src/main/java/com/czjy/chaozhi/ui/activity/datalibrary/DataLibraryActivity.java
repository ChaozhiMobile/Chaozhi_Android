package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.db.DataLibraryDao;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.presenter.datalibrary.DataLibraryPresenter;
import com.czjy.chaozhi.presenter.datalibrary.contract.DataLibraryContract;
import com.czjy.chaozhi.ui.adapter.DataLibraryAdapter;
import com.czjy.chaozhi.util.OkHttpUtils;
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.util.Utils;
import com.facebook.stetho.common.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * 资料库
 */
public class DataLibraryActivity extends BaseActivity<DataLibraryPresenter> implements DataLibraryContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private LinearLayoutManager mManager;
    private DataLibraryAdapter mAdapter;
    private List<DataLibraryBean> dataLibraryBeans;
    private DataLibraryBean currentDataLibraryBean;
    private int pid;
    private int clickPosition;
    private static boolean waitDownload;

    public static void action(Context context, int pid) {
        Intent intent = new Intent(context, DataLibraryActivity.class);
        intent.putExtra("pid", pid); //产品ID
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            pid = intent.getIntExtra("pid", 0);
        }
    }

    private void initData() {
        mPresenter.getDataLibraryList(pid, 1, 10);
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRecycler() {
        mAdapter = new DataLibraryAdapter(R.layout.item_datalibrary, dataLibraryBeans);
        mAdapter.setOnItemClickListener(this);
        mManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datalibrary;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("资料库");
    }

    @Override
    public void showDataLibraryList(DataLibraryResponse dataLibraryResponse) {
        List<DataLibraryBean> dataLibraryBeans = dataLibraryResponse.getRows();
        if (dataLibraryBeans != null && dataLibraryBeans.size() > 0) {
            for (int i = 0; i<dataLibraryBeans.size(); i++) {
                DataLibraryBean dataLibraryBean = dataLibraryBeans.get(i);
                //储存下载文件的SDCard目录
                String savePath = "/Chaozhi/File";
                String pdfStr = Environment.getExternalStorageDirectory() + savePath + "/" + String.valueOf(dataLibraryBean.getFile_id());
                dataLibraryBean.setFile_localurl(pdfStr);
                if (Utils.fileIsExists(pdfStr)) { //文件存在
                    dataLibraryBean.setProgress(101);
                    //如果文件已经下载，没有存储数据库，重新存储一遍
                    DataLibraryDao dataLibraryDao = new DataLibraryDao(this);
                    DataLibraryBean libraryBean = dataLibraryDao.select(dataLibraryBean.getFile_id());
                    if (libraryBean==null) {
                        dataLibraryDao.insert(dataLibraryBean);
                    }
                } else {
                    dataLibraryBean.setProgress(-1);
                }
            }
            mAdapter.setNewData(dataLibraryBeans);
        }
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
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

    /**
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DataLibraryBean> dataLibraryBeans = adapter.getData();
        DataLibraryBean dataLibraryBean = dataLibraryBeans.get(position);
        if (dataLibraryBean != null) {

            String pdfUrl = Const.HTTP + dataLibraryBean.getFile();
            LogUtil.i("PDF下载：网络Url路径：" + pdfUrl);

            //储存下载文件的SDCard目录
            String savePath = "/Chaozhi/File";

            String pdfStr = dataLibraryBean.getFile_localurl();
            LogUtil.i("PDF下载：本地Url路径："+pdfStr);

            if (dataLibraryBean.getProgress()!=-1
                    && dataLibraryBean.getProgress()!=101) { //正在下载，什么也不做

            }
            else if (Utils.fileIsExists(pdfStr)
                    && dataLibraryBean.getProgress()==101) { //如果文件已经下载(过滤掉正在下载的文件)直接打开，否则下载
                ShowDataLibraryActivity.action(mContext, dataLibraryBean.getFile_name(),dataLibraryBean.getFile_localurl());
            }
            else {
                if (waitDownload==true) {
                    ToastUtil.toast(getApplicationContext(),"不支持多文件同时下载");
                } else {
                    waitDownload = true;
                    clickPosition = position;
                    currentDataLibraryBean = dataLibraryBean;
                    OkHttpUtils.build().download(pdfUrl, savePath, String.valueOf(dataLibraryBean.getFile_id()), new OkHttpUtils.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(File file) {
                            LogUtil.i("PDF下载：加载完成正在打开.." + file.getPath());
                            Message message = Message.obtain();
                            message.what = 0;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onDownloading(int progress) {
                            LogUtil.i("PDF下载：正在加载(" + progress + "/100)");
                            Message message = Message.obtain();
                            message.what = 1;
                            message.arg1 = progress;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onDownloadFailed() {
                            LogUtil.i("PDF下载：加载失败..");
                            Message message = Message.obtain();
                            message.what = -1;
                            mHandler.sendMessage(message);
                        }
                    });
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case -1://失败
                    waitDownload = false;
                    currentDataLibraryBean.setProgress(-1);
                    mAdapter.setData(clickPosition, currentDataLibraryBean);
                    break;
                case 0://成功
                    waitDownload = false;
                    currentDataLibraryBean.setProgress(101);
                    mAdapter.setData(clickPosition, currentDataLibraryBean);

                    //调用DataLibraryDao插入方法进行插入
                    DataLibraryDao dao = new DataLibraryDao(mContext);
                    dao.insert(currentDataLibraryBean);

                    ShowDataLibraryActivity.action(mContext, currentDataLibraryBean.getFile_name(),currentDataLibraryBean.getFile_localurl());
                    break;
                case 1://进行中
                    int progress = msg.arg1;
                    currentDataLibraryBean.setProgress(progress);
                    mAdapter.setData(clickPosition, currentDataLibraryBean);
                    break;
            }
        }
    };
}
