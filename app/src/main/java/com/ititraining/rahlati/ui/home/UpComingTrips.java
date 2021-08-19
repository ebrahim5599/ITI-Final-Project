package com.ititraining.rahlati.ui.home;

public class UpComingTrips {

    private String id, date, time, tripName, startPoint, endPoint, status, note;
    private int alarmId;
    public UpComingTrips() {
    }

    public UpComingTrips(String id, String date, String time, String tripName, String startPoint, String endPoint) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public UpComingTrips(String id, String date, String time, String tripName, String startPoint, String endPoint, String note) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.status = status;
        this.note = note;
    }

    public UpComingTrips(String id, String date, String time, String tripName, String startPoint, String endPoint, String note, int alarmId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.note = note;
        this.alarmId = alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public int getAlarmId() {
        return alarmId;
    }
}

