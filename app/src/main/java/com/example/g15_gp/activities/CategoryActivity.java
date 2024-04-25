package com.example.g15_gp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.g15_gp.R;

/**
 * This class categorises the different types of jobs available to the user.
 */
public class CategoryActivity extends Activity {

    ImageButton engineerButton;
    ImageButton accountantButton;
    ImageButton managerButton;
    ImageButton restaurantButton;
    ImageButton driverButton;

    Intent intent;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_category);

        engineerButton = findViewById(R.id.engineer_category_button);
        accountantButton = findViewById(R.id.accountant_category_button);
        managerButton = findViewById(R.id.manager_category_button);
        restaurantButton = findViewById(R.id.restaurant_category_button);
        driverButton = findViewById(R.id.driver_category_button);

        intent = new Intent(CategoryActivity.this, SearchActivityNew.class);

        engineerButton.setOnClickListener(getListenerByJobCategory("engineer"));
        accountantButton.setOnClickListener(getListenerByJobCategory("accountant"));
        managerButton.setOnClickListener(getListenerByJobCategory("manager"));
        restaurantButton.setOnClickListener(getListenerByJobCategory("restaurant"));
        driverButton.setOnClickListener(getListenerByJobCategory("driver"));
    }

    private View.OnClickListener getListenerByJobCategory(String category){
        return v -> {
            intent.putExtra("JobCategory", category);
            startActivity(intent);
        };
    }
}
