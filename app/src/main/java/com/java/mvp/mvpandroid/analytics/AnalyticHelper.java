package com.java.mvp.mvpandroid.analytics;

import android.content.Context;
import android.os.Bundle;
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
    private FacebookManager facebookManager;
    private CrashLyticManager crashLyticManager;


    @Inject
    AnalyticHelper(Context context){
        this.context = context;
//        firebaseAnalytics = new FirebaseManager(context);
//        facebookManager = new FacebookManager(context);
//        crashLyticManager = new CrashLyticManager(context);
    }

    public void sendScreenName(@NonNull String name){
        firebaseAnalytics.sendScreenName(name);
        facebookManager.sendScreenName(name);
        crashLyticManager.sendScreenName(name);
    }

    public void sendEvent(@NonNull String name, String category, String content){
        firebaseAnalytics.sendEvent(name,category,content);
        crashLyticManager.sendEvent(name,category,content);
    }

    public void sendEvent(@NonNull String name, Bundle bundle){
        firebaseAnalytics.sendEvent(name,bundle);
        crashLyticManager.sendEvent(name,bundle);
    }

    public void sendUserProperties(@NonNull String name, String value){
        firebaseAnalytics.sendUserProperties(name,value);
        crashLyticManager.sendUserProperties(name,value);
    }

    public void sendUserIdProperties(@NonNull String id){
        firebaseAnalytics.sendUserIdProperties(id);
        facebookManager.sendUserIdProperties(id);
        crashLyticManager.sendUserIdProperties(id);
    }

    public void sendSessionTimeOut(long time){
        firebaseAnalytics.sendSessionTimeOut(time);
    }

}
