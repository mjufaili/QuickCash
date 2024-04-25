/*
Reference:
 Masud Rahman:
 Cookbook:
 https://git.cs.dal.ca/masud/csci3130-cookbook

 */


package com.example.g15_gp.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GPSTracker extends Service implements LocationListener {

    protected LocationManager locationManager;
    Context mContext;
    Location location;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60000; // 1 minute
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        location = getLocation();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location=location;
    }

    public double getLatitude() {
        if (location != null) {
            return location.getLatitude();
        }
        return 0.0;
    }

    public double getLongitude() {
        if (location != null) {
            return location.getLongitude();
        }
        return 0.0;
    }

    public boolean canGetLocation() {
        return location != null;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled && isNetworkEnabled) {
            if (locationManager.isLocationEnabled()) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return location;
            }
        } else {
            location = null;
        }
        return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
