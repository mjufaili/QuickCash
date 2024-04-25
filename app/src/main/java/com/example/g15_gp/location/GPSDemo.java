/*
Reference:
 Masud Rahman:
 Cookbook:
 https://git.cs.dal.ca/masud/csci3130-cookbook

 */
/**
 * This class gets a users dynamic location and the distance between his current location and
 * closest job near him.
 */
package com.example.g15_gp.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.g15_gp.R;

public class GPSDemo extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s_demo);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button locationButton = (Button) findViewById(R.id.getLocation);
        locationButton.setOnClickListener(view -> {
            if (hasLocationAccessPermission()) {
                GPSTracker gps = new GPSTracker(GPSDemo.this);
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getBaseContext(), "Current location:(" + latitude + "," + longitude + ")",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button calculateDistButton = (Button) findViewById(R.id.calculateDist);
        calculateDistButton.setOnClickListener(view -> {
            double distance = calculateDistance();
            Toast.makeText(getBaseContext(), "Distance: " + distance + " KM", Toast.LENGTH_LONG).show();
        });
    }
    // refactored Method
    protected double[] getLongLat(String locationValue) {
        String[] parts = locationValue.split(",");
        double longitude = Double.parseDouble(parts[0]);
        double latitude = Double.parseDouble(parts[1]);
        return new double[]{longitude, latitude};
    }

    protected Location getSourceLocation() {
        EditText sourceLoc = findViewById(R.id.sourceLoc);
        String source = sourceLoc.getText().toString();
        double[] coord = getLongLat(source);
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLongitude(coord[0]);
        location.setLatitude(coord[1]);
        return location;
    }
    // refactored Method
    protected Location getDestLocation() {
        EditText destLoc = findViewById(R.id.destLoc);
        String destination = destLoc.getText().toString();
        double[] coord = getLongLat(destination);
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLongitude(coord[0]);
        location.setLatitude(coord[1]);
        return location;
    }

    protected double calculateDistance() {
        Location sourceLocation = getSourceLocation();
        Location destLocation = getDestLocation();
        return sourceLocation.distanceTo(destLocation) / 1000;
    }

    protected boolean hasLocationAccessPermission() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            return true;
        } catch (Exception exc) {
            return false;
        }
    }
}