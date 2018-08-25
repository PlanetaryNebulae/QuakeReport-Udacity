package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake, parent, false);
        }

        //Gets the Earthquake object from its position.
        Earthquake currentEarthquake = getItem(position);

        //Finds the TextView for magnitude.
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

        //Sets the TextView to the correct magnitude for the objects position.
        magnitudeView.setText(formattedMagnitude);

        //Sets the correct color to magnitude circle.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        //Gets the correct background color for each magnitude.
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        //Sets the color for magnitude circle.
        magnitudeCircle.setColor(magnitudeColor);

        //Finds the original location.
        String originalLocation = currentEarthquake.getLocation();

        //A split string for the primary location.
        String primaryLocation;
        //A split string for the location offset.
        String locationOffset;

        //An if/else statement to divide location, or add "near the".
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        //finds the TextView with the primary location id.
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        //Displays the primary location.
        primaryLocationView.setText(primaryLocation);

        //Finds the TextView with the location offset id.
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        //Displays the location offset.
        locationOffsetView.setText(locationOffset);

        //A date object to format the date and time of the earthquake.
        Date dateObject = new Date(currentEarthquake.getDate());

        //finds the TextView with the date id.
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        //Formats the date of the string.
        String formattedDate = formatDate(dateObject);
        //Displays the date of the earthquake.
        dateView.setText(formattedDate);

        //Finds the TextView with the time id.
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        //Formats the time of the earthquake.
        String formattedTime = formatTime(dateObject);
        //Displays the time of the earthquake.
        timeView.setText(formattedTime);

        return listItemView;
    }

    //Returns the formatted date string from the date object.
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    //Returns the formatted time string from the date object.
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    //Returns the formatted magnitude string.
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
