package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.PasswordUnMatchException;
import com.b07.exceptions.UserSetException;
import com.b07.users.User;

import java.sql.SQLException;
import java.util.List;

class LoginButtonController implements View.OnClickListener {
    private final Context appContext;

    public LoginButtonController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View v) {
        try {

            EditText getLogin = ((Activity) this.appContext).findViewById(R.id.login_id);
            int logindId = 0;
            if (!(getLogin.getText().toString().equals(""))) {
                try {
                    logindId = Integer.parseInt(getLogin.getText().toString());
                } catch(NumberFormatException | NullPointerException e) {
                    throw new DatabaseInsertHelperException("Failed to insert user");
                }
            }
            Log.d("It's 1 right?", "" + logindId);

            EditText getPassword = ((Activity) this.appContext).findViewById(R.id.password);
            String password = getPassword.getText().toString();
            Log.d("It's a right?", password);

            List<Integer> roldeIds = DatabaseSelectAdapter.getRoleIds();
            int adminRoldId = 0;
            int emplRoldId = 0;
            int custRoleId = 0;
            for (Integer id: roldeIds) {
                if (DatabaseSelectAdapter.getRoleName(id).equals("ADMIN")) {
                    adminRoldId = id;
                }
                if (DatabaseSelectAdapter.getRoleName(id).equals("EMPLOYEE")) {
                    emplRoldId = id;
                }
                if (DatabaseSelectAdapter.getRoleName(id).equals("CUSTOMER")) {
                    custRoleId = id;
                }
            }

            if (DatabaseSelectAdapter.getUserRoleId(logindId) == adminRoldId) {
                User admin = DatabaseSelectAdapter.getUserDetails(logindId);
                Log.d("Check please", "" + admin);
                Log.d("Correct", "" + admin.authenticate(password));
                Log.d("Check please", "" + admin);
                if (admin.authenticate(password)) {
                    Intent i = new Intent(this.appContext, AdminOptions.class);
                    (this.appContext).startActivity(i);
                } else {
                    throw new PasswordUnMatchException("Incorrect password");
                }
            } else if (DatabaseSelectAdapter.getUserRoleId(logindId) == emplRoldId) {
                User employee = DatabaseSelectAdapter.getUserDetails(logindId);
                if (employee.authenticate(password)) {
                    Intent i = new Intent(this.appContext, EmployeeOptions.class);
                    (this.appContext).startActivity(i);
                } else {
                    throw new PasswordUnMatchException("Incorrect password");
                }
            } else if (DatabaseSelectAdapter.getUserRoleId(logindId) == custRoleId) {
                User customer = DatabaseSelectAdapter.getUserDetails(logindId);
                if (customer.authenticate(password)) {
                    try {
                        DatabaseSelectAdapter.getUserAccounts(logindId);
                        Intent i = new Intent(this.appContext, LoginScreenAccount.class);
                        i.putExtra("id", logindId);
                        i.putExtra("password", password);
                        (this.appContext).startActivity(i);
                        Toast.makeText(this.appContext, "HAS ACCOUNT", Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        Intent i = new Intent(this.appContext, UserStore.class);
                        i.putExtra("id", logindId);
                        i.putExtra("password", password);
                        (this.appContext).startActivity(i);
                        Toast.makeText(this.appContext, "NO ACCOUNT", Toast.LENGTH_LONG).show();
                    }
                } else {
                    throw new PasswordUnMatchException("Incorrect password");
                }
            }

        } catch( PasswordUnMatchException | DatabaseSelectHelperException | SQLException |
                BuildTypeException | UserSetException | DatabaseInsertHelperException e) {
            e.printStackTrace();
            Toast.makeText(this.appContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
