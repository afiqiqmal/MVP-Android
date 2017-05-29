package com.java.mvp.mvpandroid;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.java.mvp.mvpandroid.helper.Language;
import com.java.mvp.mvpandroid.internal.AppModule;
import com.java.mvp.mvpandroid.internal.DaggerGraph;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.repository.ConcealRepository;

import io.fabric.sdk.android.Fabric;

/**
 * @author : hafiq on 23/01/2017.
 */

public class MVPApplication extends Application {

    private Graph mGraph;

    ConcealRepository preferencesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        appInit();

        setGraph(DaggerGraph.builder()
                .appModule(new AppModule(this))
                .build());

        preferencesRepository = new ConcealRepository(this);

        if (preferencesRepository.getLanguage() == null)
            preferencesRepository.changeLanguage(Language.ENGLISH,this);
        else{
            preferencesRepository.changeLanguage(preferencesRepository.getLanguage(),this);
        }

    }

    private void appInit(){
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

//        Crashlytics crashlyticsKit = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();
//        Fabric.with(this, crashlyticsKit,new Answers());

        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.DEBUG);
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
