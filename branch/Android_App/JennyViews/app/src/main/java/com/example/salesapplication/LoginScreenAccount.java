package com.example.salesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreenAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInsertAdapter.setInserter(new DatabaseInsertHelperAndroid(getApplicationContext()));
        DatabaseSelectAdapter.setSelector(new DatabaseSelectHelperAndroid(getApplicationContext()));
        DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelperAndroid(getApplicationContext()));

        setContentView(R.layout.login_screen_account);

        int id = this.getIntent().getExtras().getInt("id");
        String password = this.getIntent().getExtras().getString("password");

        FloatingActionButton fab = findViewById(R.id.login_button);
        fab.setOnClickListener(new AccountLoginController(this, id, password));
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
