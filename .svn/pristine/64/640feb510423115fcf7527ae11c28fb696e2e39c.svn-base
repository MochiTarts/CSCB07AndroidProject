package com.example.salesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.database.serializer.DatabaseSerializable;
import com.b07.database.serializer.DatabaseSerializableImpl;
import com.b07.database.serializer.DatabaseSerializer;
import com.b07.database.serializer.DatabaseSerializerImpl;

public class OptionButtonController implements View.OnClickListener {
    private int i;
    private Context context;

    public OptionButtonController(Context context, int i) {
        this.i = i;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        if (i == 0) {
            Intent intent = new Intent(this.context, RestockItems.class);
            this.context.startActivity(intent);
        } else if (i == 1) {
            Intent intent = new Intent(this.context, CreateNewUser.class);
            this.context.startActivity(intent);
        } else if (i == 2) {
            Intent intent = new Intent(this.context, CreateNewCustomer.class);
            this.context.startActivity(intent);
        } else if (i == 3) {
            Intent intent = new Intent(this.context, NewAccount.class);
            this.context.startActivity(intent);
        } else if (i == -1) {
            Intent intent = new Intent(this.context, EmployeeOptions.class);
            this.context.startActivity(intent);
        } else if (i == -2) {
            Intent intent = new Intent(this.context, AdminOptions.class);
            this.context.startActivity(intent);
        } else if (i == 4) {
            Intent intent = new Intent(this.context, PromoteEmployee.class);
            this.context.startActivity(intent);
        } else if (i == 5) {
            Intent intent = new Intent(this.context, SalesHistory.class);
            this.context.startActivity(intent);
        } else if (i == 6) {
            DatabaseSerializable serial = new DatabaseSerializableImpl();
            if (serial.pullDatabase()) {
                DatabaseSerializer serializer = new DatabaseSerializerImpl();
                serializer.serialize(serial, System.getProperty("user.dir") + "\\serializedDB\\" ,"database_copy.ser");

                AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
                builder.setMessage("Database was successfully serialized");
                builder.setTitle("Serialize Successful");
                builder.setPositiveButton("Done", new ReturnButtonController(context, -1));

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
                builder.setMessage("Database was not successfully serialized");
                builder.setTitle("Serialize Error");
                builder.setPositiveButton("Done", new ReturnButtonController(context, -1));

                AlertDialog alert = builder.create();
                alert.show();
            }
        } else if (i == 7) {
            Intent intent = new Intent(this.context, ActiveUsers.class);
            this.context.startActivity(intent);
        } else if (i == 8) {
            Intent intent = new Intent(this.context, InactiveUsers.class);
            this.context.startActivity(intent);
        } else if (i == 9) {
            DatabaseSerializable serial = new DatabaseSerializableImpl();
            if (serial.pullDatabase()) {
                DatabaseSerializer serializer = new DatabaseSerializerImpl();
                serializer.deserialize(System.getProperty("user.dir") + "\\serializedDB\\" ,"database_copy.ser");

                AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
                builder.setMessage("Database was successfully deserialized");
                builder.setTitle("Deserialize Successful");
                builder.setPositiveButton("Done", new ReturnButtonController(context, -1));

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.DialogeTheme);
                builder.setMessage("Database was not successfully deserialized");
                builder.setTitle("Deserialize Error");
                builder.setPositiveButton("Done", new ReturnButtonController(context, -1));

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}
