package com.java.mvp.mvpandroid.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.java.mvp.mvpandroid.location.exception.ConnectionException;
import com.java.mvp.mvpandroid.location.exception.ConnectionSuspendedException;
import com.java.mvp.mvpandroid.location.exception.LocationDisabledException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Cancellable;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * @author Noorzaini Ilhami
 */
class RxFusedLocation {
    private GoogleApiClient mClient;
    private ApiCallbacks mCallbacks;

    RxFusedLocation(Context context) {
        mCallbacks = new ApiCallbacks();
        mClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mCallbacks)
                .addOnConnectionFailedListener(mCallbacks)
                .build();
    }

    public void connect() {
        if (!mClient.isConnected()) {
            mClient.connect();
        }
    }

    public Single<Location> getLocation(LocationRequest request) {
        return Single.create(new LocationSingle(request));
    }

    public void disconnect() {
        if (mClient.isConnected() || mClient.isConnecting()) {
            mClient.disconnect();
        }
    }

    private class LocationSingle implements SingleOnSubscribe<Location>, LocationListener,
            Cancellable {

        private final LocationRequest request;
        private SingleEmitter<Location> emitter;

        LocationSingle(LocationRequest request) {
            this.request = request;
        }

        @Override
        public void subscribe(SingleEmitter<Location> e) throws Exception {
            emitter = e;
            emitter.setCancellable(this);
            mCallbacks.addObserver(this);

            if (mClient.isConnected()) {
                requestLocationUpdates();
            }
        }

        private void checkLocationSettings() {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(request);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    Status status = result.getStatus();

                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            emitter.onError(new LocationDisabledException(status));
                            break;
                    }
                }
            });
        }

        private void requestLocationUpdates() {
            try {
                checkLocationSettings();
                FusedLocationApi.requestLocationUpdates(mClient, request, this);
            } catch (SecurityException e) {
                onError(e);
            } catch (IllegalStateException e) {
                onError(new LocationDisabledException());
            } catch (Exception e) {
                onError(e);
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            emitter.onSuccess(location);
        }

        private void onConnected() {
            if (emitter != null) {
                requestLocationUpdates();
            }
        }

        private void onError(Throwable e) {
            if (emitter != null) {
                emitter.onError(e);
            }
        }

        @Override
        public void cancel() throws Exception {
            if (mClient.isConnected()) {
                FusedLocationApi.removeLocationUpdates(mClient, this);
            }

            mCallbacks.removeCallback(this);
        }
    }

    private class ApiCallbacks implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        private List<LocationSingle> observers;

        ApiCallbacks() {
            observers = new CopyOnWriteArrayList<>();
        }

        void addObserver(LocationSingle observer) {
            if (!observers.contains(observer)) observers.add(observer);
            connect();
        }

        void removeCallback(LocationSingle observer) {
            observers.remove(observer);

            if (observers.isEmpty()) {
                disconnect();
            }
        }

        @Override
        public void onConnected(Bundle bundle) {
            for (LocationSingle o : observers) {
                o.onConnected();
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
            for (LocationSingle o : observers) {
                o.onError(new ConnectionSuspendedException(i));
            }
        }

        @Override
        public void onConnectionFailed(ConnectionResult result) {
            for (LocationSingle o : observers) {
                o.onError(new ConnectionException("Failed to connect to GoogleApi.", result));
            }
        }
    }
}
