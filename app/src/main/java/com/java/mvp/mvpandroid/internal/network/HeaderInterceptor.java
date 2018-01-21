package com.java.mvp.mvpandroid.internal.network;

import android.content.Context;
import android.support.annotation.NonNull;


import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.BaseCryptUtils;
import com.mvp.client.internal.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : hafiq on 23/03/2017.
 */

public class HeaderInterceptor implements Interceptor {

    private PreferencesRepository mPrefs;
    private String mAuth;
    private Context mContext;

    public HeaderInterceptor(Context mContext, PreferencesRepository p) {
        mPrefs = p;
        this.mContext = mContext;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        mAuth = (mPrefs.getAuthToken() != null)?mPrefs.getAuthToken():"";

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader(Constant.HEADER_ACCEPT, Constant.APP_HEADER)
                .addHeader(Constant.HEADER_AUTHORIZE, Constant.AUTH + mAuth);


        Request r = builder.build();

        return chain.proceed(r);
    }
}