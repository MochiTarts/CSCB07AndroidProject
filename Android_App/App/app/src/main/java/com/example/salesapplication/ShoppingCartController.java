package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.User;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

class ShoppingCartController implements View.OnClickListener {

    private Context appContext;
    private int id;
    private String password;
    private Integer account;
    private ImageButton button;
    private Item item;
    private TextView itemName;

    public ShoppingCartController(Context context, int id, String password, Integer account,
                                  Item item, ImageButton button, TextView itemName) {
        this.appContext = context;
        this.id = id;
        this.password = password;
        this.account = account;
        this.button = button;
        this.item = item;
        this.itemName = itemName;
    }

    @Override
    public void onClick(View v) {

        ShoppingCartInterface currentCart = new UserStore().getCart();

        if (this.button.getTag() == "Add") {

            currentCart.addItem(this.item, 1);
            new UserStore().setCart(currentCart);

            (this.itemName).setText(item.getName() + "\n" + "$" + item.getPrice() + "\n"
                    + "x" +new UserStore().getCart().getItemQuantity(item));

        } else {

            currentCart.removeItem(this.item, 1);
            new UserStore().setCart(currentCart);

            if (new UserStore().getCart().getItemQuantity(item) > 0) {
                this.itemName.setText(item.getName() + "\n" + "$" + item.getPrice() + "\n"
                        + "x" +new UserStore().getCart().getItemQuantity(item));
            } else {
                this.itemName.setText(item.getName() + "\n" + "$" + item.getPrice());
            }

        }

        BigDecimal currentSubTotal = new BigDecimal(0);
        int currentTotalItems = 0;

        List<Item> allItems = new UserStore().getCart().getItems();

        for (Item item: allItems) {
            currentTotalItems += new UserStore().getCart().getItemQuantity(item);
            BigDecimal itemPrice = item.getPrice();
            currentSubTotal = currentSubTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
        }

        TextView total = ((Activity) this.appContext).findViewById(R.id.total_items);
        total.setText((this.appContext).getResources().getString(R.string.total_items) +
                ": " + currentTotalItems);

        TextView subtotal = ((Activity) this.appContext).findViewById(R.id.subtotal);
        subtotal.setText("$" + currentSubTotal);

        /* else {

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
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ItemSetException e) {
                e.printStackTrace();
            }

            switch (v.getId()) {

                case R.id.add_fishing_rod:
                    currentCart.addItem(fishingRod, 1);

                    Log.d("Checking fishing rods from currentCart in shopping cart",
                            "" + currentCart.getItemQuantity(fishingRod));

                    TextView fishingRodTotal =
                            ((Activity) this.appContext).findViewById(R.id.fishing_rod_name);
                    fishingRodTotal.setText(((Activity) this.appContext).getResources().getString(R
                            .string.fishing_rod_purchase) +"\n" + "$" + fishingRod.getPrice() + "\n" +
                            "x" + currentCart.getItemQuantity(fishingRod));

                    new UserStore().setCart(currentCart);
                    Log.d("Added one fishing rod in cart",
                            "" + new UserStore().getCart().getItemQuantity(fishingRod));
                    break;
                case R.id.remove_fishing_rod:
                    currentCart.removeItem(fishingRod, 1);

                    Log.d("Checking fishing rods from currentCart in shopping cart",
                            "" + currentCart.getItemQuantity(fishingRod));

                    if (currentCart.getItemQuantity(fishingRod) > 0) {
                        fishingRodTotal =
                                ((Activity) this.appContext).findViewById(R.id.fishing_rod_name);
                        fishingRodTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.fishing_rod_purchase) +"\n" + "$" + fishingRod.getPrice() + "\n" + "x" + currentCart.getItemQuantity(fishingRod));
                    } else {
                        fishingRodTotal =
                                ((Activity) this.appContext).findViewById(R.id.fishing_rod_name);
                        fishingRodTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.fishing_rod_purchase) + "\n" + "$" + fishingRod.getPrice());
                    }

                    new UserStore().setCart(currentCart);
                    Log.d("Added one fishing rod in cart",
                            "" + new UserStore().getCart().getItemQuantity(fishingRod));
                    break;
                case R.id.add_hockey_stick:
                    currentCart.addItem(hockeyStick, 1);

                    TextView hockeyStickTotal =
                            ((Activity) this.appContext).findViewById(R.id.hockey_stick_name);
                    hockeyStickTotal.setText(((Activity) this.appContext).getResources()
                            .getString(R.string.hockey_stick_purchase)
                            + "\n" + "$" + hockeyStick.getPrice()
                            + "\n" + "x" + currentCart.getItemQuantity(hockeyStick));

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.remove_hockey_stick:
                    currentCart.removeItem(hockeyStick, 1);

                    if (currentCart.getItemQuantity(hockeyStick) > 0) {
                        hockeyStickTotal =
                                ((Activity) this.appContext).findViewById(R.id.hockey_stick_name);
                        hockeyStickTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.hockey_stick_purchase)
                                + "\n" + "$" + hockeyStick.getPrice()
                                + "\n" + "x" + currentCart.getItemQuantity(hockeyStick));
                    } else {
                        hockeyStickTotal =
                                ((Activity) this.appContext).findViewById(R.id.hockey_stick_name);
                        hockeyStickTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.hockey_stick_purchase) + "\n" + "$" + hockeyStick.getPrice());
                    }

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_skates:
                    currentCart.addItem(skates, 1);

                    TextView skatesTotal =
                            ((Activity) this.appContext).findViewById(R.id.skates_name);
                    skatesTotal.setText(((Activity) this.appContext).getResources()
                            .getString(R.string.skates_purchase)
                            + "\n" + "$" + skates.getPrice()
                            + "\n" + "x" + currentCart.getItemQuantity(skates));

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.remove_skates:
                    currentCart.removeItem(skates, 1);

                    if (currentCart.getItemQuantity(skates) > 0) {
                        skatesTotal =
                                ((Activity) this.appContext).findViewById(R.id.skates_name);
                        skatesTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.skates_purchase)
                                + "\n" + "$" + skates.getPrice()
                                + "\n" + "x" + currentCart.getItemQuantity(skates));
                    } else {
                        skatesTotal =
                                ((Activity) this.appContext).findViewById(R.id.skates_name);
                        skatesTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.skates_purchase) + "\n" + "$" + skates.getPrice());
                    }

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_running_shoes:
                    currentCart.addItem(runningShoes, 1);

                    TextView runningShoesTotal =
                            ((Activity) this.appContext).findViewById(R.id.running_shoes_name);
                    runningShoesTotal.setText(((Activity) this.appContext).getResources()
                            .getString(R.string.running_shoes_purchase)
                            + "\n" + "$" + runningShoes.getPrice()
                            + "\n" + "x" + currentCart.getItemQuantity(runningShoes));

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.remove_running_shoes:
                    currentCart.removeItem(runningShoes, 1);

                    if (currentCart.getItemQuantity(runningShoes) > 0) {
                        runningShoesTotal =
                                ((Activity) this.appContext).findViewById(R.id.running_shoes_name);
                        runningShoesTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.running_shoes_purchase)
                                + "\n" + "$" + runningShoes.getPrice()
                                + "\n" + "x" + currentCart.getItemQuantity(runningShoes));
                    } else {
                        runningShoesTotal =
                                ((Activity) this.appContext).findViewById(R.id.running_shoes_name);
                        runningShoesTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.running_shoes_purchase) + "$" + "\n" + runningShoes.getPrice());
                    }

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.add_protein_bar:
                    currentCart.addItem(proteinBar, 1);

                    TextView proteinBarTotal =
                            ((Activity) this.appContext).findViewById(R.id.protein_bar_name);
                    proteinBarTotal.setText(((Activity) this.appContext).getResources()
                            .getString(R.string.protein_bar_purchase)
                            + "\n" + "$" + proteinBar.getPrice()
                            + "\n" + "x" + currentCart.getItemQuantity(proteinBar));

                    new UserStore().setCart(currentCart);
                    break;
                case R.id.remove_protein_bar:
                    currentCart.removeItem(proteinBar, 1);

                    if (currentCart.getItemQuantity(proteinBar) > 0) {
                        proteinBarTotal =
                                ((Activity) this.appContext).findViewById(R.id.protein_bar_name);
                        proteinBarTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.protein_bar_purchase)
                                + "\n" + "$" + proteinBar.getPrice()
                                + "\n" + "x" + currentCart.getItemQuantity(proteinBar));
                    } else {
                        proteinBarTotal =
                                ((Activity) this.appContext).findViewById(R.id.protein_bar_name);
                        proteinBarTotal.setText(((Activity) this.appContext).getResources()
                                .getString(R.string.protein_bar_purchase) + "\n" + "$" + proteinBar.getPrice());
                    }

                    new UserStore().setCart(currentCart);
                    break;
            }

            BigDecimal currentSubTotal = new BigDecimal(0);
            int currentTotalItems = 0;

            List<Item> allItems = new UserStore().getCart().getItems();

            for (Item item: allItems) {
                currentTotalItems += new UserStore().getCart().getItemQuantity(item);
                BigDecimal itemPrice = item.getPrice();
                currentSubTotal = currentSubTotal.add(itemPrice.multiply(new BigDecimal(new UserStore().getCart().getItemQuantity(item))));
            }

            TextView total = ((Activity) this.appContext).findViewById(R.id.total_items);
            total.setText((this.appContext).getResources().getString(R.string.total_items) +
                    ": " + currentTotalItems);

            TextView subtotal = ((Activity) this.appContext).findViewById(R.id.subtotal);
            subtotal.setText("$" + currentSubTotal);

        }*/
    }
}
