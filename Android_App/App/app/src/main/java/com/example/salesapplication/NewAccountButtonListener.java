package com.example.salesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.users.User;

public class NewAccountButtonListener implements View.OnClickListener {
    private Context context;
    private User user;


    public NewAccountButtonListener(Context context, User user) {
        this.context = context;
        this.user = user;
    }


    @Override
    public void onClick(View view) {
        try {
            DatabaseInsertHelperAndroid insert = new DatabaseInsertHelperAndroid(this.context);
            int id = insert.insertAccount(this.user.getId(), true);

            AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
            builder.setMessage("The account ID is " + id);
            builder.setTitle("New Account Created");
            builder.setPositiveButton("Done", new ReturnButtonController(context, 0));

            AlertDialog alert = builder.create();
            alert.show();

        } catch (Exception e) {

        }
    }
}
