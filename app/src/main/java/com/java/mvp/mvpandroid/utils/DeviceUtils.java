package com.java.mvp.mvpandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by hafiq on 23/01/2017.
 */

public class DeviceUtils {

    public static String getAPIlevel(){
        return "Android API :"+ Build.VERSION.SDK_INT;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return SubUtils.capitalize(model);
        } else {
            return SubUtils.capitalize(manufacturer) + " " + model;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static String getDeviceVersion(Activity activity){
        String v;
        try {
            v = activity.getApplication().getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            return v.replace("-staging","");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
