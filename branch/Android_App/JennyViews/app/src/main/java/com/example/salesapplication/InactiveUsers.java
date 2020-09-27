package com.example.salesapplication;

import android.os.Bundle;

import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.users.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class InactiveUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive_users);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new OptionButtonController(this, -2));

        GridLayout grid = findViewById(R.id.grid);
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);

        try {
            List<Integer> customerIds = select.getUsersByRole(findCustomerId());

            int count = 0;
            for (int id: customerIds) {
                List<Integer> accounts = select.getUserInactiveAccounts(id);
                User user = select.getUserDetails(id);
                for (int i: accounts) {
                    CoordinatorLayout coordinator = new CoordinatorLayout(this);
                    CoordinatorLayout.LayoutParams cordParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    cordParams.gravity = Gravity.CENTER_HORIZONTAL;
                    cordParams.topMargin = 30;
                    cordParams.bottomMargin = 30;
                    cordParams.leftMargin = 15;
                    cordParams.rightMargin = 15;
                    coordinator.setBackground(getDrawable(R.drawable.bubble));
                    coordinator.setLayoutParams(cordParams);


                    LinearLayout bundle = new LinearLayout(this);
                    CoordinatorLayout.LayoutParams bundleParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    bundleParams.gravity = Gravity.CENTER_VERTICAL;
                    bundleParams.topMargin = 20;
                    bundleParams.bottomMargin = 20;
                    bundle.setOrientation(LinearLayout.VERTICAL);
                    bundle.setLayoutParams(bundleParams);

                    TextView userId = new TextView(this);
                    CoordinatorLayout.LayoutParams userParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    userParam.gravity = Gravity.START;
                    userParam.leftMargin = 100;
                    userParam.topMargin = 10;
                    userParam.bottomMargin = 10;
                    userId.setLayoutParams(userParam);

                    TextView accountId = new TextView(this);
                    accountId.setLayoutParams(userParam);
                    accountId.setText(R.string.getAccountId + " " + i);
                    accountId.setTextColor(getColor(R.color.Grey));
                    accountId.setTextSize(18);

                    userId.setText(R.string.getUserId + " " + user.getId());
                    userId.setTextColor(getColor(R.color.Grey));
                    userId.setTextSize(18);

                    TextView userName = new TextView(this);
                    userName.setLayoutParams(userParam);

                    userName.setText(R.string.getName + " " + user.getName());
                    userName.setTextColor(getColor(R.color.Grey));
                    userName.setTextSize(18);

                    TextView userAge = new TextView(this);
                    userAge.setLayoutParams(userParam);

                    userAge.setText(R.string.getAge + " " + user.getAge());
                    userAge.setTextColor(getColor(R.color.Grey));
                    userAge.setTextSize(18);

                    TextView userAddress = new TextView(this);
                    userAddress.setLayoutParams(userParam);

                    userAddress.setText(R.string.getAddress + " " + user.getAddress());
                    userAddress.setTextColor(getColor(R.color.Grey));
                    userAddress.setTextSize(18);

                    bundle.addView(userId);
                    bundle.addView(accountId);
                    bundle.addView(userName);
                    bundle.addView(userAge);
                    bundle.addView(userAddress);

                    coordinator.addView(bundle);
                    grid.addView(coordinator);
                    count++;
                }
            }

            if (count == 0) {
                TextView message = new TextView(this);
                CoordinatorLayout.LayoutParams textParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                textParam.topMargin = 20;
                textParam.leftMargin = 50;
                message.setLayoutParams(textParam);

                message.setText(getString(R.string.noInactive));
                message.setTextColor(getColor(R.color.Grey));
                message.setTextSize(18);
                grid.addView(message);
            }


        } catch(Exception e) {

        }
    }

    private int findCustomerId() {
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);
        try {
            for (Integer i : select.getRoleIds()) {
                if (select.getRoleName(i).equals("CUSTOMER")) {
                    return i;
                }
            }
        } catch (Exception e) {

        }
        return -1;
    }

}
