package com.java.mvp.mvpandroid.permission;

/**
 * @author : hafiq on 30/05/2017.
 */

public class RxPermissionView {

    public interface PermissionConnector {
        void isPermissionGranted(boolean granted);
    }

    public interface MultiplePermissionConnector {
        void isPermissionGranted(String name, boolean granted);
        void isPermissionShouldShowRationale(String name);
        void permissionPermanentDenied(String name);
        void isAllPermissionGranted(boolean allGranted, boolean permanentlyDenied);
    }
}
