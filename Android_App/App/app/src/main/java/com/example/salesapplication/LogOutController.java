package com.example.salesapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

class LogOutController implements View.OnClickListener {

    private Context appContext;

    public LogOutController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this.appContext, LoginScreen.class);
        (this.appContext).startActivity(i);
    }
}
