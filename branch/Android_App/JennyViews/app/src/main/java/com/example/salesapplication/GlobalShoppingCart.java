package com.example.salesapplication;

import android.app.Application;

import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.Unauthenticated;
import com.b07.store.ShoppingCart;
import com.b07.users.Customer;
import com.b07.users.User;

import java.sql.SQLException;

public class GlobalShoppingCart extends Application {

    private static ShoppingCart customerCart;

    public void setShoppingCart(User customer, String password) {
        User thisCustomer = customer;
        try {
            thisCustomer.authenticate(password);
            customerCart = new ShoppingCart((Customer) thisCustomer);
        } catch (SQLException | Unauthenticated | DatabaseSelectHelperException e) {
            e.printStackTrace();
        }
    }

    public ShoppingCart getShoppingCart() {
        return customerCart;
    }

}
