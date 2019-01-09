package com.example.zhanglibin.savegoldmaster.utils.retrofit;

import android.util.Log;
import com.example.zhanglibin.savegoldmaster.base.BaseBean;
import com.example.zhanglibin.savegoldmaster.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Zhanglibin on 2017/11/16.
 */

public class RespondObserver<T extends BaseBean> implements Observer<T> {
    private static final String TAG = "RespondObserver";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.getCode() == 100) {
            onSuccess(t);
        } else {
            onFailure(t);
            ToastUtil.INSTANCE.showMessage(t.getMessage());
        }
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            Logger.e(TAG, "onError() called with: " + "throwable = [" + e + "], b = [" + e.getMessage() + "]");
        }
        onError();
    }

    @Override
    public void onComplete() {
        onFinish();
    }


    public void onSuccess(T result) {
    }

    public void onFailure(T result) {
    }

    public void onFinish() {
    }

    public void onError() {
    }
}
