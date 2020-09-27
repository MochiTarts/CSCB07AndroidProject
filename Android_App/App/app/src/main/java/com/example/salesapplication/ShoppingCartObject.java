package com.example.salesapplication;

import java.io.Serializable;

public class ShoppingCartObject implements Serializable {

    private int total_items;

    private static ShoppingCartObject ourInstance;

    static ShoppingCartObject getInstance() {
        if (ourInstance == null) {
            ourInstance = new ShoppingCartObject();
        }
        return ourInstance;
    }

    private ShoppingCartObject() {}

    public void setTotal_items(int num) {
        this.total_items = num;
    }

    public void incrementTotal_items() {
        this.total_items += 1;
    }

    public void removeTotal_items() {
        if (this.total_items - 1 >= 0) {
            this.total_items -= 1;
        } else {
            this.total_items = 0;
        }
    }

    public int getTotal_items() {
        return total_items;
    }


}
