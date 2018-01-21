package com.java.mvp.mvpandroid.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.ui.splash.SplashActivity;
import com.mvp.client.internal.Constant;

import java.util.UUID;
import java.util.regex.Pattern;


/**
 * @author : hafiq on 23/01/2017.
 */

public class DeviceUtils {

    public static String getAPIlevel() {
        return "Android API :" + Build.VERSION.SDK_INT;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return CommonUtils.capitalize(model);
        } else {
            return CommonUtils.capitalize(manufacturer) + " " + model;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
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
            String tmDevice = "" + (tm != null ? tm.getDeviceId() : null);
            String tmSerial = "" + (tm != null ? tm.getSimSerialNumber() : null);
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
            gps_enabled = lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.e(Constant.TAG,"gps provider error : " + e.getMessage());
        }

        try {
            network_enabled = lm != null && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            Log.e(Constant.TAG,"network provider error : " + e.getMessage());
        }

        return (gps_enabled && network_enabled);
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
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void callPhone(Context context, String phoneText)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneText));
        context.startActivity(intent);
    }

    public static void intentEmail(Context context, String email, String title, String body)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void addShortcutIcon(Context context, PreferencesRepository preferencesRepository){

        if (!preferencesRepository.isShortCutCreated()) {
            Intent shortcutIntent = new Intent(context, SplashActivity.class);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher));
            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            addIntent.putExtra("duplicate", false);
            context.sendBroadcast(addIntent);

            preferencesRepository.setShortCutCreate(true);
        }
    }

}
