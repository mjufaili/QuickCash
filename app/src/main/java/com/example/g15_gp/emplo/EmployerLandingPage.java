/* References:
1. "How to use ScrollView in Android?" stackoverflow.com.
        https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android (accessed Oct. 15, 2022)
 */
package com.example.g15_gp.emplo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.g15_gp.R;
import com.example.g15_gp.activities.CategoryActivity;
import com.example.g15_gp.activities.LoginActivity;
import com.example.g15_gp.activities.SubmitActivity;
import com.example.g15_gp.job.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployerLandingPage extends Activity implements View.OnClickListener{
    public static final String SHARED_PREFERENCES = "SharedPreferences";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.employer_landing_page_new);

        Button logoutButton = findViewById(R.id.employer_logout_button);
        logoutButton.setOnClickListener(this);

        Button submitButton = findViewById(R.id.submit_job_button);
        submitButton.setOnClickListener(this);

        Button search = findViewById(R.id.employer_search_button);
        search.setOnClickListener(this);

        accessDatabase();
    }

    public void goCategoryPage() {
        Intent intent = new Intent(EmployerLandingPage.this, CategoryActivity.class);
        startActivity(intent);
    }
    private void removeEmployerState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("employer", "");
        editor.apply();
    }

    public void goToSubmitJob(){
        Intent intent = new Intent(EmployerLandingPage.this, SubmitActivity.class);
        startActivity(intent);
    }

    public void goToLoginPage() {
        Intent intent = new Intent(EmployerLandingPage.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void removeLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("logged", "");
        editor.apply();
    }

    public void accessDatabase() {
        ListView jobList = findViewById(R.id.employer_job_list);

        // Create an ArrayList object to store jobs
        ArrayList<String> jobsArray = new ArrayList<>();

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
                            jobsArray.add(jobInfo);
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EmployerLandingPage.this, R.layout.employer_landing_page_new_item_view, R.id.employerItemTextView, jobsArray);
                jobList.setAdapter(arrayAdapter);
                jobList.setOnItemClickListener((arg0, view, position, id) -> {
                    Intent intent = new Intent(EmployerLandingPage.this, EmployeeList.class);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployerLandingPage", error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view){

        int id = view.getId();
        if (id == R.id.employer_logout_button) {
            goToLoginPage();
            Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_LONG).show();
            removeLoginState();
            removeEmployerState();
        }

        else if (id == R.id.submit_job_button){
            goToSubmitJob();
        }
        else if (id == R.id.employer_search_button) {
            goCategoryPage();
        }
    }

    public String getJobInfo(Job job) {
        String jobTitle = "Job Title: " + job.jobType + "\n";
        String employerName = "Employer Name: " + job.name + "\n";
        String location = "Location: " + job.location + "\n";
        String payRate = "Pay Rate: " +job.payRate + "\n";
        return  jobTitle + employerName + location + payRate;
    }
}