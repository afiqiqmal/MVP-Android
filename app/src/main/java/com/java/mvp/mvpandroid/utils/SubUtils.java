package com.java.mvp.mvpandroid.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hafiq on 23/01/2017.
 */

public class SubUtils {

    //convert dp to Px
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    //get screen size
    public static int sizeScreen(){
        return (int)((Resources.getSystem().getDisplayMetrics().widthPixels)/ Resources.getSystem().getDisplayMetrics().density);
    }

    //get screen height
    public static int getHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //get witdh screen
    public static int getWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    //get current date
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(boolean format){
        SimpleDateFormat time = new SimpleDateFormat("dd/MMM/yyyy");
        Calendar calendar = Calendar.getInstance();
        if (format)
            return time.format(calendar.getTime());
        else
            return String.valueOf(calendar.getTimeInMillis()/1000);
    }

    public static boolean checkPlayServices(Activity context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(context, resultCode, 9000).show();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("");
                alert.setMessage("");
                alert.setPositiveButton("EXIT", (dialog, which) -> System.exit(0));
            }
            return false;
        }
        return true;
    }
}
