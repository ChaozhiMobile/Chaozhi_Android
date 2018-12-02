package com.czjy.chaozhi.ui.activity.datalibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.base.BaseActivity;
import com.czjy.chaozhi.util.OkHttpUtils;
import com.facebook.stetho.common.LogUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

import butterknife.BindView;

public class ShowDataLibraryActivity extends BaseActivity implements OnPageChangeListener {

    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.pdf)
    PDFView pdf;

    private String titleStr;
    private String fileID;
    private String pdfUrl;
    private String TAG = "ShowDataLibraryActivity";

    public static void action(Context context, String fileID, String titleStr, String pdfUrl) {
        Intent intent = new Intent(context, ShowDataLibraryActivity.class);
        intent.putExtra("fileID", fileID); //文件ID
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
        if (intent!=null) {
            fileID = intent.getStringExtra("fileID");
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

        String pdfStr = Environment.getExternalStorageDirectory() + savePath + "/" + fileID;
        LogUtil.i("PDF下载：本地Url路径："+pdfStr);

        if (fileIsExists(pdfStr)) { //如果文件已经下载直接打开，否则下载
            showPdf(pdfStr);
        } else {
            OkHttpUtils.build().download(path, savePath, fileID, new OkHttpUtils.OnDownloadListener() {
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
