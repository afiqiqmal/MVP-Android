package com.java.mvp.mvpandroid.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.java.mvp.mvpandroid.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.annotations.NonNull;

/**
 * @author : hafiq on 23/01/2017.
 */

public class CommonUtils {

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

    @SuppressLint("SimpleDateFormat")
    public static String getDateFormat(long timestamp){
        long tt = timestamp * 1000L;
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date netDate = (new Date(tt));
        return sdf.format(netDate);
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


    public static boolean isHtml(String s) {
        String tagStart= "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
        String tagEnd= "\\</\\w+\\>";
        String tagSelfClosing= "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
        String htmlEntity= "&[a-zA-Z][a-zA-Z0-9]+;";
        Pattern htmlPattern=Pattern.compile("("+tagStart+".*"+tagEnd+")|("+tagSelfClosing+")|("+htmlEntity+")", Pattern.DOTALL);

        boolean ret=false;
        if (s != null) {
            ret=htmlPattern.matcher(s).find();
        }
        return ret;
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

    public static boolean checkPlayServices(Activity context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(context, resultCode, 9000).show();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(context.getResources().getString(R.string.app_name));
                alert.setCancelable(false);
                alert.setMessage(R.string.play_services_warning);
                alert.setPositiveButton("Exit", (dialog, which) -> System.exit(0));
            }
            return false;
        }
        return true;
    }

    public static boolean isPackageExisted(Context c, String targetPackage) {

        PackageManager pm = c.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat(format);
        return currentDate.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateRangeFormatted(@NonNull Date start,@NonNull Date end) {

        String day1 = new SimpleDateFormat("dd").format(start);
        String day2 = new SimpleDateFormat("dd").format(end);

        String month1 = new SimpleDateFormat("MMM").format(start);
        String month2 = new SimpleDateFormat("MMM").format(end);

        String year1 = new SimpleDateFormat("yyyy").format(start);
        String year2 = new SimpleDateFormat("yyyy").format(end);

        if (year1.equals(year2)) {
            if (month1.equals(month2)) {
                if (day1.equals(day2)) {
                    return new SimpleDateFormat("dd MMM yyyy").format(end);
                }
                return new SimpleDateFormat("dd").format(start) + " - " + new SimpleDateFormat("dd MMM yyyy").format(end);
            } else {
                return new SimpleDateFormat("dd MMM").format(start) + " - " + new SimpleDateFormat("dd MMM yyyy").format(end);
            }
        }
        else{
            return new SimpleDateFormat("dd MMM yyyy").format(start) + " - " + new SimpleDateFormat("dd MMM yyyy").format(end);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean checkTime(String time, String endTime, int expression) {

        String pattern = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endTime);

            if (expression == 0)
                return date1.before(date2);
            else if (expression == 1)
                return date1.after(date2);
            else if (expression == 2)
                return date1.equals(date2);
            else if (expression == 3)
                return date1.before(date2) || date1.equals(date2);
            else if (expression == 4)
                return date1.after(date2) || date1.equals(date2);

        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(Calendar.getInstance().getTime());
    }


}
