package com.java.mvp.mvpandroid.internal.activity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class ActivityModule {

    private Activity mContext;

    public ActivityModule(Activity context) {
        mContext = context;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mContext;
    }
}