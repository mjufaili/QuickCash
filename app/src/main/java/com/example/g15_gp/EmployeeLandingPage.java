/* References:
1. "How to use ScrollView in Android?" stackoverflow.com.
        https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android (accessed Oct. 15, 2022)
 */
package com.example.g15_gp;
/**
 * This class represents the page where an employee user lands after logging in.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.g15_gp.activities.LoginActivity;

public class EmployeeLandingPage extends Activity implements View.OnClickListener{
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.employee_landing_page);
        Button logoutButton;
        logoutButton = findViewById(R.id.employee_logout_button);
        logoutButton.setOnClickListener(this);

        Intent intent = getIntent();
    }

    public void GoToLoginPage() {
        Intent intent = new Intent(EmployeeLandingPage.this, LoginActivity.class);
        //intent.putExtra("You logged out", LOGOUT_MESSAGE);
        startActivity(intent);
    }
    @Override
    public void onClick(View view){
        Toast.makeText(getApplicationContext(), "You logged out", Toast.LENGTH_LONG).show();
        GoToLoginPage();
    }
}
