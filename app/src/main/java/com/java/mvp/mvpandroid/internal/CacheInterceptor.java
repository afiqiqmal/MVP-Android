package com.java.mvp.mvpandroid.internal;

import android.content.Context;

import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.mvp.client.internal.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : hafiq on 23/03/2017.
 */

public class CacheInterceptor implements Interceptor {

    Context mContext;

    public CacheInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (request.method().equals("GET")) {
            if (DeviceUtils.isConnected(mContext)) {
                request = request.newBuilder()
                        .header(Constant.CACHE_CONTROL, "only-if-cached")
                        .build();
            } else {
                request = request.newBuilder()
                        .header(Constant.CACHE_CONTROL, "public, max-stale=2419200")
                        .build();
            }
        }

        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
                .header(Constant.CACHE_CONTROL, "max-age=600")
                .build();
    }
}