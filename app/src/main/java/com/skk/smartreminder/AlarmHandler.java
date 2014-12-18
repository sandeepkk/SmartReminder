package com.skk.smartreminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.skk.smartreminder.activities.MainActivity;
import com.skk.smartreminder.utils.DatabaseHelper;

/**
 * Created by Archie on 12/12/2014.
 */

public class AlarmHandler extends BroadcastReceiver {
    private int numMessages = 0;
    private long dataID;
    private String title = "Title";


    @Override
    public void onReceive(Context arg0, Intent arg1) {

        dataID = arg1.getLongExtra("idNumber", -1);

        if(dataID>(-1))
        {
            Cursor constantsCursor = DatabaseHelper.getInstance(arg0).loadReminderDetails(dataID);
            constantsCursor.move(1);

            title = (constantsCursor.getString(constantsCursor.getColumnIndex(DatabaseHelper.TITLE)));

            constantsCursor.close();
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(arg0)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText("Check Reminder");

        // Start of a loop that processes data and then notifies the user
        mBuilder.setNumber(++numMessages);
        mBuilder.setAutoCancel(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(arg0, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets an ID for the notification
        int mNotificationId = 001;

        // Builds the notification and issues it.
        mNotificationManager.notify(mNotificationId, mBuilder.build());

        // For our recurring task, we'll just display a message
        Toast.makeText(arg0, "test", Toast.LENGTH_SHORT).show();
    }
}
