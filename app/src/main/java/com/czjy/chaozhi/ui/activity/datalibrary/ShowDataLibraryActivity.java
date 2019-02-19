package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.util.Utils;
import com.facebook.stetho.common.LogUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

import butterknife.BindView;

/**
 * 资料显示
 */
public class ShowDataLibraryActivity extends BaseActivity implements OnPageChangeListener {

    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.pdf)
    PDFView pdf;

    private String titleStr;
    private String pdfStr;

    public static void action(Context context, String titleStr, String pdfStr) {
        Intent intent = new Intent(context, ShowDataLibraryActivity.class);
        intent.putExtra("titleStr", titleStr); //标题
        intent.putExtra("pdfStr", pdfStr); //pdf本地地址
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        showPdf(pdfStr);
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent!=null) {
            titleStr = intent.getStringExtra("titleStr");
            pdfStr = intent.getStringExtra("pdfStr");

            mTitle.setText(titleStr);
        }
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_datalibrary;
    }

    @Override
    public void setActionBar() {
        mTitle.setText(titleStr);
    }

    private void showPdf(String fileName) {
        if (!Utils.fileIsExists(fileName)) {
            Toast.makeText(this, "文件已被删除", Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.i("PDF下载：本地Url路径：" + fileName);
            pdf.fromFile(new File(fileName))
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)

                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            float pageWidth = pdf.getOptimalPageWidth();
                            float viewWidth = pdf.getWidth();
                            pdf.zoomTo(viewWidth / pageWidth);
                            pdf.loadPages();
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            Log.d("TAG",t.getMessage());
                        }
                    })
                    .onPageChange(this)
                    .load();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        String pager = "" + page + "/" + pageCount + "";
        tvPage.setText(pager);
    }
}
