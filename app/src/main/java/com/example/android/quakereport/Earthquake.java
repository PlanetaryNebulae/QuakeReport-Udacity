package com.example.android.quakereport;

public class Earthquake {

    //Magnitude of the earthquake.
    private double eMagnitude;

    //Location of the earthquake;
    private String eLocation;

    //Date of the earthquake.
    private Long eDate;

    private String eUrl;

    public Earthquake(double mMagnitude, String mLocation, long mDate, String mUrl) {
        eMagnitude = mMagnitude;
        eLocation = mLocation;
        eDate = mDate;
        eUrl = mUrl;
    }

    //Gets the magnitude of the earthquake.
    public double getMagnitude() {
        return eMagnitude;
    }

    //Gets the location of the earthquake.
    public String getLocation() {
        return eLocation;
    }

    //Gets the date of the earthquake.
    public long getDate() {
        return eDate;
    }

    public String getUrl() {
        return eUrl;
    }
}
