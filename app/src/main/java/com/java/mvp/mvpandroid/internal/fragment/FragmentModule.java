package com.java.mvp.mvpandroid.internal.fragment;

import android.app.Activity;

import com.java.mvp.mvpandroid.internal.activity.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class FragmentModule {

    private Activity mContext;

    public FragmentModule(Activity context) {
        mContext = context;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mContext;
    }
}