/* References:
1. "RadioButton & RadioGroup Tutorial With Example In Android Studio". abhiandroid.com
        https://abhiandroid.com/ui/radiobutton (accesssed Oct. 15, 2022)
2. "I want to work with two button in Android Studio". stackoverflow.com.
        https://stackoverflow.com/questions/33804200/i-want-to-work-with-two-button-in-android-studio (accessed Oct. 15, 2022)
3.   https://www.youtube.com/watch?v=Z-RE1QuUWPg (accessed Oct. 15, 2022)
4.  "Radio Buttons" developer.android.com.
        https://developer.android.com/develop/ui/views/components/radiobutton (accessed Oct. 14, 2022)
 */
/**
 * This class Registers a user in the application, allowing him to create an account and access
 * the applications features
 */
package com.example.g15_gp.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.g15_gp.R;
import com.example.g15_gp.activities.LoginActivity;
import com.example.g15_gp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText editUsername;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPhone;
    private EditText editLocation;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    Button registerButton;

    RadioButton employerButton;
    RadioButton employeeButton;

    private String userType = "employee";

    TextView loginPage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        TextView banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        registerButton = (Button) findViewById(R.id.registerUser);
        registerButton.setOnClickListener(this);
        employerButton = (RadioButton) findViewById(R.id.employerRadio);
        employeeButton = (RadioButton) findViewById(R.id.employeeRadio);
        loginPage = (TextView) findViewById(R.id.loginPage);
        loginPage.setOnClickListener(this);

        editUsername = (EditText) findViewById(R.id.name);
        editEmail = (EditText)  findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        editPhone = (EditText) findViewById(R.id.phone);
        editLocation = (EditText) findViewById(R.id.location);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.banner) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.registerUser) {
            registerUser();
        }
    }

    public void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        // Check which radio button was clicked
        int id = view.getId();
        if (id == R.id.employeeRadio) {
            if (employeeButton.isChecked())
                // Pirates are the best
                userType = "employee";
        } else if (id == R.id.employerRadio && employerButton.isChecked()) {
                userType = "employer";
        }
    }

    private void registerUser() {

        String username = editUsername.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String location = editLocation.getText().toString().trim();

        //check if the username is empty
        if (emptyUsername(username)) return;

        //checks if the email is blank
        if (emptyEmail(email)) return;

        //checks if the email is valid
        if (validEmail(email)) return;

        //checks if the password is valid
        if (emptyPassword(password)) return;

        //checks if 8 characters are entered
        if (validPassword(password)) return;

        //checks if the phone is blank
        if (emptyPhone(phone)) return;

        if (validPhone(phone)) return;

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        User user = new User(username, email, phone, location, userType);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "You have been Registered!", Toast.LENGTH_LONG).show();
                                        //redirect to login page
                                    }
                                    else{
                                        Toast.makeText(RegisterUser.this, "Registration successful", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    goToLoginPage();
                                });
                    }
                    else{
                        Toast.makeText(RegisterUser.this, "Registration failed, Please try again!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    // refactored Method
    private boolean validPhone(String phone) {
        if (phone.length() == 9) {
            editPhone.setError("Please Enter a valid Phone Number!");
            editPhone.requestFocus();
            return true;
        }
        return false;
    }

    private boolean emptyPhone(String phone) {
        if (phone.isEmpty()) {
            editPhone.setError("Please Enter Phone Number!");
            editPhone.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validPassword(String password) {
        if (password.length() < 8) {
            editPassword.setError("Please Enter 8 Characters or more!");
            editPassword.requestFocus();
            return true;
        }
        return false;
    }
    // refactored Method
    private boolean emptyPassword(String password) {
        if (password.isEmpty()) {
            editPassword.setError("Please Enter Password!");
            editPassword.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please Provide a valid Email!");
            editEmail.requestFocus();
            return true;
        }
        return false;
    }

    private boolean emptyEmail (String email) {
        if (email.isEmpty()) {
            editEmail.setError("Please Enter Email");
            editEmail.requestFocus();
            return true;
        }
        return false;
    }
    // refactored Method
    private boolean emptyUsername(String username) {
        if (username.isEmpty()) {
            editUsername.setError("Please enter Username");
            editUsername.requestFocus();
            return true;
        }
        return false;
    }
}