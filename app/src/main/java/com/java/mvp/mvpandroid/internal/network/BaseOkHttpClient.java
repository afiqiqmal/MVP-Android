package com.java.mvp.mvpandroid.internal.network;

import android.content.Context;

import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.mvp.client.internal.Constant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
public class BaseOkHttpClient {

    @Provides
    @Singleton
    public CacheInterceptor provideCacheInterceptor(Context mContext) {
        return new CacheInterceptor(mContext);
    }

    @Provides
    @Singleton
    public HeaderInterceptor provideHeaderInterceptor(Context mContext, PreferencesRepository u) {
        return new HeaderInterceptor(mContext, u);
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(Context mContext, CacheInterceptor cacheInterceptor, HeaderInterceptor headerInterceptor) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(mContext.getCacheDir(), "http"), SIZE_OF_CACHE);

        if (BuildConfig.DEBUG) {
            return new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(headerInterceptor)
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        else{
            return new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(headerInterceptor)
                    .cache(cache)
                    .addNetworkInterceptor(cacheInterceptor)
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
    }
}
