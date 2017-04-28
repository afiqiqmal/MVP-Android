package com.java.mvp.mvpandroid.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.android.gms.location.LocationRequest;
import com.java.mvp.mvpandroid.location.exception.LocationTimeoutException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Noorzaini Ilhami
 */
@Singleton
public class LocationRepository {

    private static final long LOCATION_CACHE_DURATION = 604800000; // 7 days
    private static final long LOCATION_REQUEST_TIMEOUT = 15000; // 15 seconds

    private static final String PREF_CACHE_LATITUDE = "location_cache_lat";
    private static final String PREF_CACHE_LONGITUDE = "location_cache_lng";
    private static final String PREF_CACHE_TIME = "location_cache_time";

    private final RxFusedLocation mFusedLocation;
    private final SharedPreferences mPreferences;

    private Location mLastLocation;

    @Inject
    public LocationRepository(Context context) {
        mFusedLocation = new RxFusedLocation(context);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Builder getLocation() {
        LocationRequest request = LocationRequest.create()
                .setNumUpdates(1);

        return getLocation(request);
    }

    public Builder getLocation(LocationRequest request) {
        return new Builder(request);
    }

    /**
     * Retrieves the stored Location from SharedPreferences.
     *
     * @return location
     */
    @Nullable
    private Location getLocationCache() {
        long rawLat = mPreferences.getLong(PREF_CACHE_LATITUDE, -1);
        long rawLng = mPreferences.getLong(PREF_CACHE_LONGITUDE, -1);
        long time = mPreferences.getLong(PREF_CACHE_TIME, -1);

        if (rawLat == -1 || rawLng == -1 || time == -1) {
            return null;
        }

        Location l = new Location("cache");
        l.setLatitude(Double.longBitsToDouble(rawLat));
        l.setLongitude(Double.longBitsToDouble(rawLng));
        l.setTime(time);

        return l;
    }

    /**
     * Stores the Location into SharedPreferences.
     *
     * @param location
     */
    private void setLocationCache(Location location) {
        mPreferences.edit()
                .putLong(PREF_CACHE_LATITUDE, Double.doubleToRawLongBits(location.getLatitude()))
                .putLong(PREF_CACHE_LONGITUDE, Double.doubleToRawLongBits(location.getLongitude()))
                .putLong(PREF_CACHE_TIME, location.getTime())
                .apply();
    }

    public class Builder {
        private final LocationRequest request;

        private boolean skipCache = false;

        Builder(LocationRequest request) {
            this.request = request;
        }

        private Single<Location> getLocation(LocationRequest request) {
            if (!shouldRequestNewLocation()) {
                return Single.just(mLastLocation);
            }

            return mFusedLocation.getLocation(request)
                    .doOnSuccess(new Consumer<Location>() {
                        @Override
                        public void accept(Location location) throws Exception {
                            setLocationCache(location);
                        }
                    })
                    .timeout(LOCATION_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                    .onErrorResumeNext(new Function<Throwable, SingleSource<? extends Location>>() {
                        @Override
                        public SingleSource<? extends Location> apply(Throwable e) throws Exception {
                            if (e instanceof TimeoutException && mLastLocation == null) {
                                return Single.error(new LocationTimeoutException());
                            } else if (mLastLocation == null) {
                                return Single.error(e);
                            } else {
                                return Single.just(mLastLocation);
                            }
                        }
                    });
        }

        private boolean shouldRequestNewLocation() {
            if (skipCache) {
                return true;
            }

            Location cache = getLocationCache();

            if (mLastLocation == null && cache == null) {
                return true;
            }

            if (mLastLocation == null) {
                mLastLocation = cache;
            }

            if (System.currentTimeMillis() - mLastLocation.getTime() > LOCATION_CACHE_DURATION) {
                return true;
            }

            return false;
        }

        public Builder skipCache() {
            skipCache = true;
            return this;
        }

        public Single<Location> asSingle() {
            return getLocation(request);
        }
    }
}
