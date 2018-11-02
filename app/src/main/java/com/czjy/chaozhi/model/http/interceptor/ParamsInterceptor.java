package com.czjy.chaozhi.model.http.interceptor;


import android.text.TextUtils;

import com.czjy.chaozhi.App;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huyg on 2018/9/28.
 */

public class ParamsInterceptor implements Interceptor {

    private Map<String, String> bodyParams = null;

    @Override
    public Response intercept(Chain chain) throws IOException {
        bodyParams = new TreeMap<>();
        Request request = chain.request();
        if (request.method().equals("POST")) {
            if (request.body() == null || request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                if (request.body() != null) {
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                if (!TextUtils.isEmpty(App.getInstance().getToken())){
                    bodyBuilder.addEncoded("token",App.getInstance().getToken());
                }
                formBody = bodyBuilder.build();
                request = request.newBuilder().post(formBody).build();
            }
        } else if (request.method().equals("GET")) {
            HttpUrl.Builder builder = request.url().newBuilder();
            builder.addQueryParameter("source", "Android");
            request = request.newBuilder().url(builder.build()).build();
        }
        Response response = chain.proceed(request);
        return response;
    }
}
