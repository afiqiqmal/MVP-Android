package com.java.mvp.mvpandroid.analytics;

import android.os.Bundle;

/**
 * @author : hafiq on 23/01/2017.
 */

interface AnalyticView {

    void sendScreenName(String name);
    void sendUserProperties(String name, String value);
    void sendUserIdProperties(String name);
    void sendSessionTimeOut(long time);
    void sendEvent(String name, String category, String content);
    void sendEvent(String name, Bundle bundle);
}
