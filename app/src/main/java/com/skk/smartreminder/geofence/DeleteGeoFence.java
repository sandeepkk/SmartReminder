package com.skk.smartreminder.geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.Arrays;
import java.util.List;


/**
 * Created by sandeepkannan on 12/11/14.
 */
public class DeleteGeoFence implements ConnectionCallbacks, LocationClient.OnRemoveGeofencesResultListener, OnConnectionFailedListener {
    LocationClient mLocationClient;
    private Context mContext;
    private List<String> mCurrentGeofenceIds;

    public DeleteGeoFence(Context context) {
        mContext = context;

        // Initialize the globals to null
        mCurrentGeofenceIds = null;
        mLocationClient = null;
    }

    public void removeGeofencesById(List<String> geofenceIds) throws
            IllegalArgumentException, UnsupportedOperationException {
        // If the List is empty or null, throw an error immediately
        if ((null == geofenceIds) || (geofenceIds.size() == 0)) {
            throw new IllegalArgumentException();

        } else {
            mCurrentGeofenceIds = geofenceIds;
            locationConnection().connect();
        }
    }

    private GooglePlayServicesClient locationConnection() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(mContext, this, this);
        }
        return mLocationClient;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationClient.removeGeofences(mCurrentGeofenceIds, this);
    }


    @Override
    public void onDisconnected() {

    }

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int i, String[] strings) {
        locationConnection().disconnect();
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) {
        locationConnection().disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
