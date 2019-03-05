package com.savegoldmaster.utils.retrofit;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.savegoldmaster.base.BaseApplication;
import com.savegoldmaster.utils.ToastUtil;
import com.savegoldmaster.utils.retrofit.factory.ResponseConverterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zhanglibin on 2017/11/16.
 */

public class Requester {
    private static volatile Requester requester;
    private Retrofit mRetrofit;

    private Requester() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api-test.au32.cn")
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();

    }

    public static Requester get() {
        if (requester == null) {
            synchronized (Requester.class) {
                if (requester == null) {
                    requester = new Requester();
                }
            }
        }
        return requester;
    }

    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }

    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        File httpCacheDirectory = new File(FileUtils.getCacheDir(BaseApplication.Companion.getInstance()), "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        httpClientBuilder.addInterceptor(new BaseParamsInterceptor());
        httpClientBuilder.addInterceptor(new LoggerInterceptor());
        return httpClientBuilder.build();

    }



}
