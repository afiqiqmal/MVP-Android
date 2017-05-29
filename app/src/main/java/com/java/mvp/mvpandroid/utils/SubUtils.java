package com.java.mvp.mvpandroid.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hafiq on 23/01/2017.
 */

public class SubUtils {

    //convert dp to Px
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
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

    public static boolean isEmailValid(String email){
        if (email!=null && !email.equals("")) {
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            return matcher.matches();
        }

        return true;
    }

    public static boolean isUrlValid(String url){
        if (url!=null && !url.equals("")){
            String regex = "/^http(s)?:\\/\\/(www\\.)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$/.test('www.google.com')\n";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);

            return matcher.matches();
        }

        return false;
    }

    public static String analyticFormat(String str){
        return str.toLowerCase().replaceAll("[^A-Za-z0-9]+","_");
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
