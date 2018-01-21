package com.java.mvp.mvpandroid.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.internal.service.ServiceModule;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;

import javax.inject.Inject;

/**
 * @author : hafiq on 07/02/2017.
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    public static final String TOKEN = "token";
    public static final String ACTION_TOKEN = "TOKEN_RESPONSE";

    @Inject
    protected PreferencesRepository user;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MVPApplication.daggerAppComponent(this).serviceComponent(new ServiceModule(this)).inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        FirebaseInstanceId iid = FirebaseInstanceId.getInstance();
        String token = iid.getToken();

        while(token == null){
            iid = FirebaseInstanceId.getInstance();
            token = iid.getToken();
        }


        Intent response = new Intent();
        response.putExtra(TOKEN,token);
        response.setAction(ACTION_TOKEN);
        response.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(response);


        handleToken(token);
    }

    private void handleToken(String token) {
        user.setPushTokenSent(true);
        user.savePushToken(token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.i(TAG,token);
    }

}
