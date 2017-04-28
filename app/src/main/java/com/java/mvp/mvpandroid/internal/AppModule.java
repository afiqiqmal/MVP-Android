package com.java.mvp.mvpandroid.internal;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.repository.ConcealRepository;

import com.java.mvp.mvpandroid.utils.BaseCryptUtils;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.mvp.client.RestApi;
import com.mvp.client.internal.Constant;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hafiq on 23/01/2017.
 */

@Module
public class AppModule {

    protected final Context mContext;
    protected Retrofit retrofit;

    public AppModule(Application context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
            if (json.getAsJsonPrimitive().isNumber()) {
                return new Date(json.getAsJsonPrimitive().getAsLong() * 1000);
            } else {
                return null;
            }
        });
        return builder.create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(ConcealRepository u) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(mContext.getCacheDir(), "http"), SIZE_OF_CACHE);

        if (BuildConfig.DEBUG) {
            return new OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(new HeaderInterceptor(u))
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        else{
            return new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor(u))
                    .cache(cache)
                    .addNetworkInterceptor(new CacheInterceptor(mContext))
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

    }

    @Provides
    @Singleton
    public RestApi provideRestApi(OkHttpClient client, Gson g) {
        g = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(new BaseCryptUtils.Builder(mContext).decodeStringWithIteration(BuildConfig.URL_API))
                .addConverterFactory(GsonConverterFactory.create(g))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestApi.class);
    }

    @Provides
    @Singleton
    public ConcealRepository providePrefences(Context context) {
        return new ConcealRepository(context);
    }
}
