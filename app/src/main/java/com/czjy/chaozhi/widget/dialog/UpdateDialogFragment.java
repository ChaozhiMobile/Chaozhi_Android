package com.czjy.chaozhi.widget.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.event.LaterEvent;
import com.czjy.chaozhi.model.event.UpdateEvent;

/**
 * Created by huyg on 2018/8/9.
 */
public class UpdateDialogFragment extends DialogFragment {


    @BindView(R.id.update_img)
    ImageView mImg;
    @BindView(R.id.update_message)
    TextView mMessage;
    @BindView(R.id.update_later)
    TextView mLater;
    @BindView(R.id.update_now)
    TextView mNow;
    private String message;
    private int type;//1：不升级，2：提示更新，3：强制更新
    private String url;
    public static UpdateDialogFragment getInstance(String message,int type,String url) {
        UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("type",type);
        bundle.putString("url",url);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_app_update, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//取消对话框fragment的标题
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDialog();
        initView();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle!=null){
            message = bundle.getString("message");
            type = bundle.getInt("type",1);
            url = bundle.getString("url");
        }
        message = message.replace(";","\n");
        if (!TextUtils.isEmpty(message)){
            mMessage.setText(message);
        }
        switch (type){
            case 2:
                mLater.setVisibility(View.VISIBLE);
                break;
            case 3:
                mLater.setVisibility(View.GONE);
                break;
        }
    }

    private void initDialog() {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.update_later, R.id.update_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_later:
                EventBus.getDefault().post(new LaterEvent());
                dismiss();
                break;
            case R.id.update_now:
                EventBus.getDefault().post(new UpdateEvent(url));
                dismiss();
                break;
        }
    }
}
