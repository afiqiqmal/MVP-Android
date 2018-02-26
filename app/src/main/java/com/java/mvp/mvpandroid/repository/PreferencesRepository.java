package com.java.mvp.mvpandroid.repository;

import android.annotation.SuppressLint;
import android.content.Context;


import com.chamber.java.library.SharedChamber;
import com.chamber.java.library.model.ChamberType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.mvp.mvpandroid.R;

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

    private SharedChamber sharedChamber;

    @Inject
    public PreferencesRepository(Context context) {
        mContext = context;
        sharedChamber = new SharedChamber.ChamberBuilder(mContext)
                .setChamberType(ChamberType.KEY_256)
                .enableCrypto(true,true)
                .setPassword(mContext.getString(R.string.app_name))
                .setFolderName(mContext.getString(R.string.app_name))
                .buildChamber();

    }

    public SharedChamber.UserChamber getUserPref(){
        return new SharedChamber.UserChamber();
    }

    public SharedChamber getPref(){
        return sharedChamber;
    }

    public boolean isShortCutCreated() {
        return sharedChamber.getBoolean(SHORCUT_DEVICE, false);
    }

    public void setShortCutCreate(boolean bool) {
        sharedChamber.put(SHORCUT_DEVICE, bool);
    }

    public void savePushToken(String token) {
        if (token == null) {
            sharedChamber.remove(KEY_PUSH_TOKEN);
        } else {
            sharedChamber.put(KEY_PUSH_TOKEN, token);
        }
    }

    public boolean isEnableTokenPush(){
        return sharedChamber.getBoolean(TOKEN_SWITCH,true);
    }

    public void enableTokenPush(boolean sent){
        sharedChamber.put(TOKEN_SWITCH,sent);
    }

    public String getPushToken() {
        return sharedChamber.getString(KEY_PUSH_TOKEN, null);
    }


    public boolean isPushTokenSent() {
        return sharedChamber.getBoolean(KEY_PUSH_TOKEN_SENT, false);
    }

    public void setPushTokenSent(boolean sent) {
        sharedChamber.put(KEY_PUSH_TOKEN_SENT, sent);
    }

    public void setServerToken(String token) {
        if (token == null) {
            sharedChamber.remove(KEY_SERVER_TOKEN);
        } else {
            sharedChamber.put(KEY_SERVER_TOKEN, token);
        }
    }

    public String getServerToken() {
        return sharedChamber.getString(KEY_SERVER_TOKEN, null);
    }

    public void setAuthToken(String auth) {
        sharedChamber.put(KEY_AUTH_TOKEN, auth);
    }

    public String getAuthToken() {
        return sharedChamber.getString(KEY_AUTH_TOKEN, null);
    }


    public void logout() {
        getUserPref().setLogin(false);
    }
}
