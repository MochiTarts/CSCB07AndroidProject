package com.example.salesapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.b07.inventory.Item;

public class RestockButtonController implements View.OnClickListener {
    private EditText editText;
    private Item item;
    private Context context;
    private TextView stock;

    public RestockButtonController(Context context, EditText editText, Item item, TextView stock) {
     this.editText = editText;
     this.item = item;
     this.context = context;
     this.stock = stock;
    }

    @Override
    public void onClick(View view) {
        DatabaseUpdateHelperAndroid update = new DatabaseUpdateHelperAndroid(context);
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(context);

        String s = this.editText.getText().toString();
        try {
            update.updateInventoryQuantity(Integer.parseInt(s) + select.getInventoryQuantity(this.item.getId()), this.item.getId());

            stock.setText(R.string.inStock + " " + select.getInventoryQuantity(this.item.getId()));
        } catch (Exception e) {

        }
    }
}
