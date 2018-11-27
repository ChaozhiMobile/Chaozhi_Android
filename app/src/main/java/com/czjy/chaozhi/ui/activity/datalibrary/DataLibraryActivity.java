package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.model.bean.NewBean;
import com.czjy.chaozhi.model.response.DataLibraryResponse;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.presenter.datalibrary.DataLibraryPresenter;
import com.czjy.chaozhi.presenter.datalibrary.contract.DataLibraryContract;
import com.czjy.chaozhi.ui.activity.web.SimpleWebActivity;
import com.czjy.chaozhi.ui.activity.web.WebDetailActivity;
import com.czjy.chaozhi.ui.adapter.DataLibraryAdapter;
import com.czjy.chaozhi.ui.adapter.SubjectAdapter;
import com.czjy.chaozhi.util.SharedPreferencesUtils;
import com.czjy.chaozhi.util.ToastUtil;
import com.czjy.chaozhi.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DataLibraryBean> dataLibraryBeans = adapter.getData();
        DataLibraryBean dataLibraryBean = dataLibraryBeans.get(position);
        if (dataLibraryBean != null) {
            //执行下载
            ToastUtil.toast(this,dataLibraryBean.getFile());

//            WebDetailActivity.action(mContext, Const.PDF_URL + dataLibraryBean.getFile());
        }
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
}
