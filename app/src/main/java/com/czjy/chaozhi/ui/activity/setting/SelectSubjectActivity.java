package com.czjy.chaozhi.ui.activity.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.response.SubjectsResponse;
import com.czjy.chaozhi.presenter.setting.SelectSubjectPresenter;
import com.czjy.chaozhi.presenter.setting.contract.SelectSubjectContract;
import com.czjy.chaozhi.ui.adapter.SubjectAdapter;
import com.czjy.chaozhi.util.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2018/10/8.
 */
public class SelectSubjectActivity extends BaseActivity<SelectSubjectPresenter> implements SelectSubjectContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private LinearLayoutManager mManager;
    private SubjectAdapter mAdapter;
    private List<SubjectsResponse> mSubjects;

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mPresenter.getSubjectList();
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new SubjectAdapter(R.layout.item_subject, mSubjects);
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
        return R.layout.activity_select_subject;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("关注课程");
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<SubjectsResponse> response = adapter.getData();
        SubjectsResponse subject = response.get(position);
        int id = subject.getId();
        SharedPreferencesUtils.setParam(mContext, Const.KEY_SUBJECT, id);
        Intent intent = new Intent();
        intent.putExtra("subjectId", id);
        setResult(Const.CODE_RESULT, intent);
        finish();
    }

    @Override
    public void showSubjects(List<SubjectsResponse> subjects) {
        int subjectId = (int) SharedPreferencesUtils.getParam(mContext, Const.KEY_SUBJECT, -1);
        if (subjectId == -1) {
            subjects.get(0).setSelect(true);
        } else {
            for (SubjectsResponse subject : subjects) {
                if (subjectId == subject.getId()) {
                    subject.setSelect(true);
                    break;
                }
            }
        }
        mAdapter.setNewData(subjects);
    }
}
