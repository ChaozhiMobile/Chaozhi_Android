package com.czjy.chaozhi.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by huyg on 2018/11/4.
 */
public class Utils {


    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;

    }
}
