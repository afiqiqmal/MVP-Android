package com.java.mvp.mvpandroid.helper;

/**
 * @author : hafiq on 07/02/2017.
 */

public enum Language {
    MALAY("ms"),
    ENGLISH("en");

    private final String s;

    Language(String s){
        this.s = s;
    }

    public String getName() {
        return s;
    }

    public static Language toLanguage (String str) {
        try {
            return valueOf(str);
        } catch (Exception ex) {
            return ENGLISH;
        }
    }
}
