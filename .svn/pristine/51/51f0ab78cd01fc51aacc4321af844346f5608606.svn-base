package com.example.salesapplication;

import android.os.Bundle;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.exceptions.PasswordUnMatchException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class admin_initiation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInsertAdapter.setInserter(new DatabaseInsertHelperAndroid(getApplicationContext()));

        setContentView(R.layout.activity_admin_initiation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new AdminInitializeController(admin_initiation.this));
    }

    @Override
    public void onBackPressed() {

    }

}
