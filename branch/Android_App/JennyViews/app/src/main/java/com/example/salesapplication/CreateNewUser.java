package com.example.salesapplication;

import android.os.Bundle;

import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CreateNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new OptionButtonController(this, -1));

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

        EditText name = new EditText(this);
        EditText age = new EditText(this);
        EditText address = new EditText(this);
        EditText password1 = new EditText(this);
        EditText password2 = new EditText(this);
        Button but = new Button(this);

        CoordinatorLayout.LayoutParams editTextParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        editTextParam.gravity = Gravity.CENTER;
        editTextParam.topMargin = 30;
        editTextParam.bottomMargin = 30;

        name.setLayoutParams(editTextParam);
        age.setLayoutParams(editTextParam);
        address.setLayoutParams(editTextParam);
        password1.setLayoutParams(editTextParam);
        password2.setLayoutParams(editTextParam);
        but.setLayoutParams(editTextParam);

        name.setBackground(getDrawable(R.drawable.input_bubble));
        age.setBackground(getDrawable(R.drawable.input_bubble));
        address.setBackground(getDrawable(R.drawable.input_bubble));
        password1.setBackground(getDrawable(R.drawable.input_bubble));
        password2.setBackground(getDrawable(R.drawable.input_bubble));
        but.setBackground(getDrawable(R.drawable.button_bubble));

        name.setHint(getString(R.string.name));
        age.setHint(getString(R.string.age));
        address.setHint(getString(R.string.address));
        password1.setHint(getString(R.string.password));
        password2.setHint(getString(R.string.confirmPassword));
        but.setText(getString(R.string.add));

        name.setHintTextColor(getColor(R.color.Grey));
        age.setHintTextColor(getColor(R.color.Grey));
        address.setHintTextColor(getColor(R.color.Grey));
        password1.setHintTextColor(getColor(R.color.Grey));
        password2.setHintTextColor(getColor(R.color.Grey));
        but.setTextColor(getColor(R.color.white));

        name.setTextColor(getColor(R.color.Grey));
        age.setTextColor(getColor(R.color.Grey));
        address.setTextColor(getColor(R.color.Grey));
        password1.setTextColor(getColor(R.color.Grey));
        password2.setTextColor(getColor(R.color.Grey));


        name.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        age.setInputType(InputType.TYPE_CLASS_NUMBER);
        address.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        password1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        name.setTextSize(18);
        age.setTextSize(18);
        address.setTextSize(18);
        password1.setTextSize(18);
        password2.setTextSize(18);
        but.setTextSize(18);

        age.setTextColor(getColor(R.color.Grey));

        name.setGravity(Gravity.CENTER);
        age.setGravity(Gravity.CENTER);
        address.setGravity(Gravity.CENTER);
        password1.setGravity(Gravity.CENTER);
        password2.setGravity(Gravity.CENTER);

        but.setOnClickListener(new AddUserButtonController(this, findEmployeeId(), name, age, address, password1, password2));

        linear.addView(name);
        linear.addView(age);
        linear.addView(address);
        linear.addView(password1);
        linear.addView(password2);
        linear.addView(but);

        grid.addView(linear);
    }


    private int findEmployeeId() {
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);
        try {
            for (Integer i : select.getRoleIds()) {
                if (select.getRoleName(i.intValue()).equals("EMPLOYEE")) {
                    return i.intValue();
                }
            }
        } catch (Exception e) {

        }

        return -1;
    }
}

