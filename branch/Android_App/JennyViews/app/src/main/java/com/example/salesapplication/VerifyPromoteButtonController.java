package com.example.salesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.b07.users.User;

public class VerifyPromoteButtonController implements View.OnClickListener {
    private User user;
    private Context context;

    public VerifyPromoteButtonController(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onClick(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
            builder.setMessage("Are you sure you want to promote the only Employee? \n\nIf so, no new Employees and Customers will be able to be created");
            builder.setTitle("Warning!");
            builder.setNegativeButton("Cancel", new ReturnButtonController(context, 1));
            builder.setPositiveButton("Continue", new PromoteButtonController(this.context, this.user));
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {

        }
    }

}
