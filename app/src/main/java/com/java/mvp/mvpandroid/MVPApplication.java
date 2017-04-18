package com.java.mvp.mvpandroid;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.java.mvp.mvpandroid.helper.Language;
import com.java.mvp.mvpandroid.internal.AppModule;
import com.java.mvp.mvpandroid.internal.DaggerGraph;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;

import java.util.UUID;

/**
 * Created by hafiq on 23/01/2017.
 */

public class MVPApplication extends Application {

    private Graph mGraph;

    PreferencesRepository preferencesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setGraph(DaggerGraph.builder()
                .appModule(new AppModule(this))
                .build());

        preferencesRepository = new PreferencesRepository(this);

        if (preferencesRepository.getLanguage() == null)
            preferencesRepository.changeLanguage(Language.ENGLISH,this);
        else{
            preferencesRepository.changeLanguage(preferencesRepository.getLanguage(),this);
        }

    }


    public Graph getGraph() {
        return mGraph;
    }

    public void setGraph(Graph graph) {
        this.mGraph = graph;
    }

    public static Graph graph(Context context) {
        MVPApplication app = (MVPApplication) context.getApplicationContext();
        return app.getGraph();
    }

    public static MVPApplication getApp(Context c) {
        return (MVPApplication) c.getApplicationContext();
    }

}
