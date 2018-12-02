package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.presenter.datalibrary.DataLibraryPresenter;
import com.czjy.chaozhi.presenter.datalibrary.contract.DataLibraryContract;
import com.czjy.chaozhi.ui.adapter.DataLibraryAdapter;
import com.czjy.chaozhi.util.CompletedView;
import com.czjy.chaozhi.util.OkHttpUtils;
import com.facebook.stetho.common.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class DataLibraryActivity extends BaseActivity<DataLibraryPresenter> implements DataLibraryContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private LinearLayoutManager mManager;
    private DataLibraryAdapter mAdapter;
    private List<DataLibraryBean> dataLibraryBeans;
    private DataLibraryBean dataLibraryBean;
    private int pid;

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
        if (intent!=null){
            pid = intent.getIntExtra("pid",0);
        }
    }

    private void initData() {
        mPresenter.getDataLibraryList(pid,1,10);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DataLibraryBean> dataLibraryBeans = adapter.getData();
        dataLibraryBean = dataLibraryBeans.get(position);
        if (dataLibraryBean != null) {

            String pdfUrl = Const.HTTP+dataLibraryBean.getFile();
            LogUtil.i("PDF下载：网络Url路径："+pdfUrl);

//            //执行下载
//            downloadFile(pdfUrl);

            //储存下载文件的SDCard目录
            String savePath = "/Chaozhi/File";

            OkHttpUtils.build().download(pdfUrl, savePath, new OkHttpUtils.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(File file) {
                    LogUtil.i("PDF下载：加载完成正在打开.." + file.getPath());

                    ImageView iconImgView = view.findViewById(R.id.item_datalibrary_img);
                    iconImgView.setVisibility(View.VISIBLE);
                    iconImgView.setImageResource(R.mipmap.ic_down_ok);

                    CompletedView mTasksView = (CompletedView) view.findViewById(R.id.tasks_view);
                    mTasksView.setVisibility(View.GONE);

//                    ShowDataLibraryActivity.action(mContext,dataLibraryBean.getFile_name(),pdfUrl);
                }

                @Override
                public void onDownloading(int progress) {
                    LogUtil.i("PDF下载：正在加载(" + progress + "/100)");

                    ImageView iconImgView = view.findViewById(R.id.item_datalibrary_img);
                    iconImgView.setVisibility(View.GONE);

                    CompletedView mTasksView = (CompletedView) view.findViewById(R.id.tasks_view);
                    mTasksView.setVisibility(View.VISIBLE);
                    mTasksView.setProgress(progress);
                }

                @Override
                public void onDownloadFailed() {
                    LogUtil.i("PDF下载：加载失败..");

                    ImageView iconImgView = view.findViewById(R.id.item_datalibrary_img);
                    iconImgView.setVisibility(View.VISIBLE);
                    iconImgView.setImageResource(R.mipmap.ic_down);

                    CompletedView mTasksView = (CompletedView) view.findViewById(R.id.tasks_view);
                    mTasksView.setVisibility(View.GONE);
                }
            });

//            WebDetailActivity.action(mContext, Const.PDF_URL + dataLibraryBean.getFile());
        }
    }

    /**
     * 下载
     */
    private void downloadFile(String path) {

        //储存下载文件的SDCard目录
        String savePath = "/Chaozhi/File";

        OkHttpUtils.build().download(path, savePath, new OkHttpUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                LogUtil.i("PDF下载：加载完成正在打开.." + file.getPath());

                ShowDataLibraryActivity.action(mContext,dataLibraryBean.getFile_name(),path);
            }

            @Override
            public void onDownloading(int progress) {
                LogUtil.i("PDF下载：正在加载(" + progress + "/100)");
            }

            @Override
            public void onDownloadFailed() {
                LogUtil.i("PDF下载：加载失败..");
            }
        });
    }
}
