package com.ititraining.rahlati.ui.home;

public class UpComingTrips {

    private String date, time, tripName, startPoint, endPoint;

    public UpComingTrips() {
    }

    public UpComingTrips(String tripName, String startPoint, String endPoint) {
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public UpComingTrips(String date, String time, String tripName, String startPoint, String endPoint) {
        this.date = date;
        this.time = time;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
}
