package com.java.mvp.mvpandroid.analytics;

import android.app.Activity;
import android.content.Context;

import com.facebook.appevents.AppEventsLogger;

/**
 * @author : hafiq on 26/01/2017.
 */

public class FacebookManager implements AnalyticView {

    private Context context;
//    private AppEventsLogger logger;

    public FacebookManager(Context context){
        this.context = context;
//        logger = AppEventsLogger.newLogger(context);
    }

    @Override
    public void sendScreenName(String name) {
//        logger.logEvent(name);
    }

    @Override
    public void sendUserProperties(String name, String value) {

    }

    @Override
    public void sendUserIdProperties(String name) {

    }

    @Override
    public void sendSessionTimeOut(long time) {

    }

    @Override
    public void sendEvent(String category, String action) {

    }
}
