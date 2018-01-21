package com.java.mvp.mvpandroid.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.listener.DialogPositionClickCallback;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

/**
 * Created by hafiq on 13/12/2017.
 */

public class DialogUtils {

    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public static void getImageFromGallery(Context context, DialogPositionClickCallback dialogPositionClickCallback)
    {
        final CharSequence[] items = {"Gallery", "Camera"};
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        alert.setTitle("Choose Attachment");
        alert.setItems(items, (dialogInterface, item) -> {
            if (item == 0) {
                getImageUri(context, Sources.GALLERY, dialogPositionClickCallback);
            } else {
                getImageUri(context, Sources.CAMERA, dialogPositionClickCallback);
            }

            dialogInterface.dismiss();
        });
        alert.setOnDismissListener(dialogInterface -> {
            dialogPositionClickCallback.imageDialogCallback(null);
        });
        alert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alert.show();
    }


    private static void getImageUri(Context context, Sources sources, DialogPositionClickCallback dialogPositionClickCallback)
    {
        RxImagePicker.with(context).requestImage(sources).subscribe(dialogPositionClickCallback::imageDialogCallback);
    }

}
