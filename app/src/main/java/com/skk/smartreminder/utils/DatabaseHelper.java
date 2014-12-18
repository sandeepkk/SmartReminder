package com.skk.smartreminder.utils;

/**
 * Created by Grand on 12/9/2014.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.skk.smartreminder.R;
import com.skk.smartreminder.activities.ShowReminder;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NOTIFIED = "notified";
    public static final String XCOORDS = "xcoords";
    public static final String YCOORDS = "ycoords";
    public static final String RADIUS = "radius";
    public static final String TABLE = "reminders";
    public static final String LOCATION_NAME = "locationName";

    private static final String DATABASE_NAME = "db";

    static final int DATABASE_VERSION = 1;
    Cursor cursor;

    SQLiteDatabase db;

    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +
                " TEXT, "+ MESSAGE + " TEXT, " + DATE + " TEXT, "+ TIME + " TEXT, " + XCOORDS +
                " TEXT, " + YCOORDS + " TEXT, " + RADIUS + " TEXT, " + LOCATION_NAME +" TEXT, "+
                NOTIFIED +" TEXT);");
    }


    public long addData(ContentValues cv){
        db = this.getWritableDatabase();
        return  db.insert(TABLE, TITLE, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w(TABLE, "Upgrading database, which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    public void insert(ContentValues cv) {
        this.getWritableDatabase().insert(TABLE, TITLE, cv);
    }

    public void deleteData(long dataItem){
        this.getWritableDatabase().delete(TABLE, "_id = " + dataItem, null);
    }

    public static DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Cursor loadReminders(){
        cursor = (SQLiteCursor) this.getReadableDatabase().rawQuery("SELECT " + DatabaseHelper.ID + ", "
                + DatabaseHelper.TITLE + ", " + DatabaseHelper.MESSAGE + ", "
                + DatabaseHelper.DATE + ", " + DatabaseHelper.TIME + ", "
                + DatabaseHelper.XCOORDS + ", " + DatabaseHelper.YCOORDS + ", " + DatabaseHelper.RADIUS +
                ", " + DatabaseHelper.LOCATION_NAME +"," +NOTIFIED +
                " FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.DATE, null);
        return cursor;
    }
    public void updateNotifiedItem(int dbId) {
        this.getWritableDatabase().execSQL("UPDATE " + TABLE +" SET " + NOTIFIED + "='Y' WHERE _id =" + dbId);

    }
    public ArrayList<ReminderBean> loadAllReminders() {
        ArrayList<ReminderBean> reminderBeans = new ArrayList<ReminderBean>();

        Cursor myCursor = loadReminders();
        myCursor.moveToFirst();
        ReminderBean reminderBean ;
        while(myCursor.isAfterLast() == false){
            reminderBean = new ReminderBean();

            reminderBean.setDbId(cursor.getString(cursor.getColumnIndex(ID)));
            reminderBean.setTodo(cursor.getString(cursor.getColumnIndex(TITLE)));
            reminderBean.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION_NAME)));
            reminderBean.setDateTime(cursor.getString(cursor.getColumnIndex(DATE)));
            reminderBean.setNotified(cursor.getString(cursor.getColumnIndex(NOTIFIED)));
            reminderBeans.add(reminderBean);
            cursor.moveToNext();
        }
    return reminderBeans;
    }
    public Cursor loadReminderDetails(long id){
        cursor = this.getWritableDatabase().query(DatabaseHelper.TABLE, new String[]{DatabaseHelper.ID,
                        DatabaseHelper.TITLE, DatabaseHelper.MESSAGE, DatabaseHelper.DATE,
                        DatabaseHelper.TIME, DatabaseHelper.XCOORDS, DatabaseHelper.YCOORDS,
                        DatabaseHelper.RADIUS,DatabaseHelper.LOCATION_NAME}, DatabaseHelper.ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
       return cursor;
    }
    public List<String> loadTitlesForNotification(String ids[]) {
        List<String> lstIds = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        buffer.append("Select " + TITLE + " from " + TABLE + " where _id  in (");
        for (String str : ids) {
            buffer.append("'").append(str).append("'").append(",");
        }

        buffer.append("'").append("null").append("')");
        System.out.println(buffer.toString());

        Cursor cursor = this.getReadableDatabase().rawQuery(buffer.toString(), null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            lstIds.add(cursor.getString(cursor.getColumnIndex(TITLE)));
            cursor.moveToNext();
        }
        return lstIds;
    }


}
