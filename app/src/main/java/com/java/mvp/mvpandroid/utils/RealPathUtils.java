package com.java.mvp.mvpandroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

/**
 * Created by hafiq on 13/12/2017.
 */

public class RealPathUtils {

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = null;
        try {

            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor != null ? cursor.getColumnIndex(column[0]) : 0;

            if (cursor != null && cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        catch (Exception e){
            return getRealPathFromURI_API11to18(context,uri);
        }
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;
        try {
            CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("Recycle")
    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor != null ? cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) : 0;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor != null ? cursor.getString(column_index) : null;
    }
}