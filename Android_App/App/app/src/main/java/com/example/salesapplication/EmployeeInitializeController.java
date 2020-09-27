package com.example.salesapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.PasswordUnMatchException;

import java.sql.SQLException;
import java.util.List;

class EmployeeInitializeController implements View.OnClickListener {

    private Context appContext;

    public EmployeeInitializeController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View v) {

        try {
            EditText getName = ((Activity) this.appContext).findViewById(R.id.name);
            String name = getName.getText().toString();

            EditText getAge = ((Activity) this.appContext).findViewById(R.id.age);
            int age = 0;
            if (!(getAge.getText().toString().equals(""))) {
                try {
                    age = Integer.parseInt(getAge.getText().toString());
                } catch(NumberFormatException | NullPointerException e) {
                    throw new DatabaseInsertHelperException("Failed to insert user");
                }
            }

            EditText getAddress = ((Activity) this.appContext).findViewById(R.id.address);
            String address = getAddress.getText().toString();

            EditText getPassword = ((Activity) this.appContext).findViewById(R.id.password);
            String password = getPassword.getText().toString();

            EditText verifyPassword = ((Activity) this.appContext).findViewById(R.id.verify_password);
            String verify = verifyPassword.getText().toString();

            if (!password.equals(verify)) {
                throw new PasswordUnMatchException("Passwords don't match");
            }

            int employeeId = DatabaseInsertAdapter.insertNewUser(name, age, address, password);
            List<Integer> roldeIds = DatabaseSelectAdapter.getRoleIds();
            int roleId = 0;
            for (Integer id: roldeIds) {
                if (DatabaseSelectAdapter.getRoleName(id).equals("EMPLOYEE")) {
                    roleId = id;
                }
            }

            DatabaseInsertAdapter.insertUserRole(employeeId, roleId);

            final AlertDialog.Builder alert = new AlertDialog.Builder(this.appContext, R.style.DialogeTheme);
            alert.setTitle("Employee ID").setMessage("Your employee id is: " + employeeId)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent((alert).getContext(),
                                    LoginScreen.class);
                            ((alert).getContext()).startActivity(i);
                        }
                    })
                    .setCancelable(false)
                    .show();

        }  catch (DatabaseInsertException | PasswordUnMatchException | SQLException | DatabaseInsertHelperException | DatabaseSelectHelperException e) {
            e.printStackTrace();
            Toast.makeText(this.appContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
