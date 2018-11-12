package com.czjy.chaozhi.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.MineItem;

import java.util.List;

/**
 * Created by huyg on 2018/9/29.
 */
public class MineAdapter extends BaseQuickAdapter<MineItem, BaseViewHolder> {

    public MineAdapter(int layoutResId, @Nullable List<MineItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineItem item) {
        helper.setImageResource(R.id.item_mine_img, item.getSourceId());
        helper.setText(R.id.item_mine_content, item.getItem());
//        if (item.isHasDot()) {
//            helper.setVisible(R.id.item_mine_point, true);
//        } else {
//            helper.setVisible(R.id.item_mine_point, false);
//        }
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setVisible(R.id.item_mine_line,false);
        } else {
            helper.setVisible(R.id.item_mine_line,true);
        }

    }
}
