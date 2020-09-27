package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.DatabaseSelectHelperException;

import java.sql.SQLException;

class AccountLoginController implements View.OnClickListener {

    private Context appContext;
    private int id;
    private String password;

    public AccountLoginController(Context context, int id, String password) {
        this.appContext = context;
        this.id = id;
        this.password = password;
    }

    @Override
    public void onClick(View v) {

        EditText getAccount = ((Activity) this.appContext).findViewById(R.id.account_id);
        int account = 0;
        try {
            account = Integer.parseInt(getAccount.getText().toString());
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }

        try {
            DatabaseSelectAdapter.getAccountDetails(account);
            Toast.makeText(this.appContext, "This account is inactive",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(this.appContext, LoginScreen.class);
            (this.appContext).startActivity(i);
        } catch (SQLException | DatabaseSelectHelperException e) {
            e.printStackTrace();
            try {
                if (DatabaseSelectAdapter.getUserAccounts(id).contains(account)) {
                    Intent i = new Intent(this.appContext, UserStore.class);
                    i.putExtra("account", account);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    (this.appContext).startActivity(i);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (DatabaseSelectHelperException ex) {
                ex.printStackTrace();
            }
        }

    }
}
