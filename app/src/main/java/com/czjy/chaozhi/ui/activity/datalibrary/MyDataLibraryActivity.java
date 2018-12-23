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
import com.czjy.chaozhi.ui.adapter.DataLibraryDownloadAdapter;
import com.czjy.chaozhi.ui.adapter.DataLibraryDownloadAdapter.InnerItemOnclickListener;
import com.czjy.chaozhi.util.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * 我的下载资料
 */
public class MyDataLibraryActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, InnerItemOnclickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private LinearLayoutManager mManager;
    private DataLibraryDownloadAdapter mAdapter;
    private List<DataLibraryBean> dataLibraryBeans;
    private DataLibraryBean dataLibraryBean;
    private DataLibraryDao dataLibraryDao;

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
        dataLibraryDao = new DataLibraryDao(this);
        dataLibraryBeans = dataLibraryDao.getAllData();
    }

    private void initView() {
        mAdapter = new DataLibraryDownloadAdapter(R.layout.item_datalibrary_download, dataLibraryBeans);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnInnerItemOnClickListener(this);
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

    /**
     * 列表点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DataLibraryBean> dataLibraryBeans = adapter.getData();
        dataLibraryBean = dataLibraryBeans.get(position);
        if (dataLibraryBean != null) {
            ShowDataLibraryActivity.action(mContext, dataLibraryBean.getFile_name(),dataLibraryBean.getFile_localurl());
        }
    }

    /**
     * 列表内部按钮点击事件
     * @param v
     */
    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        DataLibraryBean bean = dataLibraryBeans.get(position);
        switch (v.getId()) {
            case R.id.item_datalibrary_delete:
                if (bean != null) {
                    dataLibraryDao.delete(bean.getFile_id()); //删除数据库记录
                    Utils.deleteFile(bean.getFile_localurl()); //删除本地文件
                    dataLibraryBeans.remove(bean); //删除列表数据源
                    mAdapter.notifyItemRemoved(position); //刷新列表
                }
                break;
                default:
                    break;
        }
    }
}
