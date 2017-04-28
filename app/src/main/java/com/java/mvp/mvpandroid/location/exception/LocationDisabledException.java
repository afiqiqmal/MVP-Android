package com.java.mvp.mvpandroid.location.exception;

import com.google.android.gms.common.api.Status;

/**
 * @author Noorzaini Ilhami
 */
public class LocationDisabledException extends LocationException {

    private Status mStatus;

    public LocationDisabledException() {
    }

    public LocationDisabledException(Status status) {
        mStatus = status;
    }

    public boolean hasStatus() {
        return mStatus != null;
    }

    public Status getStatus() {
        return mStatus;
    }
}
