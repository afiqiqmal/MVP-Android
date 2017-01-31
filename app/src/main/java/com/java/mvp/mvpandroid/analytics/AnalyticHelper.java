package com.java.mvp.mvpandroid.analytics;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hafiq on 23/01/2017.
 */

/***
 * This class for analytics
 */
@Singleton
public class AnalyticHelper {

    private Context context;
    private FirebaseManager firebaseAnalytics;
//    private FacebookManager facebookManager;

    @Inject
    public AnalyticHelper(Context context){
        this.context = context;
        firebaseAnalytics = new FirebaseManager(context);
//        facebookManager = new FacebookManager(context);
    }

    public void sendScreenName(String name){
        firebaseAnalytics.sendScreenName(name);
//        facebookManager.sendScreenName(name);
    }

    public void sendUserProperties(String name, String value){
        firebaseAnalytics.sendUserProperties(name,value);
    }

    public void sendSessionTimeOut(long time){
        firebaseAnalytics.sendSessionTimeOut(time);
    }

}
