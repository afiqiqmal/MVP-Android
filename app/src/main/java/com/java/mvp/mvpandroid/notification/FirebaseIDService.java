package com.java.mvp.mvpandroid.notification;

import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * @author : hafiq on 08/09/2016.
 */
public class FirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTokenRefresh() {
    }
}
