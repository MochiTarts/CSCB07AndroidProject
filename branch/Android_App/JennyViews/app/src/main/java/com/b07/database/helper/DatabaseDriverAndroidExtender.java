package com.b07.database.helper;

import com.b07.database.DatabaseDriverAndroid;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;

public class DatabaseDriverAndroidExtender extends DatabaseDriverAndroid {
    public DatabaseDriverAndroidExtender(Context context) {
        super(context);
    }

    protected long insertRole(String role) {
        return super.insertRole(role);
    }

    protected long insertNewUser(String name, int age, String address, String password) {
        return super.insertNewUser(name, age, address, password);
    }

    protected long insertUserRole(int userId, int roleId) {
        return super.insertUserRole(userId, roleId);
    }

    protected long insertItem(String name, BigDecimal price) {
        return super.insertItem(name, price);
    }

    protected long insertInventory(int itemId, int quantity) {
        return super.insertInventory(itemId, quantity);
    }

    protected long insertSale(int userId, BigDecimal totalPrice) {
        return super.insertSale(userId, totalPrice);
    }

    protected long insertItemizedSale(int saleId, int itemId, int quantity) {
        return super.insertItemizedSale(saleId, itemId, quantity);
    }

    protected long insertAccount(int userId, boolean active) {
        return super.insertAccount(userId, active);
    }

    protected long insertAccountLine(int accountId, int itemId, int quantity) {
        return super.insertAccountLine(accountId, itemId, quantity);
    }

    //SELECT METHODS
    protected Cursor getRoles() {
        return super.getRoles();
    }

    protected String getRole(int id) {
        return super.getRole(id);
    }

    protected int getUserRole(int userId) {
        return super.getUserRole(userId);
    }


    protected Cursor getUsersByRole(int roleId) {
        return super.getUsersByRole(roleId);
    }

    protected Cursor getUsersDetails() {
        return super.getUsersDetails();
    }

    protected Cursor getUserDetails(int userId) {
        return super.getUserDetails(userId);
    }

    protected String getPassword(int userId) {
        return super.getPassword(userId);
    }

    protected Cursor getAllItems() {
        return super.getAllItems();
    }

    protected  Cursor getItem(int itemId) {
        return super.getItem(itemId);
    }

    protected Cursor getInventory() {
        return super.getInventory();
    }

    protected int getInventoryQuantity(int itemId) {
        return super.getInventoryQuantity(itemId);
    }

    protected Cursor getSales() {
        return super.getSales();
    }

    protected Cursor getSaleById(int saleId) {
        return super.getSaleById(saleId);
    }

    protected Cursor getSalesToUser(int userId) {
        return super.getSalesToUser(userId);
    }

    protected Cursor getItemizedSales() {
        return super.getItemizedSales();
    }

    protected Cursor getItemizedSaleById(int saleId) {
        return super.getItemizedSaleById(saleId);
    }

    protected Cursor getUserAccounts(int userId) {
        return super.getUserAccounts(userId);
    }

    protected Cursor getAccountDetails(int accountId) {
        return super.getAccountDetails(accountId);
    }

    protected Cursor getUserActiveAccounts(int userId) {
        return super.getUserActiveAccounts(userId);
    }

    protected Cursor getUserInactiveAccounts(int userId) {
        return super.getUserInactiveAccounts(userId);
    }

    //UPDATE METHODS

    protected boolean updateRoleName(String name, int id) {
        return super.updateRoleName(name, id);
    }

    protected boolean updateUserName(String name, int id) {
        return super.updateUserName(name, id);
    }

    protected boolean updateUserAge(int age, int id) {
        return super.updateUserAge(age, id);
    }

    protected boolean updateUserAddress(String address, int id) {
        return super.updateUserAddress(address, id);
    }

    protected boolean updateUserRole(int roleId, int id) {
        return super.updateUserRole(roleId, id);
    }

    protected boolean updateItemName(String name, int id) {
        return super.updateItemName(name, id);
    }

    protected boolean updateItemPrice(BigDecimal price, int id) {
        return super.updateItemPrice(price, id);
    }

    protected boolean updateInventoryQuantity(int quantity, int id) {
        return super.updateInventoryQuantity(quantity, id);
    }

    protected boolean updateAccountStatus(int accountId, boolean active) {
        return super.updateAccountStatus(accountId, active);
    }
}
