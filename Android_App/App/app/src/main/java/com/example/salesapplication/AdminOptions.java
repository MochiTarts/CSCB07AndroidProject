package com.example.salesapplication;

import android.os.Bundle;

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
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdminOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(this));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(this));


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


        Button promote = new Button(this);
        Button log = new Button(this);
        Button serialize = new Button(this);
        Button active = new Button(this);
        Button inactive = new Button(this);
        Button deserialize = new Button(this);

        CoordinatorLayout.LayoutParams buttonParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);

        buttonParam.topMargin = 30;
        buttonParam.bottomMargin = 30;

        promote.setBackground(getDrawable(R.drawable.button_bubble));
        log.setBackground(getDrawable(R.drawable.button_bubble));
        serialize.setBackground(getDrawable(R.drawable.button_bubble));
        deserialize.setBackground(getDrawable(R.drawable.button_bubble));
        active.setBackground(getDrawable(R.drawable.button_bubble));
        inactive.setBackground(getDrawable(R.drawable.button_bubble));

        promote.setText(getString(R.string.promote));
        log.setText(getString(R.string.viewSalesHistory));
        serialize.setText(getString(R.string.serialize));
        deserialize.setText(getString(R.string.deserialize));
        active.setText(getString(R.string.viewActive));
        inactive.setText(getString(R.string.viewInactive));

        promote.setTextColor(getColor(R.color.white));
        log.setTextColor(getColor(R.color.white));
        serialize.setTextColor(getColor(R.color.white));
        deserialize.setTextColor(getColor(R.color.white));
        active.setTextColor(getColor(R.color.white));
        inactive.setTextColor(getColor(R.color.white));

        promote.setTextSize(20);
        log.setTextSize(20);
        serialize.setTextSize(20);
        deserialize.setTextSize(20);
        active.setTextSize(20);
        inactive.setTextSize(20);

        promote.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        promote.setPaddingRelative(75, 0, 0, 0);

        log.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        log.setPaddingRelative(75, 0, 0, 0);

        serialize.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        serialize.setPaddingRelative(75, 0, 0, 0);

        deserialize.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        deserialize.setPaddingRelative(75, 0, 0, 0);

        active.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        active.setPaddingRelative(75, 0, 0, 0);

        inactive.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        inactive.setPaddingRelative(75, 0, 0, 0);

        promote.setLayoutParams(buttonParam);
        log.setLayoutParams(buttonParam);
        serialize.setLayoutParams(buttonParam);
        deserialize.setLayoutParams(buttonParam);
        active.setLayoutParams(buttonParam);
        inactive.setLayoutParams(buttonParam);

        promote.setOnClickListener(new OptionButtonController(this, 4));
        log.setOnClickListener(new OptionButtonController(this, 5));
        serialize.setOnClickListener(new OptionButtonController(this, 6));
        deserialize.setOnClickListener(new OptionButtonController(this, 9));
        active.setOnClickListener(new OptionButtonController(this, 7));
        inactive.setOnClickListener(new OptionButtonController(this, 8));

        linear.addView(promote);
        linear.addView(log);
        linear.addView(active);
        linear.addView(inactive);
        linear.addView(serialize);
        linear.addView(deserialize);

        grid.addView(linear);

    }

}
