package com.java.mvp.mvpandroid.utils;

import android.support.annotation.NonNull;

/**
 * @author : hafiq on 10/10/2016.
 */

public class VersionUtils implements Comparable<VersionUtils> {

    private String version;

    public final String get() {
        return this.version;
    }

    public VersionUtils(String version) {
        if(version == null)
            throw new IllegalArgumentException("Version can not be null");
        if(!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");
        this.version = version;
    }

    @Override
    public int compareTo(@NonNull VersionUtils next) {
        String[] curStr = this.get().split("\\.");
        String[] nextStr = next.get().split("\\.");
        int length = Math.max(curStr.length, nextStr.length);
        for(int i = 0; i < length; i++) {

            int currentVer = i < curStr.length ? Integer.parseInt(curStr[i]) : 0;
            int nextVersion = i < nextStr.length ? Integer.parseInt(nextStr[i]) : 0;

            if(currentVer < nextVersion)
                return -1;
            if(currentVer > nextVersion)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object next) {
        return this == next || next != null && this.getClass() == next.getClass() && this.compareTo((VersionUtils) next) == 0;
    }

}
