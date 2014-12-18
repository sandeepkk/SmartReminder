package com.skk.smartreminder.utils;

/**
 * Created by sandeepkannan on 12/16/14.
 */
public class ReminderBean {
    String dbId;

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    String todo;
    String location;
    String dateTime;
    String notified;

    public String getNotified() {
        return notified;
    }

    public void setNotified(String notified) {
        this.notified = notified;
    }
}
