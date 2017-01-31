package com.mvp.client.entity.response;

/**
 * @author : hafiq on 23/01/2017.
 */

public class TokenResponse {

    private String error;
    private String message;
    private String token;
    private String nickname;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }
}