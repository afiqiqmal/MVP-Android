package com.java.mvp.mvpandroid.location.exception;


import com.google.android.gms.common.ConnectionResult;

/**
 * @author Noorzaini Ilhami
 */
public class ConnectionException extends LocationException {

    private ConnectionResult mConnectionResult;

    public ConnectionException(String message, ConnectionResult result) {
        super(message);
        mConnectionResult = result;
    }

    public ConnectionResult getConnectionResult() {
        return mConnectionResult;
    }
}
