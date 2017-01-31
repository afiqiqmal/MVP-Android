package com.java.mvp.mvpandroid.internal;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;

import com.mvp.client.RestApi;
import com.mvp.client.internal.Constant;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
    public OkHttpClient provideClient(PreferencesRepository u) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);


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
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

    }

    @Provides
    @Singleton
    public RestApi provideRestApi(OkHttpClient client, Gson g) {

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_API)
                .addConverterFactory(GsonConverterFactory.create(g))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestApi.class);
    }

    @Provides
    @Singleton
    public PreferencesRepository providePrefences(Context context) {
        return new PreferencesRepository(context);
    }

    private class HeaderInterceptor implements Interceptor {

        private PreferencesRepository mPrefs;
        private String mAuth;

        public HeaderInterceptor(PreferencesRepository p) {
            mPrefs = p;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            if (mPrefs.getAuthToken() != null) {
                mAuth = mPrefs.getAuthToken();
            } else {
                mAuth = "";
            }

            Request r = chain.request()
                    .newBuilder()
                    .addHeader(Constant.HEADER_ACCEPT, Constant.APP_HEADER)
                    .addHeader(Constant.HEADER_CONTENT_TYPE, Constant.APP_HEADER)
                    .addHeader(Constant.HEADER_AUTHORIZE, Constant.AUTH + mAuth)
                    .build();

            return chain.proceed(r);
        }
    }
}