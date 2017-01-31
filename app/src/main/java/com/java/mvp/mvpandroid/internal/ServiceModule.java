package com.java.mvp.mvpandroid.internal;


import android.app.Service;

import dagger.Module;
import dagger.Provides;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class ServiceModule {

    private Service mContext;

    public ServiceModule(Service context) {
        mContext = context;
    }

    @Provides
    @PerService
    Service provideContext() {
        return mContext;
    }
}
