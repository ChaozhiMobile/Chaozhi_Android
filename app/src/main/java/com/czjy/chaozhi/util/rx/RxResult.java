package com.czjy.chaozhi.util.rx;


import android.content.Intent;
import android.text.TextUtils;

import com.czjy.chaozhi.App;
import com.czjy.chaozhi.global.Const;
import com.czjy.chaozhi.model.http.HttpResponse;
import com.czjy.chaozhi.ui.activity.user.LoginActivity;
import com.czjy.chaozhi.util.SharedPreferencesUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by huyg on 2018/4/24.
 */

public class RxResult {

    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResponse<T>, T> handleResult() {
        return upstream -> {
            return upstream.flatMap(result -> {
                        if (result.isSuccess()) {
                            return createData(result.getData());
                        } else if (result.getCode()>=600&&result.getCode()<700) {
                            App.getInstance().setToken("");
                            SharedPreferencesUtils.setParam(App.getInstance().getApplicationContext(),Const.KEY_TOKEN,"");
                            Intent intent = new Intent(App.getInstance().getApplicationContext(), LoginActivity.class);
                            App.getInstance().getApplicationContext().startActivity(intent);
                            return Observable.error(new Exception("请您重新登录!"));
                        } else {
                            if (TextUtils.isEmpty(result.getMsg())) {
                                return Observable.error(new Exception("获取错误"));
                            }
                            return Observable.error(new Exception(result.getMsg()));
                        }
                    }

            );
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
