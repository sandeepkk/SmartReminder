package com.skk.smartreminder.utils;

import android.text.format.DateUtils;

/**
 * Created by sandeepkannan on 12/10/14.
 */
public class GeoFenceConsants {


    // Used to track what type of geofence removal request was made.
    public enum REMOVE_TYPE {INTENT, LIST}
    public static String NOTIFICATION_ID="com.skk.reminder.notificationId";
    public static String NOTIFICATION_INTENT="com.skk.reminder.notificationIntent";

    public static String FROM_INTENT="com.skk.reminder.fromIntent";


    // Used to track what type of request is in process
    public enum REQUEST_TYPE {ADD, REMOVE}

    /*
     * A log tag for the application
     */
    public static final String APPTAG = "Geofence Detection";

    public static final CharSequence GEOFENCE_ID_DELIMITER = ",";
    // Intent actions
    public static final String ACTION_CONNECTION_ERROR =
            "come.anrlabs.ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS =
            "come.anrlabs.ACTION_CONNECTION_SUCCESS";



    public static final String ACTION_GEOFENCE_TRANSITION =
            "come.anrlabs.ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
            "come.anrlabs.ACTION_GEOFENCE_TRANSITION_ERROR";



    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
            "com.example.android.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "come.anrlabs.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "come.anrlabs.EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS =
            "come.anrlabs.EXTRA_GEOFENCE_STATUS";

    /*
     * Keys for flattened geofences stored in SharedPreferences
     */
    public static final String KEY_LATITUDE = "come.anrlabs.KEY_LATITUDE";

    public static final String KEY_LONGITUDE = "come.anrlabs.KEY_LONGITUDE";

    public static final String KEY_RADIUS = "come.anrlabs.KEY_RADIUS";

    public static final String KEY_EXPIRATION_DURATION =
            "come.anrlabs.KEY_EXPIRATION_DURATION";

    public static final String KEY_TRANSITION_TYPE =
            "come.anrlabs.KEY_TRANSITION_TYPE";

    // The prefix for flattened geofence keys
    public static final String KEY_PREFIX =
            "come.anrlabs.KEY";




    //Error Code
    public static final  int INVALID_POSITION = 1;
    /*
     * Constants used in verifying the correctness of input values
     */
    public static final double MAX_LATITUDE = 90.d;

    public static final double MIN_LATITUDE = -90.d;

    public static final double MAX_LONGITUDE = 180.d;

    public static final double MIN_LONGITUDE = -180.d;

    public static final float MIN_RADIUS = 1f;

    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 1;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * DateUtils.HOUR_IN_MILLIS;

    public static final String ACTION_GEOFENCES_ADDED =
            "com.anrlabs.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "com.anrlabs.ACTION_GEOFENCES_DELETED";
    public static final String ACTION_GEOFENCE_ERROR =
            "com.anrlabs.ACTION_GEOFENCES_ERROR";

    public static final String CATEGORY_LOCATION_SERVICES =
            "com.anrlabs.CATEGORY_LOCATION_SERVICES";
}
