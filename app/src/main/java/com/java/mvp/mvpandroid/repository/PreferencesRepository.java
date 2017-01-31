package com.java.mvp.mvpandroid.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Basyrun Halim
 */
@Singleton
public class PreferencesRepository {

    private static final String KEY_PUSH_TOKEN = "push_token";
    private static final String KEY_PUSH_TOKEN_SENT = "push_token_sent";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_SERVER_TOKEN = "server_token";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_AGE = "user_age";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_GENDER = "user_gender";
    private static final String KEY_USER_IMAGE_URL = "user_image_url";
    private static final String KEY_USER_TYPE = "user_type";

    private static final String KEY_FIRST_TIME = "first_time";
    private static final String KEY_HAS_LOGIN = "has_login";


    private static String TOKEN_SWITCH = "token.switch";
    private SharedPreferences mPreferences;
    private Context mContext;

    private SharedPreferences.Editor editor;

    @Inject
    public PreferencesRepository(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPreferences.edit();
    }

    public void savePushToken(String token) {
        SharedPreferences.Editor editor = mPreferences.edit();

        if (token == null) {
            editor.remove(KEY_PUSH_TOKEN);
        } else {
            editor.putString(KEY_PUSH_TOKEN, token);
        }

        editor.apply();
    }

    public boolean isEnableTokenPush(){
        return mPreferences.getBoolean(TOKEN_SWITCH,true);
    }

    public void enableTokenPush(boolean sent){
        mPreferences.edit().putBoolean(TOKEN_SWITCH,sent).apply();
    }

    public String getPushToken() {
        return mPreferences.getString(KEY_PUSH_TOKEN, null);
    }


    public boolean isPushTokenSent() {
        return mPreferences.getBoolean(KEY_PUSH_TOKEN_SENT, false);
    }

    public void setPushTokenSent(boolean sent) {
        editor.putBoolean(KEY_PUSH_TOKEN_SENT, sent).apply();
    }

    public void setServerToken(String token) {
        if (token == null) {
            editor.remove(KEY_SERVER_TOKEN).apply();
        } else {
            editor.putString(KEY_SERVER_TOKEN, token).apply();
        }
    }

    public String getServerToken() {
        return mPreferences.getString(KEY_SERVER_TOKEN, null);
    }

    public void setUser(String email, String name) {
        editor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .apply();
    }

    public void setUserUpdate(String email, String name, String age, String phone, String gender) {
        editor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_AGE, age)
                .putString(KEY_USER_PHONE, phone)
                .putString(KEY_USER_GENDER, gender)
                .apply();
    }

    public void setUserData(String email, String image, String name, String type) {
        editor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_IMAGE_URL, image)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_TYPE, type)
                .apply();
    }

    public void setAuthToken(String auth) {
        editor.putString(KEY_AUTH_TOKEN, auth).apply();
    }

    public String getAuthToken() {
        return mPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void setUserEmail(String email) {
        if (email == null) {
            editor.remove(KEY_USER_EMAIL).apply();
        } else {
            editor.putString(KEY_USER_EMAIL, email).apply();
        }
    }

    public String getUserEmail() {
        return mPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserName() {
        return mPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserAge() {
        return mPreferences.getString(KEY_USER_AGE, null);
    }

    public String getUserPhone() {
        return mPreferences.getString(KEY_USER_PHONE, null);
    }

    public String getUserGender() {
        return mPreferences.getString(KEY_USER_GENDER, null);
    }

    public String getUserImage() {
        return mPreferences.getString(KEY_USER_IMAGE_URL, null);
    }

    public String getUserType() {
        return mPreferences.getString(KEY_USER_TYPE, null);
    }


    public boolean isHasLogin() {
        return mPreferences.getBoolean(KEY_HAS_LOGIN, false);
    }

    public void setHasLogin(boolean login) {
        editor.putBoolean(KEY_HAS_LOGIN, login).apply();
    }
}
