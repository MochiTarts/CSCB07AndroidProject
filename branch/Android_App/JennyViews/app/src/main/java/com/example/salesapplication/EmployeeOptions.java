package com.example.salesapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.PrecomputedText;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmployeeOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(this));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(this));*/

        GridLayout grid = findViewById(R.id.grid);

        LinearLayout linear = new LinearLayout(this);
        CoordinatorLayout.LayoutParams linearParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        linearParam.gravity = Gravity.CENTER_HORIZONTAL;
        linearParam.topMargin = 15;
        linearParam.bottomMargin = 15;
        linearParam.leftMargin = 15;
        linearParam.rightMargin = 15;
        linear.setLayoutParams(linearParam);
        linear.setOrientation(LinearLayout.VERTICAL);


        Button stock = new Button(this);
        Button newEmployee = new Button(this);
        Button newCustomer = new Button(this);
        Button newAccount = new Button(this);

        CoordinatorLayout.LayoutParams buttonParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);

        buttonParam.topMargin = 30;
        buttonParam.bottomMargin = 30;

        stock.setBackground(getDrawable(R.drawable.button_bubble));
        newEmployee.setBackground(getDrawable(R.drawable.button_bubble));
        newCustomer.setBackground(getDrawable(R.drawable.button_bubble));
        newAccount.setBackground(getDrawable(R.drawable.button_bubble));

        stock.setText(getString(R.string.restock));
        newEmployee.setText(getString(R.string.newEmployee));
        newCustomer.setText(getString(R.string.newCustomer));
        newAccount.setText(getString(R.string.newAccount));

        stock.setTextColor(getColor(R.color.white));
        newEmployee.setTextColor(getColor(R.color.white));
        newCustomer.setTextColor(getColor(R.color.white));
        newAccount.setTextColor(getColor(R.color.white));

        stock.setTextSize(20);
        newEmployee.setTextSize(20);
        newCustomer.setTextSize(20);
        newAccount.setTextSize(20);

        stock.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        stock.setPaddingRelative(75, 0, 0, 0);

        newEmployee.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        newEmployee.setPaddingRelative(75, 0, 0, 0);

        newCustomer.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        newCustomer.setPaddingRelative(75, 0, 0, 0);

        newAccount.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        newAccount.setPaddingRelative(75, 0, 0, 0);


        stock.setLayoutParams(buttonParam);
        newEmployee.setLayoutParams(buttonParam);
        newCustomer.setLayoutParams(buttonParam);
        newAccount.setLayoutParams(buttonParam);

        stock.setOnClickListener(new OptionButtonController(this, 0));
        newEmployee.setOnClickListener(new OptionButtonController(this, 1));
        newCustomer.setOnClickListener(new OptionButtonController(this, 2));
        newAccount.setOnClickListener(new OptionButtonController(this, 3));

        linear.addView(stock);
        linear.addView(newEmployee);
        linear.addView(newCustomer);
        linear.addView(newAccount);

        grid.addView(linear);

        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(this));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(this));

    }

}