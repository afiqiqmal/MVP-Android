package com.java.mvp.mvpandroid;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.java.mvp.mvpandroid.internal.AppModule;
import com.java.mvp.mvpandroid.internal.DaggerGraph;
import com.java.mvp.mvpandroid.internal.Graph;

import java.util.UUID;

/**
 * Created by hafiq on 23/01/2017.
 */

public class MVPApplication extends Application {

    private Graph mGraph;
    private String mUdid;

    @Override
    public void onCreate() {
        super.onCreate();

        setGraph(DaggerGraph.builder()
                .appModule(new AppModule(this))
                .build());

        setUdid();
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

    public static String getUdid(Context c) {
        return getApp(c).getUdid();
    }

    public String getUdid() {
        return mUdid;
    }

    public String setUdid() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();
        String androidId = "" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        return this.mUdid = deviceUuid.toString();
    }
}
