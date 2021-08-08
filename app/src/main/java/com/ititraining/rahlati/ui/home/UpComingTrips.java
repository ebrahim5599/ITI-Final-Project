package com.ititraining.rahlati.ui.home;

public class UpComingTrips {

    private String tripName, startPoint, endPoint;

    public UpComingTrips() {
    }

    public UpComingTrips(String tripName, String startPoint, String endPoint) {
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
}
