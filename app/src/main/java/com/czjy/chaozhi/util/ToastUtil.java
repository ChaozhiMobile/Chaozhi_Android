package com.czjy.chaozhi.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by huyg on 2018/9/29.
 */
public class ToastUtil {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void toast(Context context, int messageId) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
    }

}
