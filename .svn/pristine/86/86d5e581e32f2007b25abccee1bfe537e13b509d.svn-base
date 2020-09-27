package com.example.salesapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

public class ReturnButtonController implements DialogInterface.OnClickListener {
    private Context context;
    private int i;

    public ReturnButtonController(Context context, int i) {
        this.context = context;
        this.i = i;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (i == 0) {
            Intent intent = new Intent(this.context, EmployeeOptions.class);
            context.startActivity(intent);
        } else if (i == 1) {
            Intent intent = new Intent(this.context, AdminOptions.class);
            context.startActivity(intent);
        }
    }
}
