package com.java.mvp.mvpandroid.analytics;

import android.content.Context;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;

/**
 * @author : hafiq on 26/01/2017.
 */

class FacebookManager implements AnalyticView {

    private Context context;
    private AppEventsLogger logger;

    FacebookManager(Context context){
        this.context = context;
        logger = AppEventsLogger.newLogger(context);
    }

    @Override
    public void sendScreenName(String name) {
        logger.logEvent(name);
    }

    @Override
    public void sendUserProperties(String name, String value) {

    }

    @Override
    public void sendUserIdProperties(String name) {
        AppEventsLogger.setUserID(name);
    }

    @Override
    public void sendSessionTimeOut(long time) {

    }

    @Override
    public void sendEvent(String name, String category, String string) {
        Bundle bundle = new Bundle();
        bundle.putString(category,string);
        logger.logEvent(name,bundle);
    }

    @Override
    public void sendEvent(String name, Bundle bundle) {
        logger.logEvent(name,bundle);
    }
}
