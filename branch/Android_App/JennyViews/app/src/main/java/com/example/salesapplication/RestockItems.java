package com.example.salesapplication;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.b07.inventory.Item;

import java.util.List;

public class RestockItems extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);
        DatabaseUpdateHelperAndroid update = new DatabaseUpdateHelperAndroid(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new OptionButtonController(this, -1));

        Intent intent = getIntent();

        try {
            List<Item> items = select.getAllItems();

            GridLayout grid = findViewById(R.id.grid);

            for (Item i: items) {

                CoordinatorLayout coordinator = new CoordinatorLayout(this);
                CoordinatorLayout.LayoutParams cordParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                cordParams.gravity = Gravity.CENTER_HORIZONTAL;
                cordParams.topMargin = 30;
                cordParams.bottomMargin = 30;
                cordParams.leftMargin = 15;
                cordParams.rightMargin = 15;

                coordinator.setBackground(getDrawable(R.drawable.bubble));
                coordinator.setLayoutParams(cordParams);

                ImageView img = new ImageView(this);
                img.setBackground(getDrawable(R.drawable.image_placeholder));

                CoordinatorLayout.LayoutParams imgParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                imgParams.topMargin = 150;
                imgParams.bottomMargin = 150;
                imgParams.leftMargin = 75;

                img.setLayoutParams(imgParams);

                TextView itemName = new TextView(this);
                CoordinatorLayout.LayoutParams nameParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                nameParams.leftMargin = 100;
                nameParams.topMargin = 40;

                itemName.setLayoutParams(nameParams);
                itemName.setText(replaceUnderScore(i.getName().toLowerCase()));
                itemName.setTextColor(getColor(R.color.Grey));
                itemName.setTextSize(20);


                TextView stock = new TextView(this);
                CoordinatorLayout.LayoutParams stockParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                stockParam.leftMargin = 100;
                stockParam.bottomMargin = 40;
                stockParam.gravity = Gravity.BOTTOM;

                stock.setLayoutParams(stockParam);
                stock.setText(getString(R.string.stock) + " " + select.getInventoryQuantity(i.getId()));
                stock.setTextColor(getColor(R.color.Grey));
                stock.setTextSize(20);


                LinearLayout linear = new LinearLayout(this);
                CoordinatorLayout.LayoutParams linearParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
                linearParam.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                linearParam.rightMargin = 150;
                linear.setLayoutParams(linearParam);
                linear.setOrientation(LinearLayout.VERTICAL);


                /*TextView addStock = new TextView(this);
                CoordinatorLayout.LayoutParams addStockParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                addStockParam.gravity = Gravity.CENTER;

                addStock.setLayoutParams(addStockParam);
                addStock.setText(getString(R.string.addToStock));
                addStock.setTextSize(20);
                addStock.setTextColor(getColor(R.color.Grey));*/

                EditText amount = new EditText(this);
                CoordinatorLayout.LayoutParams amountParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                amountParam.gravity = Gravity.CENTER;
                amountParam.topMargin = 25;
                amountParam.bottomMargin = 35;

                amount.setLayoutParams(amountParam);
                amount.setHint(getString(R.string.amount));
                amount.setHintTextColor(getColor(R.color.Grey));
                amount.setTextColor(getColor(R.color.Grey));
                amount.setBackground(getDrawable(R.drawable.input_bubble));
                amount.setInputType(InputType.TYPE_CLASS_NUMBER);

                Button add = new Button(this);
                CoordinatorLayout.LayoutParams buttonParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                buttonParam.gravity = Gravity.CENTER;

                add.setLayoutParams(buttonParam);
                add.setText(getString(R.string.add));
                add.setTextColor(getColor(R.color.white));
                add.setBackground(getDrawable(R.drawable.button_bubble));

                add.setOnClickListener(new RestockButtonController(this, amount, i, stock));


                //linear.addView(addStock);
                linear.addView(amount);
                linear.addView(add);
                coordinator.addView(img);
                coordinator.addView(itemName);
                coordinator.addView(stock);
                coordinator.addView(linear);

                grid.addView(coordinator);
            }

        } catch (Exception e) {

        }
    }

    private String replaceUnderScore(String s) {
        return s.replace('_', ' ');
    }
}
