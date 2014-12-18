package com.skk.smartreminder.utils;

/**
 * Created by sandeepkannan on 12/17/14.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.skk.smartreminder.R;
import com.skk.smartreminder.activities.ShowReminder;

import java.util.List;

/**
 * Created by sandeepkannan on 12/9/14.
 */
public  class ReminderReceiver extends BroadcastReceiver {
    Context context;
    String[] strinIds;
    Intent broadcastIntent = new Intent();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.skk.smartreminder.geofence")) {
            // if (intent  instanceof )
            this.context = context;
            broadcastIntent.addCategory(GeoFenceConsants.CATEGORY_LOCATION_SERVICES);
            handleEnter(intent);
        } else  if (intent.getAction().equals("com.skk.smartreminder.alarm")) {
            //call timer
        }

    }




    private void handleEnter(Intent intent) {
        int transition = LocationClient.getGeofenceTransition(intent);
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Post a notification
            List<Geofence> geofences = LocationClient
                    .getTriggeringGeofences(intent);
            strinIds = new String[geofences.size()];
            int index=0;
            for (Geofence geofence : geofences) {

                strinIds[index]= geofence.getRequestId();

            }
            index=0;
            List<String> lst = DatabaseHelper.getInstance(this.context).loadTitlesForNotification(strinIds);
            for (String  str :lst) {
                sendNotification((String)lst.get(index));
            }
        } else {
            // Always log as an error
        }
    }


    private void sendNotificationNew(String title) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "New Notification";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0,100,200,200,200,200};
        notification.vibrate = vibrate;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        CharSequence contentTitle = "Title";
        CharSequence contentText = "Text";
        Intent notificationIntent = new Intent(context, ShowReminder.class);
        notificationIntent.putExtra("myId", 4);
        notificationIntent.putExtra("name", "Sandeep");
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent contentIntent = PendingIntent.getActivity(context, iUniqueId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int mynotification_id = 1;

        mNotificationManager.notify(mynotification_id, notification);
    }
    private void sendNotification(String title) {
        Intent notificationIntent = new Intent(context, ShowReminder.class);
        notificationIntent.putExtra(GeoFenceConsants.NOTIFICATION_ID,strinIds[0]);
        notificationIntent.putExtra(GeoFenceConsants.FROM_INTENT,GeoFenceConsants.NOTIFICATION_INTENT);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ShowReminder.class);
        stackBuilder.addNextIntent(notificationIntent);
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent notificationPendingIntent = stackBuilder
                .getPendingIntent(iUniqueId, PendingIntent.FLAG_UPDATE_CURRENT);

        //int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        // PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, iUniqueId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText( "Click here to open app").setTicker("New Reminder")
                .setContentIntent(notificationPendingIntent);
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Issue the notification
        mNotificationManager.notify(iUniqueId, builder.build());
    }


    public static String getErrorString(Context context, int errorCode) {

        // Get a handle to resources, to allow the method to retrieve messages.
        Resources mResources = context.getResources();

        // Define a string to contain the error message
        String errorString;

        // Decide which error message to get, based on the error code.
        switch (errorCode) {

            case ConnectionResult.DEVELOPER_ERROR:
                errorString = "DEVELOPER ERROR";
                break;

            case ConnectionResult.INTERNAL_ERROR:
                errorString = "INTERNAL ERROR";
                break;

            case ConnectionResult.INVALID_ACCOUNT:
                errorString = "The Account is invalid";
                break;

            case ConnectionResult.LICENSE_CHECK_FAILED:
                errorString = "The licence check failed";
                break;

            case ConnectionResult.NETWORK_ERROR:
                errorString = "There was a problem connecting to network";
                break;

            case ConnectionResult.RESOLUTION_REQUIRED:
                errorString = "Addition information needed";
                break;

            case ConnectionResult.SERVICE_DISABLED:
                errorString = "Google play serivce disabled";
                break;

            case ConnectionResult.SERVICE_INVALID:
                errorString = "The version of Google play on this device is not matching";
                break;

            case ConnectionResult.SERVICE_MISSING:
                errorString = "Google play sermice missing";
                break;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                errorString = "Please update the google play";
                break;

            case ConnectionResult.SIGN_IN_REQUIRED:
                errorString = "Please sign in to your google service";
                break;

            default:
                errorString = "Unknown error";
                break;
        }

        // Return the error message
        return errorString;
    }
}
