package com.java.mvp.mvpandroid.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

/**
 * @author : hafiq on 15/02/2017.
 */

public class BaseCryptUtils {

    @Inject
    public BaseCryptUtils() {}

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

    public String AesCrypt(String key, String iv, String data) {
        try {
            int CIPHER_KEY_LEN = 16;
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                StringBuilder keyBuilder = new StringBuilder(key);
                for(int i = 0; i < numPad; i++){
                    keyBuilder.append("0");
                }
                key = keyBuilder.toString();

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }


            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = Base64.encodeToString(encryptedData, Base64.DEFAULT);
            String base64_IV = Base64.encodeToString(iv.getBytes("UTF-8"), Base64.DEFAULT);

            return Base64.encodeToString((base64_EncryptedData + ":" + base64_IV).getBytes("UTF-8"), Base64.DEFAULT);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String AesDecrypt(String key, String data) {
        try {

            int CIPHER_KEY_LEN = 16;
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                StringBuilder keyBuilder = new StringBuilder(key);
                for(int i = 0; i < numPad; i++){
                    keyBuilder.append("0");
                }
                key = keyBuilder.toString();

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            String[] parts = new String(Base64.decode(data, Base64.DEFAULT)).split(":");

            IvParameterSpec iv = new IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

