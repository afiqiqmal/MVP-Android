package com.java.mvp.mvpandroid.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by hafiq on 23/01/2017.
 */

class FirebaseManager implements AnalyticView {

    Context context;
    public FirebaseManager(Context context){
        this.context = context;
    }

    @Override
    public void sendScreenName(String screen){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screen);
        FirebaseAnalytics.getInstance(context).logEvent(screen.toLowerCase().replaceAll("[^A-Za-z0-9]+","_"),bundle);
    }

    @Override
    public void sendEvent(String category,String action){
        Bundle eventAction = new Bundle();
        eventAction.putString(FirebaseAnalytics.Param.CONTENT_TYPE, category);
        eventAction.putString(FirebaseAnalytics.Param.ITEM_ID, action);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, eventAction);
    }

    @Override
    public void sendUserProperties(String name, String value){
        FirebaseAnalytics.getInstance(context).setUserProperty(name.toLowerCase(),value);
    }

    @Override
    public void sendUserIdProperties(String name) {
        FirebaseAnalytics.getInstance(context).setUserId(name);
    }

    @Override
    public void sendSessionTimeOut(long time){
        FirebaseAnalytics.getInstance(context).setSessionTimeoutDuration(time);
    }
}
