package com.java.mvp.mvpandroid.analytics;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;
import com.crashlytics.android.answers.SignUpEvent;

/**
 * @author : hafiq on 28/04/2017.
 */

class CrashLyticManager implements AnalyticView {

    private Context mContext;

    CrashLyticManager(Context context) {
        this.mContext = context;
    }

    @Override
    public void sendScreenName(String name) {
        Answers.getInstance().logCustom(new CustomEvent("Screen").putCustomAttribute("Page",name));
    }

    @Override
    public void sendUserProperties(String name, String value) {
        switch (name) {
            case AnalyticConstant.USER_EMAIL:
                Crashlytics.setUserEmail(value);
                break;
            case AnalyticConstant.USER_NAME:
                Crashlytics.setUserName(value);
                break;
            case AnalyticConstant.USER_SIGN_IN:
                Answers.getInstance().logLogin(new LoginEvent().putMethod(value).putSuccess(true));
                break;
            case AnalyticConstant.USER_SIGN_UP:
                Answers.getInstance().logSignUp(new SignUpEvent().putMethod(value).putSuccess(true));
                break;
            default:
                Answers.getInstance().logCustom(new CustomEvent("Custom_Event").putCustomAttribute(name, value));
                break;
        }

    }

    @Override
    public void sendUserIdProperties(String name) {
        Crashlytics.setUserIdentifier(name);
    }

    @Override
    public void sendSessionTimeOut(long time) {

    }

    @Override
    public void sendEvent(String name, String category, String content) {
        Answers.getInstance().logCustom(new CustomEvent(category).putCustomAttribute(name,content));
    }

    @Override
    public void sendEvent(String name, Bundle bundle) {

    }
}
