package com.example.salesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.b07.users.User;

public class PromoteButtonController implements View.OnClickListener, DialogInterface.OnClickListener {
    private Context context;
    private User user;

    public PromoteButtonController(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onClick(View view) {
        promote();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        promote();
    }

    private void promote() {
        try {
            DatabaseUpdateHelperAndroid update = new DatabaseUpdateHelperAndroid(this.context);
            update.updateUserRole(findAdminId(), this.user.getId());

            AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
            builder.setMessage("The employee ID: " + this.user.getId() + " has been promoted");
            builder.setTitle("Promoted!");

            builder.setNeutralButton("Done", new ReturnButtonController(context, 1));

            AlertDialog alert = builder.create();
            //alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getColor(R.color.white));
            alert.show();

        } catch(Exception e) {

        }
    }


    private int findAdminId() {
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this.context);
        try {
            for (Integer i : select.getRoleIds()) {
                if (select.getRoleName(i).equals("ADMIN")) {
                    return i;
                }
            }
        } catch (Exception e) {

        }
        return -1;
    }
}
