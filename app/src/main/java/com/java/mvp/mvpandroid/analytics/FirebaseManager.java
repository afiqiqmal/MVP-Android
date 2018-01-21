package com.java.mvp.mvpandroid.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.java.mvp.mvpandroid.utils.CommonUtils;


/**
 * @author : hafiq on 23/01/2017.
 */

class FirebaseManager implements AnalyticView {

    private FirebaseAnalytics mFirebaseAnalytics;

    private Context context;

    FirebaseManager(Context context){
        this.context = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void sendScreenName(String screen){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screen);
        mFirebaseAnalytics.logEvent(screen.toLowerCase().replaceAll("[^A-Za-z0-9]+","_"),bundle);
    }

    @Override
    public void sendEvent(String name,String category,String content){
        Bundle eventAction = new Bundle();
        eventAction.putString(FirebaseAnalytics.Param.CONTENT_TYPE, category);
        eventAction.putString(FirebaseAnalytics.Param.ITEM_NAME,content);
        if (content != null)
            mFirebaseAnalytics.logEvent(CommonUtils.analyticFormat(name), eventAction);
        else
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, eventAction);
    }

    @Override
    public void sendEvent(String name, Bundle bundle) {
        mFirebaseAnalytics.logEvent(name, bundle);
    }

    @Override
    public void sendUserProperties(String name, String value){
        mFirebaseAnalytics.setUserProperty(CommonUtils.analyticFormat(name),value);
    }

    @Override
    public void sendUserIdProperties(String name) {
        mFirebaseAnalytics.setUserId(name);
    }

    @Override
    public void sendSessionTimeOut(long time){
        mFirebaseAnalytics.setSessionTimeoutDuration(time);
    }
}
