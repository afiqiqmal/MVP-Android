package com.java.mvp.mvpandroid.location.exception;

/**
 * @author Noorzaini Ilhami
 */
public class ConnectionSuspendedException extends LocationException {

    private int mErrorCode;

    public ConnectionSuspendedException(int errorCode) {
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
