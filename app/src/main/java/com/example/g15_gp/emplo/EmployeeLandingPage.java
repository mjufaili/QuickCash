/* References:
1. "How to use ScrollView in Android?" stackoverflow.com.
        https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android (accessed Oct. 15, 2022)
2. "ArrayAdapter in Android with Example". geeksforgeeks.org.
        https://www.geeksforgeeks.org/arrayadapter-in-android-with-example/ (accessed Dec. 1, 2022)

3. "How to make items clickable in list view?". stackoverflow.com.
        https://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view (accessed Dec. 2, 2022)
 */
package com.example.g15_gp.emplo;
/**
 * This class is the landing page where an employer is taken to. The page contains all features
 * for an employer to use while working,
 */

import android.annotation.SuppressLint;
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
import com.example.g15_gp.job.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeLandingPage extends Activity implements View.OnClickListener{

    @SuppressLint("MissingInflatedId")
    public static final String SHARED_PREFERENCES = "SharedPreferences";
    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.employee_landing_page_new);
        Button logoutButton;
        logoutButton = findViewById(R.id.employee_logout_button);
        logoutButton.setOnClickListener(this);
        Button search = findViewById(R.id.employee_search_button);
        search.setOnClickListener(this);
        accessDatabase();
    }

    public void goToLoginPage() {
        Intent intent = new Intent(EmployeeLandingPage.this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToCategoryPage() {
        Intent intent = new Intent(EmployeeLandingPage.this, CategoryActivity.class);
        startActivity(intent);
    }

    private void removeLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("logged", "");
        editor.apply();
    }
    private void removeEmployeeState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("employee", "");
        editor.apply();
    }

    public void accessDatabase() {
        ListView jobList = findViewById(R.id.employee_job_list);

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
                            System.out.print(jobInfo);
                            jobsArray.add(jobInfo);
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EmployeeLandingPage.this, R.layout.employee_landing_page_new_item_view, R.id.employeeItemTextView, jobsArray);
                jobList.setAdapter(arrayAdapter);
                jobList.setOnItemClickListener((arg0, view, position, id) -> Toast.makeText(EmployeeLandingPage.this, "You have been applied", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployeeLandingPage", error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view){

        int id = view.getId();
        if (id == R.id.employee_logout_button) {
            Toast.makeText(getApplicationContext(), "You logged out", Toast.LENGTH_LONG).show();
            removeLoginState();
            removeEmployeeState();
            goToLoginPage();
        } else if (id == R.id.employee_search_button) {
            goToCategoryPage();
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