package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.ItemSetException;
import com.b07.exceptions.Unauthenticated;
import com.b07.exceptions.UserSetException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.Customer;
import com.b07.users.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class UserStore extends AppCompatActivity {

    public Context getContext() {
        return this;
    }

    private static ShoppingCartInterface customerCart;

    public void setCart(ShoppingCartInterface updatedCart) {
        customerCart = updatedCart;
    }

    public ShoppingCartInterface getCart() {
        return customerCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        int id = this.getIntent().getExtras().getInt("id");
        String password = this.getIntent().getExtras().getString("password");
        Integer account = this.getIntent().getExtras().getInt("account");

        Log.d("Account", "" + account);

        ShoppingCartInterface initialCart = null;

        try {

            GridLayout storeGrid = findViewById(R.id.store_grid);

            List<Item> listItems = DatabaseSelectAdapter.getAllItems();
            Log.d("Size", "" + listItems.size());

            User currentCustomer = DatabaseSelectAdapter.getUserDetails(id);
            currentCustomer.authenticate(password);

            for (Item item: listItems) {

                RelativeLayout relative = new RelativeLayout(this);
                GridLayout.LayoutParams relativeParams = new GridLayout.LayoutParams();
                relativeParams.bottomMargin = 175;
                relativeParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f);
                relativeParams.width = 0;
                relative.setGravity(Gravity.CENTER_HORIZONTAL);
                //relative.setBackground(getDrawable(R.drawable.bubble_dark));

                relative.setLayoutParams(relativeParams);

                ImageView img = new ImageView(this);
                img.setBackground(getDrawable(R.drawable.image_placeholder));
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                imgParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                imgParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                imgParams.bottomMargin = 300;
                imgParams.width = 350;
                imgParams.height = 350;
                imgParams.setMarginEnd(20);
                imgParams.setMarginStart(20);
                img.setLayoutParams(imgParams);
                img.setId(img.generateViewId());

                LinearLayout linear = new LinearLayout(this);
                RelativeLayout.LayoutParams linearParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT);
                linearParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                linear.setGravity(Gravity.CENTER);
                linear.setOrientation(LinearLayout.VERTICAL);
                linear.setLayoutParams(linearParams);

                TextView itemName = new TextView(this);
                RelativeLayout.LayoutParams textParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                textParams.setMarginEnd(20);
                textParams.setMarginStart(20);
                textParams.bottomMargin = 25;
                itemName.setLayoutParams(textParams);
                itemName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                itemName.setText(item.getName() + "\n" + "$" + item.getPrice());
                itemName.setTextColor(getColor(R.color.dark_text));

                ImageButton addItem = new ImageButton(this);
                RelativeLayout.LayoutParams buttonParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                buttonParams.setMarginStart(20);
                buttonParams.setMarginEnd(20);
                buttonParams.width = 100;
                buttonParams.height = 100;
                addItem.setLayoutParams(buttonParams);
                addItem.setBackground(getDrawable(R.drawable.plus));
                addItem.setOnClickListener(new AddItemController(this, item));


                relative.addView(img);
                linear.addView(itemName);
                linear.addView(addItem);
                relative.addView(linear);

                storeGrid.addView(relative);

                FloatingActionButton shopping_cart = findViewById(R.id.cart);
                shopping_cart.setOnClickListener(new ShoppingCartControllerFromUserStore(UserStore.this, id,
                        password, account, item));

                Log.d("Here", "added view");


                /*if (item.getName().equals("FISHING_ROD")) {
                    fishingRod = item;
                    initialCart.addItem(fishingRod, 0);
                }
                if (item.getName().equals("HOCKEY_STICK")) {
                    hockeyStick = item;
                    initialCart.addItem(hockeyStick, 0);
                }
                if (item.getName().equals("SKATES")) {
                    skates = item;
                    initialCart.addItem(skates, 0);
                }
                if (item.getName().equals("RUNNING_SHOES")) {
                    runningShoes = item;
                    initialCart.addItem(runningShoes, 0);
                }
                if (item.getName().equals("PROTEIN_BAR")) {
                    proteinBar = item;
                    initialCart.addItem(proteinBar, 0);
                }*/
            }

            initialCart = new ShoppingCart((Customer) currentCustomer);

            DatabaseSelectAdapter.getShoppingCart((Customer) currentCustomer, account);

            Log.d("Saved cart", "Cart saved");
            setCart(DatabaseSelectAdapter.getShoppingCart((Customer) currentCustomer, account));

        } catch (SQLException | DatabaseSelectHelperException | BuildTypeException | UserSetException | Unauthenticated | ItemSetException e) {
            e.printStackTrace();
            Log.d("New cart", "Cart not saved");
            setCart(initialCart);
        }

        /*List<Item> allItems = getCart().getItems();
        int total_items = 0;

        for (Item item: allItems) {
            total_items += new UserStore().getCart().getItemQuantity(item);
        }

        if (total_items == 0) {
            TextView item_count = findViewById(R.id.item_count);
            item_count.setVisibility(TextView.GONE);
        } else {
            TextView item_count = findViewById(R.id.item_count);
            item_count.setVisibility(TextView.VISIBLE);

            item_count.setText(""+ total_items);
        }

        ImageButton add_fishing_rod = findViewById(R.id.add_fishing_rod);
        add_fishing_rod.setOnClickListener(new AddItemController(UserStore.this));

        ImageButton add_hockey_stick = findViewById(R.id.add_hockey_stick);
        add_hockey_stick.setOnClickListener(new AddItemController(UserStore.this));

        ImageButton add_skates = findViewById(R.id.add_skates);
        add_skates.setOnClickListener(new AddItemController(UserStore.this));

        ImageButton add_running_shoes = findViewById(R.id.add_running_shoes);
        add_running_shoes.setOnClickListener(new AddItemController(UserStore.this));

        ImageButton add_protein_bar = findViewById(R.id.add_protein_bar);
        add_protein_bar.setOnClickListener(new AddItemController(UserStore.this));*/

        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new MenuController(UserStore.this));

        ImageButton close = findViewById(R.id.close_menu);
        close.setOnClickListener(new MenuController(UserStore.this));

        Button saveCart = findViewById(R.id.save_cart);
        saveCart.setOnClickListener(new SaveCartController(UserStore.this, account, id));

        Button logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new LogOutController(UserStore.this));

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        List<Item> allItems = getCart().getItems();
        int total_items = 0;

        for (Item item: allItems) {
            total_items += getCart().getItemQuantity(item);
        }

        TextView item_count = findViewById(R.id.item_count);
        if (total_items > 0) {
            item_count.setText("" + total_items);
        } else {
            item_count.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
