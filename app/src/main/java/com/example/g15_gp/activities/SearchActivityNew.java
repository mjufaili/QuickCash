/* References:
1. Usmi Mukherjee. "Firebase CRUD Application". git.cs.dal.ca.
    https://git.cs.dal.ca/umukherjee/csci3130firebasecrud/-/commit/1c2c54f04f01284c841b81b3a4beb9e288d7f714
    (accessed Nov. 1, 2022)
2. "Retrieving Data". firebase.google.com.
    https://firebase.google.com/docs/database/admin/retrieve-data (accessed Nov. 2, 2022)
 */
package com.example.g15_gp.activities;
/**
 * This class allows an employee user to search for the jobs he desired and easily apply for the job he desires.
 */

import static java.lang.System.*;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.g15_gp.job.Job;
import com.example.g15_gp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityNew extends Activity implements View.OnClickListener{
    Button searchButton;
    EditText search;
    TextView preferences;
    public static final String SHARED_PREFERENCES = "SharedPreferences";
    // Create an ArrayList object to store jobs
    ArrayList<String> jobsArray = new ArrayList<>();
    ListView jobList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.search_activity_new);
        searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(this);
        search = findViewById(R.id.search);
        preferences = findViewById(R.id.storingPreferences);
        jobList = findViewById(R.id.search_job_list);

        Intent intent = getIntent();

        if (intent.getStringExtra("JobCategory") != null){
            accessDatabase();
        }
    }
    private void saveJobPreference(String preference) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preference, "job");
        editor.apply();
    }
    private void readDataFromSharedPreferences(String preference) {
        if (preference.isEmpty()){
            preferences.setText("No job is searched");
        }
        else{
            preferences.setText(preference);
        }
    }

    public void accessDatabase() {
        //initialize your database and store the retrieved values to the static members above.
        final DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("Jobs");
        jobsRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Job job;
                    String jobInfo = "";
                    for (DataSnapshot currentSnapShot : snapshot.getChildren()) {
                        job = currentSnapShot.getValue(Job.class);
                        if (job != null) {
                            jobInfo = getJobInfo(job);
                            out.print(jobInfo);
                            jobsArray.add(jobInfo);
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchActivityNew.this, R.layout.search_activity_new_item_view, R.id.searchItemTextView, jobsArray);
                jobList.setAdapter(arrayAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchActivityNew", error.getMessage());
            }
        });
    }

    // refactored Method
    public void search(List<String> searchResultArray,String searchString) {
        for (int i = 0; i < jobsArray.size(); i ++) {
            saveJobPreference(searchString);
            readDataFromSharedPreferences((searchString));
            if (jobsArray.get(i).toLowerCase().contains(searchString.toLowerCase())) {
                searchResultArray.add(jobsArray.get(i));
                out.print(jobsArray.get(i));
            }
        }
    }
    // refactored Method

    public String getJobInfo(Job job) {
        String jobTitle = "Job Title: " + job.jobType + "\n";
        String employerName = "Employer Name: " + job.name + "\n";
        String location = "Location: " + job.location + "\n";
        String payRate = "Pay Rate: " +job.payRate + "\n";
        return  jobTitle + employerName + location + payRate;
    }

    @Override
    public void onClick(View v) {
        String searchString = search.getText().toString();
        ArrayList<String> searchResultArray = new ArrayList<>();
        search(searchResultArray, searchString);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchActivityNew.this, R.layout.search_activity_new_item_view, R.id.searchItemTextView, searchResultArray);
        jobList.setAdapter(arrayAdapter);
    }
}