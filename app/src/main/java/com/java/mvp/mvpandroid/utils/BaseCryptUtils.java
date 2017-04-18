package com.java.mvp.mvpandroid.utils;

import android.content.Context;
import android.util.Base64;

/**
 * @author : hafiq on 15/02/2017.
 */

public class BaseCryptUtils {

    public static final class Builder{
        Context ac;
        public Builder(Context ac){
            this.ac = ac;
        }

        public String decodeStringWithIteration(String encoded) {
            try {
                byte[] dataDec = encoded.getBytes("UTF-8");
                for (int x=0;x<4;x++){
                    dataDec = Base64.decode(dataDec,Base64.NO_WRAP);
                }

                return new String(dataDec);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return "";
        }
    }
}

