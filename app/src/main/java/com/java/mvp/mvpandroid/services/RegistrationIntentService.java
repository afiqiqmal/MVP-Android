package com.java.mvp.mvpandroid.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.internal.ServiceModule;
import com.java.mvp.mvpandroid.repository.ConcealRepository;

import javax.inject.Inject;

/**
 * @author : hafiq on 25/10/2016.
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    public static final String TOKEN = "token";
    public static final String ACTION_TOKEN = "TOKEN_RESPONSE";

    @Inject
    protected ConcealRepository user;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MVPApplication.graph(this).serviceGraph(new ServiceModule(this)).inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        FirebaseInstanceId iid = FirebaseInstanceId.getInstance();
        String token = iid.getToken();

        while(token == null){
            iid = FirebaseInstanceId.getInstance();
            token = iid.getToken();
        }

        Log.i("TOKEN","==>"+token);

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

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationIntentService.class);
        context.startService(intent);
    }

    private void sendRegistrationToServer(String token) {
        Log.i(TAG,token);
    }

}
