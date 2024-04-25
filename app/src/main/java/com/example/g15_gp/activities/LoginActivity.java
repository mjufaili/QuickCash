package com.example.g15_gp.activities;
/**
 * This class is a log in page where a registered user in the application can log in into the app whenever
 * he desires, using the correct credentials.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.g15_gp.emplo.EmployeeLandingPage;
import com.example.g15_gp.emplo.EmployerLandingPage;
import com.example.g15_gp.R;
import com.example.g15_gp.user.RegisterUser;
import com.example.g15_gp.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class
LoginActivity extends Activity implements View.OnClickListener {

    // for storing values
    public static final String SHARED_PREFERENCES = "SharedPreferences";
    Button loginButton;
    EditText usernameBox;
    EditText passwordBox;
    private boolean employerCheck = false;

    TextView register;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);

        // access the intent from register page
        Intent intent = getIntent();

        employerCheck = intent.getBooleanExtra("Employer", false);
        loginButton = findViewById(R.id.loginButton);
        usernameBox = findViewById(R.id.username);
        passwordBox = findViewById(R.id.password);
        loginButton.setOnClickListener(this);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        checkbox();
        loginButton.setOnClickListener(this);
    }

    // checks whether the users preference is set as true.

    /**
     *This method checks whether a user is logged in already and has not logged out.
     * It also makes sure which type of user has logged in, an employer or employee and takes him
     * to the correct page.
     *
     */
    private void checkbox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String checker = sharedPreferences.getString("logged","");
        String employerChecker = sharedPreferences.getString("employer", "");
        if (checker.equals("true")){
            Toast.makeText(LoginActivity.this, "Login Successfully",Toast.LENGTH_LONG).show();
            if (employerChecker.equals("true")){
                goToEmployerLandingPage();
            }
            else {
                goToEmployeeLandingPage();
            }
        }
    }
    /**
     *This methods saves a users log in state so that the user is allowed to stay logged in.
     */
    private void saveLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("logged", "true");
        editor.apply();
    }
    /**
     *This methods is used to save if a user is an employer.
     */
    private void SaveEmployerState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("employer", "true");
        editor.apply();
    }
    /**
     *This methods is used to save if a user is an employee.
     */
    private void SaveEmployeeState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("employee", "true");
        editor.apply();
    }

    //a method for moving from this activity to the landing activity.
    // main activity should be the landing page![](C:/Users/shuting xie/AppData/Local/Temp/download.jpg) as we logged in
    /**
     *This methods takes the user to the employer landing page
     */
    public void goToEmployerLandingPage() {
        Intent intent = new Intent(LoginActivity.this, EmployerLandingPage.class);
        startActivity(intent);
        finish();
    }
    /**
     *This methods takes the user to the employee landing page
     */
    public void goToEmployeeLandingPage() {
        Intent intent = new Intent(LoginActivity.this, EmployeeLandingPage.class);
        startActivity(intent);
        finish();
    }

    /**
     * This method checks if a username is empty or not
     * @param username the username inputted
     * @return returns a boolean value stating whether the username is empty or not. True for empty and
     * False for not empty
     *
     */
    private boolean emptyUsername(String username) {
        if (username.isEmpty()) {
            usernameBox.setError("Please enter Username");
            usernameBox.requestFocus();
            return true;
        }
        return false;
    }

    /**
     * Checks whether a password is empty or not
     * @param password is the inputted password by a user
     * @return a boolean stating whether the password is empty or not.
     */
    private boolean emptyPassword(String password) {
        if (password.isEmpty()) {
            passwordBox.setError("Please enter Username");
            passwordBox.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        gotoRegisterPage(view);

        if (emptyUsername(username)) return;

        if(emptyPassword(password)) return;

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            String message;
            if (task.isSuccessful()) {
                message = "Login Successfully!";
                String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                saveLoginState();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");
                Query query = databaseRef.orderByKey().equalTo(userID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            User user;
                            for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                                user = currentSnapshot.getValue(User.class);
                                if (user != null) {
                                    String userType1 = user.userType;
                                    if (userType1.equals("employer")) {
                                        SaveEmployerState();
                                        goToEmployerLandingPage();
                                    }
                                    else {
                                        SaveEmployeeState();
                                        goToEmployeeLandingPage();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LoginActivity", error.getMessage());
                    }
                });
            }
            else {
                message = "Wrong Credentials, Please try again";
            }
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        });
    }

    /**
     * This method takes a user to the register page
     * @param view is the view we want to move to.
     */
    private void gotoRegisterPage(View view) {
        if (view == register) {
            Intent intentRegister = new Intent(this, RegisterUser.class);
            startActivity(intentRegister);
        }
    }
}
