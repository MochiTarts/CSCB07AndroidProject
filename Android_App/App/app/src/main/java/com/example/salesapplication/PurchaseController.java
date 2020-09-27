package com.example.salesapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.DatabaseUpdateHelperException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.store.ShoppingCartQuantityManager;

import java.sql.SQLException;
import java.util.List;

class PurchaseController implements View.OnClickListener {

    private Context appContext;
    private int id;
    private String password;
    private Integer account;

    public PurchaseController(Context context, int id, String password, Integer account) {
        this.appContext = context;
        this.id = id;
        this.password = password;
        this.account = account;
    }

    @Override
    public void onClick(View v) {
        ShoppingCartInterface currentCart = new UserStore().getCart();
        ShoppingCartQuantityManager insertCart = (ShoppingCartQuantityManager) currentCart;

        try {

            Log.d("Account is", "" + this.account);

            if (this.account != 0) {
                Log.d("Items Total", "" + insertCart.getItems().size());
                DatabaseInsertAdapter.insertShoppingCart((ShoppingCartQuantityManager) currentCart,
                        this.account, this.id);
                DatabaseUpdateAdapter.updateAccountStatus(this.account, false);

                final AlertDialog.Builder alert = new AlertDialog.Builder(this.appContext, R.style.DialogeTheme);

                alert.setTitle("Will you donate to a charity today?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DonateCharity(true);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(alert.getContext(), LoginScreen.class);
                        i.putExtra("id", id);
                        i.putExtra("password", password);
                        i.putExtra("account", account);
                        (alert.getContext()).startActivity(i);
                    }
                });

                alert.setCancelable(false);
                alert.create();
                alert.show();

                currentCart.checkOut();
                Toast.makeText(this.appContext,
                        (this.appContext).getResources().getString(R.string.purchase_success),
                        Toast.LENGTH_LONG).show();

            } else {

                final AlertDialog.Builder alert = new AlertDialog.Builder(this.appContext, R.style.DialogeTheme);

                alert.setTitle("Will you donate to a charity today?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DonateCharity(false);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(alert.getContext(), UserStore.class);
                        i.putExtra("id", id);
                        i.putExtra("password", password);
                        i.putExtra("account", account);
                        (alert.getContext()).startActivity(i);
                    }
                });

                alert.setCancelable(false);
                alert.create();
                alert.show();

                currentCart.checkOut();
                Toast.makeText(this.appContext,
                        (this.appContext).getResources().getString(R.string.purchase_success),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.appContext,
                    (this.appContext).getResources().getString(R.string.purchase_failed),
                    Toast.LENGTH_LONG);
        }
    }

    private void DonateCharity(final boolean hasAccount) {

        final String[] charities = {"Sick Kids", "The Salvation Army", "Habitat for Humanity", "Heart " +
                "and Stroke", "Make a Wish Foundation"};

        final AlertDialog.Builder alert = new AlertDialog.Builder(this.appContext,
                R.style.DialogeTheme);

        alert.setTitle("Choose a charity to donate to");
        int checkedItem = 0;

        alert.setSingleChoiceItems(charities, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DonateAmount(hasAccount);
            }
        });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (hasAccount) {
                    Intent i = new Intent(alert.getContext(), UserStore.class);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    i.putExtra("account", account);
                    (alert.getContext()).startActivity(i);
                } else {
                    Intent i = new Intent(alert.getContext(), LoginScreen.class);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    i.putExtra("account", account);
                    (alert.getContext()).startActivity(i);
                }
            }
        });

        alert.setCancelable(false);
        alert.create();
        alert.show();

    }

    private void DonateAmount(final boolean hasAccount) {

        final EditText amount = new EditText(this.appContext);
        amount.setInputType(InputType.TYPE_CLASS_NUMBER);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this.appContext,
                R.style.DialogeTheme);

        alert.setTitle("Please enter the amount you want to donate");
        alert.setView(amount);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (hasAccount) {
                    Intent i = new Intent(alert.getContext(), UserStore.class);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    i.putExtra("account", account);
                    (alert.getContext()).startActivity(i);
                } else {
                    Intent i = new Intent(alert.getContext(), LoginScreen.class);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    i.putExtra("account", account);
                    (alert.getContext()).startActivity(i);
                }
            }
        });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DonateCharity(hasAccount);
            }
        });

        alert.setCancelable(false);
        alert.create();
        alert.show();

    }
}
