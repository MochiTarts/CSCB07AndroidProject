package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.User;

import java.sql.SQLException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

class AddItemController implements View.OnClickListener {

    private Context appContext;
    private Item item;

    public AddItemController(Context context, Item item) {
        this.appContext = context;
        this.item = item;
    }

    @Override
    public void onClick(View v) {
        ShoppingCartInterface currentCart = new UserStore().getCart();

        currentCart.addItem(this.item, 1);
        new UserStore().setCart(currentCart);

        UpdateItemBadge();

        /*Item fishingRod = null;
        Item hockeyStick = null;
        Item skates = null;
        Item runningShoes = null;
        Item proteinBar = null;

        try {

            for (Item item: DatabaseSelectAdapter.getAllItems()) {
                if (item.getName().equals("FISHING_ROD")) {
                    fishingRod = item;
                }
                if (item.getName().equals("HOCKEY_STICK")) {
                    hockeyStick = item;
                }
                if (item.getName().equals("SKATES")) {
                    skates = item;
                }
                if (item.getName().equals("RUNNING_SHOES")) {
                    runningShoes = item;
                }
                if (item.getName().equals("PROTEIN_BAR")) {
                    proteinBar = item;
                }
            }

            switch (v.getId()) {
                case R.id.add_fishing_rod:
                    currentCart.addItem(fishingRod, 1);
                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_hockey_stick:
                    currentCart.addItem(hockeyStick, 1);
                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_skates:
                    currentCart.addItem(skates, 1);
                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_running_shoes:
                    currentCart.addItem(runningShoes, 1);
                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_protein_bar:
                    currentCart.addItem(proteinBar, 1);
                    new UserStore().setCart(currentCart);
                    break;
            }

            UpdateItemBadge();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ItemSetException e) {
            e.printStackTrace();
        }*/
    }

    public void UpdateItemBadge() {

        List<Item> allItems = new UserStore().getCart().getItems();
        int total_items = 0;

        for (Item item: allItems) {
            total_items += new UserStore().getCart().getItemQuantity(item);
        }

        if (total_items == 0) {
            TextView item_count = ((Activity) this.appContext).findViewById(R.id.item_count);
            item_count.setVisibility(TextView.GONE);
        } else {
            TextView item_count = ((Activity) this.appContext).findViewById(R.id.item_count);
            item_count.setVisibility(TextView.VISIBLE);

            item_count.setText(""+ total_items);
        }

    }

}
