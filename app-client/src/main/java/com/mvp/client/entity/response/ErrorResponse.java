package com.mvp.client.entity.response;

/**
 * @author : hafiq on 30/10/2017.
 */

public class ErrorResponse {

    private boolean error;
    private String message;
    private String reference;
    private boolean should_quit;
    private boolean should_login;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isShould_quit() {
        return should_quit;
    }

    public void setShould_quit(boolean should_quit) {
        this.should_quit = should_quit;
    }

    public boolean isShould_login() {
        return should_login;
    }

    public void setShould_login(boolean should_login) {
        this.should_login = should_login;
    }
}
