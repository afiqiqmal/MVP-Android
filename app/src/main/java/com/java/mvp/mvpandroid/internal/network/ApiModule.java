package com.java.mvp.mvpandroid.internal.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.BaseCryptUtils;
import com.mvp.client.RestApi;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
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
    public RestApi provideRestApi(OkHttpClient client, Gson g, PreferencesRepository preferencesRepository) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new BaseCryptUtils().decodeStringWithIteration(BuildConfig.URL_API))
                .addConverterFactory(GsonConverterFactory.create(g))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestApi.class);
    }
}
