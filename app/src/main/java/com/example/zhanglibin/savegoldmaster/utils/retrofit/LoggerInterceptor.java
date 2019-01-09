package com.example.zhanglibin.savegoldmaster.utils.retrofit;

import android.util.Log;
import com.orhanobut.logger.Logger;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Zhanglibin on 2017/4/25.
 */

public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "NetWorkLogger";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequestMessage(request);
        //开始请求
        Response response = chain.proceed(request);
        //请求之后的信息打印
        printResponseMessage(response);
        return response;
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        Logger.i(TAG, "Method: " + request.method() + "\nUrl   : " + request.url().url().toString());
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            Logger.i(TAG, "Params: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (contentLength != 0 && charset != null) {
            String result = buffer.clone().readString(charset);
            Logger.json(UnicodeUtil.decodeUnicode(result));
            Log.i(TAG, "Response: " + UnicodeUtil.decodeUnicode(result));
        }
    }


}
