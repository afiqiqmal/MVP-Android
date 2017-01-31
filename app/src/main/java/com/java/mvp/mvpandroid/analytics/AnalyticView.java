package com.java.mvp.mvpandroid.analytics;

/**
 * Created by hafiq on 23/01/2017.
 */

public interface AnalyticView {

    void sendScreenName(String name);
    void sendUserProperties(String name, String value);
    void sendUserIdProperties(String name);
    void sendSessionTimeOut(long time);
    void sendEvent(String category, String action);
}
