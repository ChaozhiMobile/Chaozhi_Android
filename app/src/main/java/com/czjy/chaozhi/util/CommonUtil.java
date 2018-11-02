package com.czjy.chaozhi.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


/**
 * Created by chen on 20/03/2018.
 */

public class CommonUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static String getAppVersion(Context context) {
        PackageInfo packageInfo = CommonUtil.getPackageInfo(context);
        return packageInfo.versionName;
    }

    // 判断手机号码格式是否正确
    public static boolean phoneNumber(String mobiles) {
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证身份证号格式是否正确
     */
    public static boolean idCardNO(String idCardNo) {
        String telRegex = "([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|[x-z]|[X-Z])$)|([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)";
        if (TextUtils.isEmpty(idCardNo)) {
            return false;
        } else {
            return idCardNo.matches(telRegex);
        }
    }

    // 判断登录密码格式是否正确
    public static boolean password(String password) {
        String telRegex = "(^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$)";
        if (TextUtils.isEmpty(password))
            return false;
        else
            return password.matches(telRegex);
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Boolean isLogin() {
//        AppPreference appPreference = MainApplication.getInstance().getAppPreferences();
//        return appPreference.getUserInfo() != null && !TextUtils.isEmpty(appPreference.getUserInfo().getToken());
        return true;
    }

    public static void logout(Context context) {

    }


}
