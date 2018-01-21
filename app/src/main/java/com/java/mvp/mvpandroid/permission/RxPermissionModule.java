package com.java.mvp.mvpandroid.permission;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Basyrun Halim
 */

@Module
public class RxPermissionModule {
    @Provides
    @Singleton
    protected RxPermissionHelper provideRxPermissionHelper(Context context) {
        return new RxPermissionHelper(context);
    }
}
