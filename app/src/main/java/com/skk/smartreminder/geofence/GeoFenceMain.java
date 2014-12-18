package com.skk.smartreminder.geofence;
import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.skk.smartreminder.geofence.CreateGeoFence;
import com.skk.smartreminder.geofence.DeleteGeoFence;
import com.skk.smartreminder.utils.GeoFenceConsants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepkannan on 12/10/14.
 */
public class GeoFenceMain {
    private List<Geofence> mListGeoFence;
    private List<String> mListGeoFenceId;

    private CreateGeoFence mCreateGeoFence;
    private DeleteGeoFence mDeleteGeoFence;
    double mLatitude;
    double mLongitude;
    float mRadius;
    int mErrorCode=0;
    String mId;


    private boolean checkInputs(double latitude, double longitude, float radius){
        boolean clear= true;
        if (latitude> GeoFenceConsants.MAX_LATITUDE || latitude<GeoFenceConsants.MIN_LATITUDE ||
        longitude>GeoFenceConsants.MAX_LONGITUDE || longitude<GeoFenceConsants.MIN_LONGITUDE ||
                radius <GeoFenceConsants.MIN_RADIUS) {
            mErrorCode = GeoFenceConsants.INVALID_POSITION;
            clear = false;
        }
        return clear;
    }

    private void createGeoFenceObject() {
        mListGeoFence = new ArrayList<Geofence>();
        mListGeoFence.add(
         new Geofence.Builder()
                .setRequestId(mId)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(mLatitude,mLongitude,mRadius)
                .setExpirationDuration(GeoFenceConsants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .build());
    }

    public boolean addGeoFence(Context context, String id, double latitude, double longitude, float radius) {
        boolean added = false;
        if (checkInputs(latitude,longitude,radius)) {
            mCreateGeoFence = new CreateGeoFence(context);
            this.mLatitude = latitude;
            this.mLongitude = longitude;
            this.mRadius = radius;
            this.mId = id;

            createGeoFenceObject();
            mCreateGeoFence.addGeoFence(mListGeoFence);
            added = true;
        } else{
            added = false;
        }
        return added;

    }

    public void removeGeoFence(Context context, String id) {
        mDeleteGeoFence  = new DeleteGeoFence(context);
        mListGeoFenceId = new ArrayList<String>();
        mListGeoFenceId.add(id);
        mDeleteGeoFence.removeGeofencesById(mListGeoFenceId);

    }

}
