package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.b07.store.ShoppingCart;
import com.b07.users.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ShoppingCartActivity extends AppCompatActivity {

    private BigDecimal currentSubTotal = new BigDecimal(0);
    private int currentTotalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_cart);

        int id = this.getIntent().getExtras().getInt("id");
        String password = this.getIntent().getExtras().getString("password");
        Integer account = this.getIntent().getExtras().getInt("account");

        try {

            GridLayout grid = findViewById(R.id.cart_grid);

            for (Item item: new UserStore().getCart().getItems()) {

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

                ImageButton addItem = new ImageButton(this);
                RelativeLayout.LayoutParams buttonParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                buttonParams.width = 100;
                buttonParams.height = 100;
                addItem.setTag("Add");
                addItem.setLayoutParams(buttonParams);
                addItem.setBackground(getDrawable(R.drawable.plus));
                addItem.setOnClickListener(new ShoppingCartController(this, id, password, account
                        , item, addItem, itemName));

                ImageButton removeItem = new ImageButton(this);
                removeItem.setTag("Remove");
                removeItem.setLayoutParams(buttonParams);
                removeItem.setBackground(getDrawable(R.drawable.minus));
                removeItem.setOnClickListener(new ShoppingCartController(this, id, password,
                        account, item, removeItem, itemName));

                linearImg.addView(img);
                linear.addView(addItem);
                linear.addView(itemName);
                linear.addView(removeItem);
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
            currentSubTotal =
                    currentSubTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
        }

        TextView total = findViewById(R.id.total_items);
        total.setText(getResources().getString(R.string.total_items) + ": " + currentTotalItems);

        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText("$" + currentSubTotal);

        /*List<Item> allItems = new UserStore().getCart().getItems();
        currentTotalItems = 0;

        for (Item item: allItems) {
            currentTotalItems += new UserStore().getCart().getItemQuantity(item);
            BigDecimal itemPrice = item.getPrice();
            currentSubTotal = currentSubTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
        }

        TextView total = findViewById(R.id.total_items);
        total.setText(getResources().getString(R.string.total_items) + ": " + currentTotalItems);

        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText("$" + currentSubTotal);

        TextView fishingRodTotal = findViewById(R.id.fishing_rod_name);
        if (new UserStore().getCart().getItemQuantity(fishingRod) > 0) {
            fishingRodTotal.setText(getResources().getString(R.string.fishing_rod_purchase)
                    +"\n" + "$" + fishingRod.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(fishingRod));
        } else {
            fishingRodTotal.setText(getResources().getString(R.string.fishing_rod_purchase) +"\n" + "$" + fishingRod.getPrice());
        }
        ImageButton addFishingRod = findViewById(R.id.add_fishing_rod);
        addFishingRod.setOnClickListener(new ShoppingCartController(this, id , password, account));
        ImageButton removeFishingRod = findViewById(R.id.remove_fishing_rod);
        removeFishingRod.setOnClickListener(new ShoppingCartController(this, id , password, account));

        TextView hockeyStickRodTotal = findViewById(R.id.hockey_stick_name);
        if (new UserStore().getCart().getItemQuantity(hockeyStick) > 0) {
            hockeyStickRodTotal.setText(getResources().getString(R.string.hockey_stick_purchase)
                    + "\n" + "$" + hockeyStick.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(hockeyStick));
        } else {
            hockeyStickRodTotal.setText(getResources().getString(R.string.hockey_stick_purchase) + "\n" + "$" + hockeyStick.getPrice());
        }
        ImageButton addHockeyStick = findViewById(R.id.add_hockey_stick);
        addHockeyStick.setOnClickListener(new ShoppingCartController(this, id , password, account));
        ImageButton removeHockeyStick = findViewById(R.id.remove_hockey_stick);
        removeHockeyStick.setOnClickListener(new ShoppingCartController(this, id , password, account));

        TextView skatesTotal = findViewById(R.id.skates_name);
        if (new UserStore().getCart().getItemQuantity(skates) > 0) {
            skatesTotal.setText(getResources().getString(R.string.skates_purchase)
                    + "\n" + "$" + skates.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(skates));
        } else {
            skatesTotal.setText(getResources().getString(R.string.skates_purchase) + "\n" + "$" + skates.getPrice());
        }
        ImageButton addSkates = findViewById(R.id.add_skates);
        addSkates.setOnClickListener(new ShoppingCartController(this, id , password, account));
        ImageButton removeSkates = findViewById(R.id.remove_skates);
        removeSkates.setOnClickListener(new ShoppingCartController(this, id , password, account));

        TextView runningShoesTotal = findViewById(R.id.running_shoes_name);
        if (new UserStore().getCart().getItemQuantity(runningShoes) > 0) {
            runningShoesTotal.setText(getResources().getString(R.string.running_shoes_purchase)
                    + "\n" + "$" + runningShoes.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(runningShoes));
        } else {
            runningShoesTotal.setText(getResources().getString(R.string.running_shoes_purchase) + "\n" + "$" + runningShoes.getPrice());
        }
        ImageButton addRunningShoes = findViewById(R.id.add_running_shoes);
        addRunningShoes.setOnClickListener(new ShoppingCartController(this, id , password, account));
        ImageButton removeRunningShoes = findViewById(R.id.remove_running_shoes);
        removeRunningShoes.setOnClickListener(new ShoppingCartController(this, id , password, account));

        TextView proteinBarTotal = findViewById(R.id.protein_bar_name);
        if (new UserStore().getCart().getItemQuantity(proteinBar) > 0) {
            proteinBarTotal.setText(getResources().getString(R.string.protein_bar_purchase)
                    + "\n" + "$" + proteinBar.getPrice()
                    + "\n" + "x" + new UserStore().getCart().getItemQuantity(proteinBar));
        } else {
            proteinBarTotal.setText(getResources().getString(R.string.protein_bar_purchase) + "\n" + "$" + proteinBar.getPrice());
        }
        ImageButton addProteinBar = findViewById(R.id.add_protein_bar);
        addProteinBar.setOnClickListener(new ShoppingCartController(this, id , password, account));
        ImageButton removeProteinBar = findViewById(R.id.remove_protein_bar);
        removeProteinBar.setOnClickListener(new ShoppingCartController(this, id , password,
        account));
         */

        Button checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new CheckOutController(this, id, password, account));

        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(ShoppingCartActivity.this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(ShoppingCartActivity.this));

        Button saveCart = findViewById(R.id.save_cart);
        saveCart.setOnClickListener(new SaveCartController(ShoppingCartActivity.this, account, id));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(ShoppingCartActivity.this));

    }

}
