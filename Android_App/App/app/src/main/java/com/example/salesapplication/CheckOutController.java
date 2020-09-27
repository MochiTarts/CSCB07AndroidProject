package com.example.salesapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

class CheckOutController implements View.OnClickListener {

    private Context appContext;
    private int id;
    private String password;
    private Integer account;

    public CheckOutController(Context context, int id, String password, Integer account) {
        this.appContext = context;
        this.id = id;
        this.password = password;
        this.account = account;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this.appContext, CheckOut.class);
        i.putExtra("id", id);
        i.putExtra("password", password);
        i.putExtra("account", account);
        (this.appContext).startActivity(i);
    }

}
