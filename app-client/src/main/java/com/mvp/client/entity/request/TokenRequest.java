package com.mvp.client.entity.request;

/**
 * @author : hafiq on 23/01/2017.
 */

public class TokenRequest {

    private String device_id;
    private String os_type;
    private String os_version;
    private String apps_version;
    private String push_token;

    public TokenRequest(String device_id, String os_type, String os_version, String apps_version, String push_token) {
        this.device_id = device_id;
        this.os_type = os_type;
        this.os_version = os_version;
        this.apps_version = apps_version;
        this.push_token = push_token;
    }

    public void setDeviceType(String device_type) {
        this.device_id = device_type;
    }

    public void setOsType(String os_type) {
        this.os_type = os_type;
    }

    public void setOsVersion(String os_version) {
        this.os_version = os_version;
    }

    public void setAppsVersion(String apps_version) {
        this.apps_version = apps_version;
    }

    public void setPushToken(String push_token) {
        this.push_token = push_token;
    }

    public String getDeviceType() {
        return device_id;
    }

    public String getOsType() {
        return os_type;
    }

    public String getOsVersion() {
        return os_version;
    }

    public String getAppsVersion() {
        return apps_version;
    }

    public String getPushToken() {
        return push_token;
    }
}
