package com.example.salesapplication;

import android.os.Bundle;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class employee_initilization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInsertAdapter.setInserter(new DatabaseInsertHelperAndroid(getApplicationContext()));
        DatabaseSelectAdapter.setSelector(new DatabaseSelectHelperAndroid(getApplicationContext()));
        DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelperAndroid(getApplicationContext()));

        setContentView(R.layout.activity_employee_initilization);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new EmployeeInitializeController(employee_initilization.this));
    }

    @Override
    public void onBackPressed() {

    }

}
