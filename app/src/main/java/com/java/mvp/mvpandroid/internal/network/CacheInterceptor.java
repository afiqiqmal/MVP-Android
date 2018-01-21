package com.java.mvp.mvpandroid.internal.network;

import android.content.Context;
import android.support.annotation.NonNull;


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

    private Context mContext;
    private int durationCache = 60 * 60 * 24 * 7; // 1 week
    private int cacheAge = 60; // 1 minutes

    public CacheInterceptor(Context context) {
        this.mContext = context;
    }

    public void setDurationCache(int durationCache){
        this.durationCache = durationCache;
    }

    public void setCacheAge(int cacheAge){
        this.cacheAge = cacheAge;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        if (request.method().equals("GET")) {
            if (DeviceUtils.isConnected(mContext)) {
                request = request.newBuilder()
                        .header(Constant.CACHE_CONTROL, "public, max-age="+cacheAge)
                        .build();
            } else {
                request = request.newBuilder()
                        .header(Constant.CACHE_CONTROL, "public, only-if-cached, max-stale="+durationCache)
                        .build();
            }
        }

        return chain.proceed(request);
    }
}