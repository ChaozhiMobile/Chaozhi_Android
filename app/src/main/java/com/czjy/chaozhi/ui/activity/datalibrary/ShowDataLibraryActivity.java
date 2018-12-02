package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.util.OkHttpUtils;
import com.facebook.stetho.common.LogUtil;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

import butterknife.BindView;

public class ShowDataLibraryActivity extends BaseActivity implements OnPageChangeListener {

    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.pdf)
    PDFView pdf;

    private String titleStr;
    private String pdfUrl;

    public static void action(Context context, String titleStr, String pdfUrl) {
        Intent intent = new Intent(context, ShowDataLibraryActivity.class);
        intent.putExtra("titleStr", titleStr); //标题
        intent.putExtra("pdfUrl", pdfUrl); //pdf下载地址
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        downloadFile(pdfUrl);
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent!=null){
            titleStr = intent.getStringExtra("titleStr");
            pdfUrl = intent.getStringExtra("pdfUrl");

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

    /**
     * 下载
     */
    private void downloadFile(String path) {

        //储存下载文件的SDCard目录
        String savePath = "/Chaozhi/File";

        String pdfStr = Environment.getExternalStorageDirectory() + savePath + "/" + getNameFromUrl(path);
        LogUtil.i("PDF下载：本地Url路径："+pdfStr);

        if (fileIsExists(pdfStr)) { //如果文件已经下载直接打开，否则下载
            showPdf(pdfStr);
        } else {
            OkHttpUtils.build().download(path, "/Chaozhi/File", new OkHttpUtils.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(File file) {
                    LogUtil.i("PDF下载：加载完成正在打开.."+file.getPath());
                    tvPage.setText("加载完成正在打开..");
                    showPdf(file.getPath());
                }
                @Override
                public void onDownloading(int progress) {
                    LogUtil.i("PDF下载：正在加载("+progress+"/100)");
                    tvPage.setText("正在加载("+progress+"/100)");
                }
                @Override
                public void onDownloadFailed() {
                    LogUtil.i("PDF下载：加载失败..");
                    tvPage.setText("加载失败..");
                }
            });
        }
    }

    /**
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 判断文件是否存在
     */
    public boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void showPdf(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.i("PDF下载：本地Url路径：" + fileName);
            pdf.fromFile(new File(fileName))

                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .swipeVertical(false)

                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            float pageWidth = pdf.getOptimalPageWidth();
                            float viewWidth = pdf.getWidth();
                            pdf.zoomTo(viewWidth / pageWidth);
                            pdf.loadPages();
                        }
                    })
                    .onPageChange(this)
                    .load();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        String pager = "页码(" + page + "/" + pageCount + ")";
        tvPage.setText(pager);
    }
}
