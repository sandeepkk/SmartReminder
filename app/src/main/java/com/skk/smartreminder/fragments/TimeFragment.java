package com.skk.smartreminder.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.skk.smartreminder.utils.DatabaseHelper;
import com.skk.smartreminder.R;

import java.util.Calendar;

/**
 * Created by Archie on 12/8/2014.
 */
public class TimeFragment extends Fragment{
    static TimePicker timePicked;
    static DatePicker datePicked;
    static int hoursDB, minDB, yearDB, monthDb, dayDB;
    static String morningEvening;
    View fragTransports;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragTransports = inflater.inflate(R.layout.time_fragment, container, false);

        timePicked = (TimePicker) fragTransports.findViewById(R.id.timeRemeber);
        timePicked.setIs24HourView(false);
        timePicked.setOnTimeChangedListener(OnTimeChanged);

        Calendar today = Calendar.getInstance();
        datePicked = (DatePicker) fragTransports.findViewById(R.id.dateRemember);
        datePicked.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH), onDateChanged);

        yearDB = datePicked.getYear();
        monthDb = datePicked.getMonth() + 1;
        dayDB = datePicked.getDayOfMonth();
        onTimeSet(timePicked, timePicked.getCurrentHour(), timePicked.getCurrentMinute());

        return fragTransports;
    }

    public static void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            morningEvening = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            morningEvening = "PM";

        hoursDB = hourOfDay - 12;
        minDB = minute;


    }

    TimePicker.OnTimeChangedListener OnTimeChanged =
            new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(
                        TimePicker view,
                        int hourOfDay,
                        int minute) {
                    hoursDB = hourOfDay;
                    minDB = minute;
                }
            };

    DatePicker.OnDateChangedListener onDateChanged=
            new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(
                        DatePicker view,
                        int year,
                        int monthOfYear,
                        int dayOfMonth) {
                    yearDB = year;
                    monthDb = monthOfYear + 1;
                    dayDB = dayOfMonth;
                }
            };



    @Override
    public void onPause() {
        super.onPause();

        //saveData();
        //saveState.putInt("timeHour", hoursDB);
        //saveState.putInt("timeMin", minDB);
    }

    public void saveData()
    {
        ContentValues temp = new ContentValues();
        temp.put(DatabaseHelper.TIME, hoursDB + ":" + minDB);

        
    }

    public static String passTime()
    {
        String min, hour;

        if(minDB<10)
        {
            min = "0" + minDB;
        }
        else
        {
            min = Integer.toString(minDB);
        }

        if(hoursDB<10)
        {
            hour = "0" + hoursDB;
        }
        else if(hoursDB>12)
        {
            hour = Integer.toString(hoursDB - 12);
        }
        else
        {
            hour = Integer.toString(hoursDB);
        }

        return hour + ":" + min + " " + morningEvening;
    }

    public static String passDate()
    {
        return monthDb + "/" + dayDB + "/" + yearDB;
    }

    public static void setTime(int hour, int min)
    {
        timePicked.setCurrentMinute(min);
        timePicked.setCurrentHour(hour);

    }

    public static long timeAlarmMillis()
    {
        Calendar moment = Calendar.getInstance();
        moment.set(yearDB, monthDb, dayDB, hoursDB, minDB);

        if(System.currentTimeMillis() <= moment.getTimeInMillis()) {
            //machine milliseconds * Milliseconds * secon4ds * min * hour * day * month * year
            return (moment.getTimeInMillis());
        }
        else
            return System.currentTimeMillis();
    }
}
