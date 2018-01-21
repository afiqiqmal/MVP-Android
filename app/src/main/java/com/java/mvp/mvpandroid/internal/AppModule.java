package com.java.mvp.mvpandroid.internal;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class AppModule {

    protected final Context mContext;

    public AppModule(Application context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

}
