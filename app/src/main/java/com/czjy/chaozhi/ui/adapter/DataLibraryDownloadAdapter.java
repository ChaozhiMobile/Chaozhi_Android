package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.util.Utils;

import java.util.List;

public class DataLibraryDownloadAdapter extends BaseQuickAdapter<DataLibraryBean, BaseViewHolder> implements View.OnClickListener {

    private List<DataLibraryBean> mData;
    private InnerItemOnclickListener mListener;

    public interface InnerItemOnclickListener {
        public void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }

    public DataLibraryDownloadAdapter(int layoutResId, @Nullable List<DataLibraryBean> data) {
        super(layoutResId, data);
        mData = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, DataLibraryBean item) {
        helper.setText(R.id.item_datalibrary_name, item.getFile_name());
        helper.setText(R.id.item_datalibrary_size, Utils.getFileSize(item.getFile_localurl()));
        Button deleteBtn = helper.getView(R.id.item_datalibrary_delete);
        deleteBtn.setOnClickListener(this);
        int position = 0;
        for (int i = 0; i<mData.size(); i++) {
            DataLibraryBean dataLibraryBean = mData.get(i);
            if (dataLibraryBean.getFile_id() == item.getFile_id()) {
                position = i;
                break;
            }
        }
        deleteBtn.setTag(position);
    }
}
