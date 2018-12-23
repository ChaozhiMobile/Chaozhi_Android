package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.db.DataLibraryDao;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.ui.adapter.DataLibraryAdapter;
import com.czjy.chaozhi.ui.adapter.DataLibraryDownloadAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的下载资料
 */
public class MyDataLibraryActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private LinearLayoutManager mManager;
    private DataLibraryDownloadAdapter mAdapter;
    private List<DataLibraryBean> dataLibraryBeans;
    private DataLibraryBean dataLibraryBean;

    public static void action(Context context) {
        Intent intent = new Intent(context, MyDataLibraryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initData();
        initView();
    }

    private void initData() {
        DataLibraryDao dataLibraryDao = new DataLibraryDao(this);
        dataLibraryBeans = dataLibraryDao.getAllData();
    }

    private void initView() {
        mAdapter = new DataLibraryDownloadAdapter(R.layout.item_datalibrary_download, dataLibraryBeans);
        mAdapter.setOnItemClickListener(this);
        mManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datalibrary;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的下载");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DataLibraryBean> dataLibraryBeans = adapter.getData();
        dataLibraryBean = dataLibraryBeans.get(position);
        if (dataLibraryBean != null) {
            ShowDataLibraryActivity.action(mContext, dataLibraryBean.getFile_name(),dataLibraryBean.getFile_localurl());
        }
    }
}
