package com.example.salesapplication;

import android.os.Bundle;

import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SalesHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseSelectHelperAndroid select = new DatabaseSelectHelperAndroid(this);
        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new OptionButtonController(this, -2));

        GridLayout grid = findViewById(R.id.grid);

        LinearLayout linear = new LinearLayout(this);
        CoordinatorLayout.LayoutParams linearParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        linearParam.topMargin = 15;
        linearParam.bottomMargin = 15;
        linearParam.leftMargin = 15;
        linearParam.rightMargin = 15;
        linear.setLayoutParams(linearParam);
        linear.setOrientation(LinearLayout.VERTICAL);

        CoordinatorLayout.LayoutParams textParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        textParam.topMargin = 15;
        textParam.leftMargin = 15;
        textParam.rightMargin = 15;

        try {
            SalesLog log = select.getCompleteSales();
            List<Sale> sales = log.getSales();
            BigDecimal total = new BigDecimal(0);

            List<Item> allItems = select.getAllItems();

            int[] quantities = new int[allItems.size()];


            for (int i = 0; i < sales.size(); i++) {
                total = total.add(sales.get(i).getTotalPrice());

                TextView customer = new TextView(this);
                customer.setLayoutParams(textParam);

                customer.setTextColor(getColor(R.color.Grey));
                customer.setTextSize(18);
                customer.setText(R.string.customer + " " + sales.get(i).getUser().getName());

                TextView purchaseNumber = new TextView(this);
                purchaseNumber.setLayoutParams(textParam);
                purchaseNumber.setTextColor(getColor(R.color.Grey));
                purchaseNumber.setTextSize(18);
                purchaseNumber.setText(R.string.purchase_number + " " + sales.get(i).getId());

                TextView totalPrice = new TextView(this);
                totalPrice.setLayoutParams(textParam);
                totalPrice.setTextColor(getColor(R.color.Grey));
                totalPrice.setTextSize(18);
                totalPrice.setText(R.string.total_purchase_price + " " + sales.get(i).getTotalPrice());

                TextView breakDown = new TextView(this);
                breakDown.setLayoutParams(textParam);
                breakDown.setTextColor(getColor(R.color.Grey));
                breakDown.setTextSize(18);
                breakDown.setText(R.string.itemized_breakdown + " ");

                ImageView line = new ImageView(this);
                line.setLayoutParams(textParam);
                line.setBackground(getDrawable(R.drawable.horizontal_line));

                linear.addView(customer);
                linear.addView(purchaseNumber);
                linear.addView(totalPrice);
                linear.addView(breakDown);

                for (Map.Entry<Item, Integer> entry : sales.get(i).getItemMap().entrySet()) {
                    CoordinatorLayout.LayoutParams itemParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    itemParam.topMargin = 15;
                    itemParam.leftMargin = 15;

                    TextView item = new TextView(this);
                    item.setLayoutParams(textParam);
                    item.setTextColor(getColor(R.color.Grey));
                    item.setTextSize(18);
                    item.setGravity(Gravity.END);
                    item.setText(replaceUnderScore(entry.getKey().getName()).toLowerCase() + " : " + entry.getValue());

                    linear.addView(item);


                    quantities[entry.getKey().getId() - 1] += entry.getValue();
                }

                linear.addView(line);
            }


            for (int j = 0; j < allItems.size(); j++) {
                TextView items = new TextView(this);
                items.setLayoutParams(textParam);

                items.setTextColor(getColor(R.color.Grey));
                items.setTextSize(18);
                items.setText(allItems.get(j).getId() + ". " + replaceUnderScore(allItems.get(j).getName()).toLowerCase() + " : " + quantities[j]);
                linear.addView(items);
            }

            TextView totalSale = new TextView(this);
            totalSale.setLayoutParams(textParam);

            totalSale.setTextColor(getColor(R.color.Grey));
            totalSale.setTextSize(18);
            totalSale.setText(R.string.total_purchase_price + "" + total);
            linear.addView(totalSale);

            grid.addView(linear);
        } catch (Exception e) {
            TextView message = new TextView(this);
            CoordinatorLayout.LayoutParams text2Param = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            text2Param.topMargin = 20;
            text2Param.leftMargin = 50;
            message.setLayoutParams(text2Param);

            message.setText(getString(R.string.noSales));
            message.setTextColor(getColor(R.color.Grey));
            message.setTextSize(18);
            grid.addView(message);
        }
    }

    private String replaceUnderScore(String s) {
        return s.replace('_', ' ');
    }
}
