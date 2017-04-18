package com.java.mvp.mvpandroid.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.facebook.crypto.CryptoConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.mvp.mvpandroid.helper.Language;
import com.java.mvp.mvpandroid.helper.LanguageLocalHelper;
import com.logger.min.easylogger.Logger;
import com.mvp.client.internal.Constant;
import com.zeroone.conceal.ConcealPrefRepository;

import java.util.ArrayList;
import java.util.List;

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

    private static final String STATES = "states.selected";


    private static String TOKEN_SWITCH = "token.switch";
    private static String RECENT_SEARCH = "search.recent";

    private Context mContext;

    private ConcealPrefRepository concealPrefRepository;
    private ConcealPrefRepository.Editor concealEditor;

    @SuppressLint("CommitPrefEdits")
    @Inject
    public PreferencesRepository(Context context) {
        mContext = context;
        concealPrefRepository = new ConcealPrefRepository.PreferencesBuilder(mContext)
                .useDefaultPrefStorage()
                .sharedPrefsBackedKeyChain(CryptoConfig.KEY_256)
                .enableCrypto(true,true)
                .createPassword("mvp12345")
                .setFolderName("mvp_path")
                .create();

        concealEditor = new ConcealPrefRepository.Editor();
    }

    public void savePushToken(String token) {
        if (token == null) {
            concealPrefRepository.remove(KEY_PUSH_TOKEN);
        } else {
            concealPrefRepository.putString(KEY_PUSH_TOKEN, token);
        }
    }

    public boolean isEnableTokenPush(){
        return concealPrefRepository.getBoolean(TOKEN_SWITCH,true);
    }

    public void enableTokenPush(boolean sent){
        concealPrefRepository.putBoolean(TOKEN_SWITCH,sent);
    }

    public String getPushToken() {
        return concealPrefRepository.getString(KEY_PUSH_TOKEN, null);
    }


    public boolean isPushTokenSent() {
        return concealPrefRepository.getBoolean(KEY_PUSH_TOKEN_SENT, false);
    }

    public void setPushTokenSent(boolean sent) {
        concealPrefRepository.putBoolean(KEY_PUSH_TOKEN_SENT, sent);
    }

    public void setServerToken(String token) {
        if (token == null) {
            concealPrefRepository.remove(KEY_SERVER_TOKEN);
        } else {
            concealPrefRepository.putString(KEY_SERVER_TOKEN, token);
        }
    }

    public String getServerToken() {
        return concealPrefRepository.getString(KEY_SERVER_TOKEN, null);
    }

    public void setUser(String email, String name) {
        concealEditor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .apply();
    }

    public void setUserUpdate(String email, String name, String age, String phone, String gender) {
        concealEditor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_AGE, age)
                .putString(KEY_USER_PHONE, phone)
                .putString(KEY_USER_GENDER, gender)
                .apply();
    }

    public void setUserData(String email, String image, String name, String type) {
        concealEditor.putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_IMAGE_URL, image)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_TYPE, type)
                .apply();
    }

    public void setAuthToken(String auth) {
        concealPrefRepository.putString(KEY_AUTH_TOKEN, auth);
    }

    public String getAuthToken() {
        return concealPrefRepository.getString(KEY_AUTH_TOKEN, null);
    }

    public void setUserEmail(String email) {
        if (email == null) {
            concealPrefRepository.remove(KEY_USER_EMAIL);
        } else {
            concealPrefRepository.putString(KEY_USER_EMAIL, email);
        }
    }

    public String getUserEmail() {
        return concealPrefRepository.getString(KEY_USER_EMAIL, null);
    }

    public String getUserName() {
        return concealPrefRepository.getString(KEY_USER_NAME, null);
    }

    public String getUserAge() {
        return concealPrefRepository.getString(KEY_USER_AGE, null);
    }

    public String getUserPhone() {
        return concealPrefRepository.getString(KEY_USER_PHONE, null);
    }

    public String getUserGender() {
        return concealPrefRepository.getString(KEY_USER_GENDER, null);
    }

    public String getUserImage() {
        return concealPrefRepository.getString(KEY_USER_IMAGE_URL, null);
    }

    public String getUserType() {
        return concealPrefRepository.getString(KEY_USER_TYPE, null);
    }


    public boolean isHasLogin() {
        return concealPrefRepository.getBoolean(KEY_HAS_LOGIN, false);
    }

    public void setHasLogin(boolean login) {
        concealPrefRepository.putBoolean(KEY_HAS_LOGIN, login);
    }


    public boolean isFirstTime(){
        return concealPrefRepository.getBoolean(KEY_FIRST_TIME, true);
    }

    public void setFirstTime(boolean value){
        concealPrefRepository.putBoolean(KEY_FIRST_TIME, value);
    }


    public void setStringValue(String key,String value){
        concealPrefRepository.putString(key,value);
    }

    public void clear(){
        concealPrefRepository.destroySharedPreferences();
    }

    public String getValue(String key){
        return concealPrefRepository.getString(key,null);
    }


    public void changeLanguage(Language lang, Context activity){
        if (lang.equals(Language.ENGLISH)){
            Logger.debug("en :"+lang.getName());
            setStringValue(Constant.LANGUAGE,lang.getName());
            LanguageLocalHelper.setLocale(activity,lang.getName());
        }
        else if (lang.equals(Language.MALAY)){
            Logger.debug("ms :"+lang.getName());
            setStringValue(Constant.LANGUAGE,lang.getName());
            LanguageLocalHelper.setLocale(activity,lang.getName());
        }
    }

    public Language getLanguage(){
        return Language.toLanguage(getValue(Constant.LANGUAGE));
    }
}
