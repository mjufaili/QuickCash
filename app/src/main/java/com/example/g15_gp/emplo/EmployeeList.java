package com.example.g15_gp.emplo;
/**
 * Such a class contains a list of employees using the application
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.g15_gp.R;
import com.example.g15_gp.activities.PayPal;

import java.util.ArrayList;

public class EmployeeList extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.employee_list);

        ListView employeeListView = findViewById(R.id.employee_list_view);

        ArrayList<String> employees = new ArrayList<>();
        employees.add("Employee Name: Basim\nEmail: basim1512@gmail.com\nPhone: 9029024421");
        employees.add("Employee Name: Alice\nEmail: Alice@bob.com\nPhone: 9224335589");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EmployeeList.this, R.layout.employee_list_item_view, R.id.itemTextView, employees);
        employeeListView.setAdapter(arrayAdapter);
        employeeListView.setOnItemClickListener((arg0, view, position, id) -> {
                    Intent intent = new Intent(EmployeeList.this, PayPal.class);
                    startActivity(intent);
        });
    }
}
