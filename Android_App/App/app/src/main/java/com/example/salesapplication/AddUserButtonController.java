package com.example.salesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.b07.database.helper.DatabaseInsertHelperAndroid;

public class AddUserButtonController implements View.OnClickListener {
    private int user;
    private EditText name;
    private EditText age;
    private EditText address;
    private EditText password;
    private EditText passwordConfirm;
    private Context context;

    public AddUserButtonController(Context context, int user, EditText name, EditText age, EditText address, EditText password, EditText passwordConfirm) {
        this.user = user;
        this.name = name;
        this.age = age;
        this.address = address;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        try {
            DatabaseInsertHelperAndroid insert = new DatabaseInsertHelperAndroid(this.context);
            int ageInt = Integer.parseInt(age.getText().toString());
            String nameString = this.name.getText().toString();
            String addressString = this.address.getText().toString();
            String passwordString = this.password.getText().toString();
            String passwordConfirmString = this.passwordConfirm.getText().toString();

            boolean condition = !nameString.equals("") && !addressString.equals("") && !passwordConfirmString.equals("") && !passwordString.equals("");

            if (condition && passwordString.equals(passwordConfirmString)) {
                int id = insert.insertNewUser(nameString, ageInt, addressString, passwordString);
                insert.insertUserRole(id, this.user);

                AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
                builder.setMessage("The User ID is " + id);
                builder.setTitle("New User Created");
                builder.setPositiveButton("Done", new ReturnButtonController(context, 0));

                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch(Exception e) {

        }
    }
}
