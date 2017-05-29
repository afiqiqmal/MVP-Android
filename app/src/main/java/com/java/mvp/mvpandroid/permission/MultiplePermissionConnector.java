package com.java.mvp.mvpandroid.permission;

/**
 * @author : hafiq on 05/03/2017.
 */

public interface MultiplePermissionConnector {
    void isPermissionGranted(String name, boolean granted);
    void isPermissionShouldShowRationale(String name);
    void permissionPermanentDenied(String name);
    void isAllPermissionGranted(boolean allGranted, boolean permanentlyDenied);
}
