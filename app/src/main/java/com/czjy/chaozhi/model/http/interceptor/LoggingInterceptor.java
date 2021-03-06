package com.czjy.chaozhi.model.http.interceptor;

import android.util.Log;

import com.czjy.chaozhi.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by huyg on 2018/9/28.
 */

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        long startTime = System.currentTimeMillis();
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "keep-alive")
                .build();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        if (response.isSuccessful()){

            String TAG = "LogInterceptor";
            Log.d(TAG, "\n");
            Log.d(TAG, "----------Start----------------");
            Log.d(TAG, "| " + request.toString());

            String logStr = "";

            String method = request.method();
            StringBuilder sb = new StringBuilder();
            if ("POST".equals(method)) {
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(URLDecoder.decode(body.encodedName(i), "utf-8") + "="
                                + URLDecoder.decode(body.encodedValue(i), "utf-8") + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                }
                logStr = "时间：" + Utils.getCurrentTime() + "  " + duration + "毫秒"
                        + "\n地址：" + request.url() + "\n参数：" + printJson("RequestParams", sb.toString())
                        + "\n结果：" + printJson("Response", content) + "\n\n\n";
            } else {
                logStr = "时间：" + Utils.getCurrentTime() + "  " + duration + "毫秒"
                        + "\n地址：" + request.url() + "\n结果：" + printJson("Response", content)
                        + "\n\n\n";
            }
            Log.d(TAG, logStr);
            Log.d(TAG, "----------End:" + duration + "毫秒----------");
        }
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    public static String printJson(String type, String msg) {
        String result = "";
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        message = LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);

        for (String line : lines) {
            result = result + line + "\n";

        }
        Log.d(TAG, type + result);
        return result;
    }

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

}