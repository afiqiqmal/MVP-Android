package com.java.mvp.mvpandroid.analytics;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
public class AnalyticHelper {

    private Context context;
    private FirebaseManager firebaseAnalytics;
    private CrashLyticManager crashLyticManager;

    @Inject
    AnalyticHelper(Context context){
        this.context = context;
        firebaseAnalytics = new FirebaseManager(context);
        crashLyticManager = new CrashLyticManager(context);
    }

    public void sendScreenName(@NonNull String name){
        firebaseAnalytics.sendScreenName(name);
        crashLyticManager.sendScreenName(name);
    }
}
