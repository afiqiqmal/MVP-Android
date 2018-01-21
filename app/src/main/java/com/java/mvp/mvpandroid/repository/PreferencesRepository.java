package com.java.mvp.mvpandroid.repository;

import android.annotation.SuppressLint;
import android.content.Context;


import com.facebook.crypto.CryptoConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.mvp.mvpandroid.R;
import com.zeroone.conceal.ConcealPrefRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


/**
 * @author : hafiq on 07/02/2017.
 */

@SuppressWarnings("unchecked")
public class PreferencesRepository{

    private static final String KEY_PUSH_TOKEN = "push_token";
    private static final String KEY_PUSH_TOKEN_SENT = "push_token_sent";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_SERVER_TOKEN = "server_token";
    private static final String KEY_USER = "user";

    private static final String SCAN_VERIFIED = "has_verified";
    private static final String BEPUNCT_API_URL = "bepunct_api_url";
    private static final String BEPUNCT_ACTIVATION_CODE = "bepunct_code";


    private static final String USER_SIGN_IN = "user.check.signIn";
    private static final String USER_SIGN_IN_TIME = "user.check.signIn.time";

    private static final String HOLIDAY = "holiday";
    private static final String COMPANY_DETAIL = "company.detail";
    private static final String CONFIG_DETAIL = "config";

    private static final String SHORCUT_DEVICE = "shortcut.device.app";

    private static String TOKEN_SWITCH = "token.switch";


    private Context mContext;

    private ConcealPrefRepository concealPrefRepository;
    private ConcealPrefRepository.Editor concealEditor;

    @Inject
    public PreferencesRepository(Context context) {
        mContext = context;
        concealPrefRepository = new ConcealPrefRepository.PreferencesBuilder(mContext)
                .useDefaultPrefStorage()
                .sharedPrefsBackedKeyChain(CryptoConfig.KEY_256)
                .enableCrypto(true,true)
                .createPassword(mContext.getString(R.string.app_name))
                .setFolderName(mContext.getString(R.string.app_name))
                .create();

        concealEditor = new ConcealPrefRepository.Editor();
    }

    public ConcealPrefRepository.UserPref getUserPref(){
        return new ConcealPrefRepository.UserPref();
    }

    public ConcealPrefRepository getPref(){
        return concealPrefRepository;
    }

    public boolean isShortCutCreated() {
        return concealPrefRepository.getBoolean(SHORCUT_DEVICE, false);
    }

    public void setShortCutCreate(boolean bool) {
        concealPrefRepository.putBoolean(SHORCUT_DEVICE, bool);
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

    public void setAuthToken(String auth) {
        concealPrefRepository.putString(KEY_AUTH_TOKEN, auth);
    }

    public String getAuthToken() {
        return concealPrefRepository.getString(KEY_AUTH_TOKEN, null);
    }




    public void logout() {
        getUserPref().setLogin(false);
    }
}
