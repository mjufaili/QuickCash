package com.example.g15_gp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.g15_gp.R;
import com.example.g15_gp.emplo.EmployerLandingPage;
import com.example.g15_gp.job.Job;
import com.example.g15_gp.location.GPSTracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubmitActivity extends AppCompatActivity{
    public static final String SHARED_PREFERENCES = "SharedPreferences";
    Button submitButton;
    EditText employeeName;
    EditText location;
    EditText payRate;
    Spinner jobSpinner;
    String itemSelected;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Job jobDetails;

    TextView LocationHalifax;
    Button LocationButton;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    public void goToEmployerLandingPage() {
        Intent intent = new Intent(SubmitActivity.this, EmployerLandingPage.class);
        startActivity(intent);
        finish();
    }

    private void saveJobPreference(String location, String payRate, String jobType) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(location, "job");
        editor.putString(payRate, "payRate");
        editor.putString(jobType, "jobType");
        editor.apply();
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        NotificationChannel channel = new NotificationChannel("Job Alert","My notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.submit_activity);
        submitButton = findViewById(R.id.submitButton);
        employeeName = findViewById(R.id.employerName);
        payRate = findViewById(R.id.payRate);

        LocationHalifax = findViewById(R.id.location1);
        LocationButton = findViewById(R.id.getLocation1);

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (hasLocationAccessPermission()) {
                    GPSTracker gps = new GPSTracker(SubmitActivity.this);
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        Toast.makeText(getBaseContext(), "Current location:(" + latitude + "," + longitude + ")",
                                Toast.LENGTH_LONG).show();
                    }
                }
                LocationHalifax.setText("Halifax");
            }
        });

        jobSpinner = findViewById(R.id.jobType);
        jobSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /* TODO document why this method is empty
                 * needs to be written to work
                 *  */

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jobTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        jobSpinner.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /* TODO document why this method is empty
                * needs to be written to work
                *  */
            }
        });

        submitButton.setOnClickListener(v -> {
            String name = getName();
            String location = getLocation();
            String payRate = getRate();
            String jobType = getJobType();
            String errorMessage = "";

            if(!(validName(name))){
                errorMessage="Invalid Name Used.";
            }

            if(!(validPayRate(payRate))){
                errorMessage = "Invalid Pay rate Used.";
            }

            if(!(validJobType(jobType))){
                errorMessage = "Please choose a Job.";
            }
            setStatusMessage(errorMessage);
            if(validName(name) && validLocation(location) && validPayRate(payRate) && validJobType(jobType)){
                saveJobPreference(location, payRate, jobType);
                jobDetails = new Job(name,location,payRate,jobType);

                myRef.child("Jobs").push().setValue(jobDetails);

                Context context = getApplicationContext();
                CharSequence text = "Job Submitted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(SubmitActivity.this,"Job Alert");
                builder.setContentTitle("New Job Alert!");
                builder.setContentText("A new job has been submitted! Would you like to check it out before someone takes it?");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SubmitActivity.this);
                managerCompat.notify(1, builder.build());
                goToEmployerLandingPage();
            }
            else{
                Context context = getApplicationContext();
                CharSequence text = "Job Not Submitted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    protected String getName(){
        return employeeName.getText().toString().trim();
    }

    protected String getLocation(){
        return LocationHalifax.getText().toString().trim();
    }
    protected String getRate(){
        return payRate.getText().toString().trim();
    }
    public String getJobType(){
        return itemSelected;
    }

    protected boolean validName(String employeeName){
        char[] array = employeeName.toCharArray();
        for(char c: array){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    protected boolean validLocation(String location){
        return location.equalsIgnoreCase("Halifax");
    }

    protected boolean validPayRate(String payRate){
        if(payRate.isEmpty()||payRate.equals("0")){
            return false;
        }
        return !payRate.matches(".*[a-z].*");
    }

    protected boolean validJobType(String job){
        return !job.equals(jobSpinner.getItemAtPosition(0).toString().trim());
    }

    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.errorLabel);
        statusLabel.setText(message.trim());
    }

    //getting location permission
    protected boolean hasLocationAccessPermission() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            return true;
        } catch (Exception exc) {
            return false;
        }
    }
}