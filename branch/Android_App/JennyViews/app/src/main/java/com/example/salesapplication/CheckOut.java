package com.example.salesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class CheckOut extends AppCompatActivity {

    private BigDecimal currentTotal = new BigDecimal(0);
    private int currentTotalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_checkout);

        int id = this.getIntent().getExtras().getInt("id");
        String password = this.getIntent().getExtras().getString("password");
        Integer account = this.getIntent().getExtras().getInt("account");

        try {

            GridLayout grid = findViewById(R.id.checkout_grid);

            for (Item item: new UserStore().getCart().getItems()) {

                Log.d("Items to buy", "" + new UserStore().getCart().getItems().size());

                LinearLayout relative = new LinearLayout(this);
                GridLayout.LayoutParams relativeParams = new GridLayout.LayoutParams();
                relativeParams.bottomMargin = 175;
                relativeParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f);
                relativeParams.width = 0;
                relativeParams.setMargins(50, 50, 50, 50);
                relative.setGravity(Gravity.CENTER_VERTICAL);
                relative.setPadding(0, 100, 0, 100);
                relative.setWeightSum(2f);
                relative.setBackground(getDrawable(R.drawable.bubble));

                relative.setLayoutParams(relativeParams);

                LinearLayout linearImg = new LinearLayout(this);
                LinearLayout.LayoutParams linearParamsImg =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,
                                1f);
                linearParamsImg.weight = 1;
                linearImg.setGravity(Gravity.CENTER);
                linearImg.setOrientation(LinearLayout.VERTICAL);
                linearImg.setLayoutParams(linearParamsImg);

                ImageView img = new ImageView(this);
                img.setBackground(getDrawable(R.drawable.image_placeholder));
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                imgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                imgParams.width = 350;
                imgParams.height = 350;
                img.setLayoutParams(imgParams);
                img.setId(img.generateViewId());

                LinearLayout linear = new LinearLayout(this);
                LinearLayout.LayoutParams linearParams =
                        new LinearLayout.LayoutParams(0,
                                RelativeLayout.LayoutParams.MATCH_PARENT, 1f);
                linearParams.weight = 1;
                linear.setGravity(Gravity.CENTER);
                linear.setOrientation(LinearLayout.VERTICAL);
                linear.setGravity(Gravity.CENTER);
                linear.setLayoutParams(linearParams);

                TextView itemName = new TextView(this);
                RelativeLayout.LayoutParams textParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                textParams.topMargin = 25;
                textParams.bottomMargin = 25;
                itemName.setLayoutParams(textParams);
                itemName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                itemName.setTextColor(getColor(R.color.dark_text));

                if (new UserStore().getCart().getItemQuantity(item) > 0) {
                    itemName.setText(item.getName() + "\n" + "$" + item.getPrice() + "\n"
                            + "x" +new UserStore().getCart().getItemQuantity(item));
                } else {
                    itemName.setText(item.getName() + "\n" + "$" + item.getPrice());
                }

                linearImg.addView(img);
                linear.addView(itemName);
                relative.addView(linearImg);
                relative.addView(linear);
                grid.addView(relative);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Item> allItems = new UserStore().getCart().getItems();
        currentTotalItems = 0;

        for (Item item: allItems) {
            currentTotalItems += new UserStore().getCart().getItemQuantity(item);
            BigDecimal itemPrice = item.getPrice();
            currentTotal =
                    currentTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
        }

        currentTotal = currentTotal.multiply(new UserStore().getCart().getTaxRate());

        TextView total = findViewById(R.id.total_items);
        total.setText(getResources().getString(R.string.total_items) + ": " + currentTotalItems);

        TextView totalTax = findViewById(R.id.totalTax);
        totalTax.setText("$" + currentTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        /*List<Item> allItems = new UserStore().getCart().getItems();
        currentTotalItems = 0;

        for (Item item: allItems) {
            currentTotalItems += new UserStore().getCart().getItemQuantity(item);
            BigDecimal itemPrice = item.getPrice();
            currentTotal = currentTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
        }

        currentTotal = currentTotal.multiply(new UserStore().getCart().getTaxRate());

        TextView total = findViewById(R.id.total_items);
        total.setText(getResources().getString(R.string.total_items) + ": " + currentTotalItems);

        TextView totalTax = findViewById(R.id.totalTax);
        totalTax.setText("$" + currentTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        TextView fishingRodTotal = findViewById(R.id.fishing_rod_name);
        if (new UserStore().getCart().getItemQuantity(fishingRod) > 0) {
            fishingRodTotal.setText(getResources().getString(R.string.fishing_rod_purchase)
                    +"\n" + "$" + fishingRod.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(fishingRod));
        } else {
            fishingRodTotal.setText(getResources().getString(R.string.fishing_rod_purchase) +"\n" + fishingRod.getPrice());
        }

        TextView hockeyStickRodTotal = findViewById(R.id.hockey_stick_name);
        if (new UserStore().getCart().getItemQuantity(hockeyStick) > 0) {
            hockeyStickRodTotal.setText(getResources().getString(R.string.hockey_stick_purchase)
                    + "\n" + "$" + hockeyStick.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(hockeyStick));
        } else {
            hockeyStickRodTotal.setText(getResources().getString(R.string.hockey_stick_purchase) + "\n" + hockeyStick.getPrice());
        }

        TextView skatesTotal = findViewById(R.id.skates_name);
        if (new UserStore().getCart().getItemQuantity(skates) > 0) {
            skatesTotal.setText(getResources().getString(R.string.skates_purchase)
                    + "\n" + "$" + skates.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(skates));
        } else {
            skatesTotal.setText(getResources().getString(R.string.skates_purchase) + "\n" + skates.getPrice());
        }

        TextView runningShoesTotal = findViewById(R.id.running_shoes_name);
        if (new UserStore().getCart().getItemQuantity(runningShoes) > 0) {
            runningShoesTotal.setText(getResources().getString(R.string.running_shoes_purchase)
                    + "\n" + "$" + runningShoes.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(runningShoes));
        } else {
            runningShoesTotal.setText(getResources().getString(R.string.running_shoes_purchase) + "\n" + runningShoes.getPrice());
        }

        TextView proteinBarTotal = findViewById(R.id.protein_bar_name);
        if (new UserStore().getCart().getItemQuantity(proteinBar) > 0) {
            proteinBarTotal.setText(getResources().getString(R.string.protein_bar_purchase)
                    + "\n" + "$" + proteinBar.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(proteinBar));
        } else {
            proteinBarTotal.setText(getResources().getString(R.string.protein_bar_purchase) + "\n" + proteinBar.getPrice());
        }*/

        Button purchase = findViewById(R.id.purchase);
        purchase.setOnClickListener(new PurchaseController(CheckOut.this, id, password, account));

        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(CheckOut.this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(CheckOut.this));

        Button saveCart = findViewById(R.id.save_cart);
        saveCart.setOnClickListener(new SaveCartController(CheckOut.this, account, id));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(CheckOut.this));

    }

}
