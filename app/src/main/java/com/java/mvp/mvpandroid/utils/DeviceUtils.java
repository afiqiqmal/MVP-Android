package com.java.mvp.mvpandroid.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.UUID;
import java.util.regex.Pattern;

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

    public static float getBatteryLevel(Activity context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryIntent.hasExtra(BatteryManager.EXTRA_LEVEL) && batteryIntent.hasExtra(BatteryManager.EXTRA_SCALE)) {
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if(level == -1 || scale == -1) {
                return 50.0f;
            }

            return ((float)level / (float)scale) * 100.0f;
        }

        return 50.0f;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static String getDeviceVersion(Activity activity) {
        String v;
        try {
            v = activity.getApplication().getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            return v.replace("-staging", "");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @SuppressLint("HardwareIds")
    public static String getUDID(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            String tmDevice = "" + tm.getDeviceId();
            String tmSerial = "" + tm.getSimSerialNumber();
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

            return deviceUuid.toString();
        }
        else{
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    public static boolean isGPSEnabled(Activity activity) {
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }

        return !(!gps_enabled && !network_enabled);
    }

    public static String getDeviceEmailAddress(Activity activity) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        Account[] accounts = AccountManager.get(activity).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return "";
    }

    public static void closeSoftKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
