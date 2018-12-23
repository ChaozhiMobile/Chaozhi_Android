package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.util.CompletedView;

import java.util.List;

public class DataLibraryDownloadAdapter extends BaseQuickAdapter<DataLibraryBean, BaseViewHolder> {

    public DataLibraryDownloadAdapter(int layoutResId, @Nullable List<DataLibraryBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, DataLibraryBean item) {
        helper.setText(R.id.item_datalibrary_name, item.getFile_name());

    }
}
