package com.czjy.chaozhi.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.czjy.chaozhi.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by huyg on 2018/9/28.
 */

public abstract class ToolbarActivity extends RxAppCompatActivity {

    private FrameLayout containerLayout = null;
    protected Toolbar mToolbar;
    protected TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mToolbar = findViewById(R.id.base_toolbar);
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");

        mTitle = findViewById(R.id.toolbar_title);
        // set status bar text color black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (R.layout.activity_base == layoutResID) {
            super.setContentView(R.layout.activity_base);
            containerLayout = findViewById(R.id.layout_center);
            containerLayout.removeAllViews();
        } else {
            View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
            containerLayout.addView(contentView);
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    finish();
                }
            });
            setActionBar();
        }
    }


    public abstract void setActionBar();

}
