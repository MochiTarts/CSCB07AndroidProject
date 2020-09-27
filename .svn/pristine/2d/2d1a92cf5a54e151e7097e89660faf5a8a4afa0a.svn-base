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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PromoteEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_employee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new OptionButtonController(this, -2));

        GridLayout grid = findViewById(R.id.grid);
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);

        try {
            List<Integer> i = select.getUsersByRole(findEmployeeId());
            for (int id: i) {
                User user = select.getUserDetails(id);

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

                userId.setText("ID: " + user.getId());
                userId.setTextColor(getColor(R.color.Grey));
                userId.setTextSize(18);

                TextView userName = new TextView(this);
                userName.setLayoutParams(userParam);

                userName.setText("Name: " + user.getName());
                userName.setTextColor(getColor(R.color.Grey));
                userName.setTextSize(18);

                TextView userAge = new TextView(this);
                userAge.setLayoutParams(userParam);

                userAge.setText("Age: " + user.getAge());
                userAge.setTextColor(getColor(R.color.Grey));
                userAge.setTextSize(18);

                TextView userAddress = new TextView(this);
                userAddress.setLayoutParams(userParam);

                userAddress.setText("Address: " + user.getAddress());
                userAddress.setTextColor(getColor(R.color.Grey));
                userAddress.setTextSize(18);

                Button add = new Button(this);
                CoordinatorLayout.LayoutParams buttonParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                buttonParam.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                buttonParam.rightMargin = 100;

                if (i.size() > 1) {
                    add.setOnClickListener(new PromoteButtonController(this, user));
                } else {
                    add.setOnClickListener(new VerifyPromoteButtonController(this, user));
                }

                add.setLayoutParams(buttonParam);
                add.setText(getString(R.string.promote));
                add.setBackground(getDrawable(R.drawable.button_bubble));
                add.setTextColor(getColor(R.color.white));
                add.setMinimumWidth(500);

                bundle.addView(userId);
                bundle.addView(userName);
                bundle.addView(userAge);
                bundle.addView(userAddress);

                coordinator.addView(bundle);
                coordinator.addView(add);
                grid.addView(coordinator);
            }

            if (i.size() == 0) {
                TextView message = new TextView(this);
                CoordinatorLayout.LayoutParams textParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                textParam.topMargin = 20;
                textParam.leftMargin = 50;
                message.setLayoutParams(textParam);

                message.setText(getString(R.string.noEmployees));
                message.setTextColor(getColor(R.color.Grey));
                message.setTextSize(18);
                grid.addView(message);
            }

        } catch (Exception e) {

        }
    }

    private int findEmployeeId() {
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);
        try {
            for (Integer i : select.getRoleIds()) {
                if (select.getRoleName(i).equals("EMPLOYEE")) {
                    return i;
                }
            }
        } catch (Exception e) {

        }
        return -1;
    }
}
