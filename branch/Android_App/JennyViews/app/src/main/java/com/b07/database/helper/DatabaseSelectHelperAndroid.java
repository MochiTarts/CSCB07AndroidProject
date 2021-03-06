package com.b07.database.helper;

import android.content.Context;
import android.database.Cursor;

import com.b07.accounts.Account;
import com.b07.accounts.AccountImpl;
import com.b07.builders.ItemBuilderImpl;
import com.b07.builders.UserBuilderImpl;
import com.b07.database.adapter.DatabaseSelectInterface;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseIllegalGet;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.ItemSetException;
import com.b07.exceptions.Unauthenticated;
import com.b07.exceptions.UserSetException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.StoreInventory;
import com.b07.inventory.StoreItem;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.Customer;
import com.b07.users.Roles;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseSelectHelperAndroid implements DatabaseSelectInterface {
    private DatabaseDriverAndroidExtender driver;

    public DatabaseSelectHelperAndroid(Context context) {
        driver = new DatabaseDriverAndroidExtender(context);
    }

    /**
     * Gets a list of all of the role ids in the database.
     * @return a list of the role ids
     * @throws SQLException if a generic issue occurs with the database.
     */
    @Override
    public List<Integer> getRoleIds() {
        List<Integer> ids = new ArrayList<>();
        Cursor results = driver.getRoles();
        while (results.moveToNext()) {
            ids.add(results.getInt(results.getColumnIndex("ID")));
        }
        results.close();
        return ids;
    }

    /**
     * Gets the name of a role from the database.
     * @param roleId the id of the role, must be in the database.
     * @return the name of the role associated with roleId.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws DatabaseSelectHelperException If the input is invalid.
     */
    @Override
    public String getRoleName(int roleId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkResults = driver.getRoles();
            List<Integer> roleIds = new ArrayList<Integer>();
            while (checkResults.moveToNext()) {
                roleIds.add(checkResults.getInt(checkResults.getColumnIndex("ID")));
            }
            if (!roleIds.contains(roleId)) {
                checkResults.close();
                throw new DatabaseIllegalGet("This roleId does not exist");
            }
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        String role = driver.getRole(roleId);
        return role;
    }

    /**
     * Returns the role id associated with the given user.
     * @param userId a user in the database.
     * @return the role id of the user.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws DatabaseSelectHelperException if the input is invalid.
     */
    @Override
    public int getUserRoleId(int userId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkTable = driver.getRoles();
            if (!checkTable.moveToNext()) {
                checkTable.close();
                throw new DatabaseIllegalGet(
                        "The USERROLE table is currently empty. Please insert a user role");
            }

            Cursor checkResults = driver.getUserDetails(userId);
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalGet("This userId does not exist");
            }
            checkTable.close();
            checkResults.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        int roleId = driver.getUserRole(userId);
        return roleId;
    }

    /**
     * Returns the ids of all of the users associated with a given role.
     * @param roleId the id of the role, must me in the Roles enum.
     * @return a list containing the users associated with the given role.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws DatabaseSelectHelperException if the input is invalid.
     */
    @Override
    public List<Integer> getUsersByRole(int roleId) throws SQLException, DatabaseSelectHelperException {
        List<Integer> userIds = new ArrayList<>();
        try {
            Cursor checkTable = driver.getRoles();
            if (!checkTable.moveToNext()) {
                checkTable.close();
                throw new DatabaseIllegalGet(
                        "The USERROLE table is currently empty. Please insert a user role");
            }

            Cursor checkResults = driver.getRoles();
            List<Integer> roleIds = new ArrayList<Integer>();
            while (checkResults.moveToNext()) {
                roleIds.add(checkResults.getInt(checkResults.getColumnIndex("ID")));
            }
            if (!roleIds.contains(roleId)) {
                checkResults.close();
                throw new DatabaseIllegalGet("This roleId does not exist");
            }
            checkTable.close();
            checkResults.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        Cursor results = driver.getUsersByRole(roleId);
        while (results.moveToNext()) {
            userIds.add(results.getInt(results.getColumnIndex("USERID")));
        }
        results.close();
        return userIds;
    }

    /**
     * Returns a list of all the Users in the database.
     * @return a list containing User objects representing every user in the database.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws BuildTypeException if one of the Users has an invalid role.
     * @throws UserSetException if one of the users has an invalid field.
     * @throws DatabaseSelectHelperException if the Users cannot be retrieved from the database.
     */
    @Override
    public List<User> getUsersDetails() throws SQLException, BuildTypeException, UserSetException, DatabaseSelectHelperException {
        List<User> users = new ArrayList<>();
        Cursor results = driver.getUsersDetails();
        int id;
        int age;
        String name;
        String address;
        while (results.moveToNext()) {
            id = results.getInt(results.getColumnIndex("ID"));
            name = results.getString(results.getColumnIndex("NAME"));
            age = results.getInt(results.getColumnIndex("AGE"));
            address = results.getString(results.getColumnIndex("ADDRESS"));
            users.add(makeUser(id, name, age, address));
        }
        results.close();
        return users;
    }

    /**
     * Returns the User associated with the given userId.
     *
     * @param userId the id of the User, must be in the table.
     * @return a User object representing the user in the database.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws BuildTypeException if the User has an invalid role.
     * @throws UserSetException if the User has an invalid field.
     * @throws DatabaseSelectHelperException if the User cannot be found.
     */
    @Override
    public User getUserDetails(int userId) throws SQLException, DatabaseSelectHelperException, BuildTypeException, UserSetException {
        Cursor checkResults = driver.getUserDetails(userId);
        try {
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalGet("The entered userId does not exist");
            }
            checkResults.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        int id;
        int age;
        String name;
        id = checkResults.getInt(checkResults.getColumnIndex("ID"));
        name = checkResults.getString(checkResults.getColumnIndex("NAME"));
        age = checkResults.getInt(checkResults.getColumnIndex("AGE"));
        String address;
        address = checkResults.getString(checkResults.getColumnIndex("ADDRESS"));
        checkResults.close();
        return makeUser(id, name, age, address);
    }

    /**
     * Returns the hashed password in the database associated with the given user.
     * @param userId the id of a user in the database.
     * @return the hashed password of the user.
     * @throws SQLException if a generic issue occurs with the database.
     * @throws DatabaseSelectHelperException if the user cannot be found.
     */
    @Override
    public String getPassword(int userId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkResults = driver.getUserDetails(userId);
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalGet("The entered userId does not exist");
            }
            checkResults.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        String password = driver.getPassword(userId);
        return password;
    }

    @Override
    public List<Item> getAllItems() throws SQLException, ItemSetException {
        Cursor results = driver.getAllItems();
        List<Item> items = new ArrayList<>();
        while (results.moveToNext()) {
            Item item = new StoreItem();
            item.setId(results.getInt(results.getColumnIndex("ID")));
            item.setName(results.getString(results.getColumnIndex("NAME")));
            item.setPrice(new BigDecimal(results.getString(results
                    .getColumnIndex("PRICE"))));
            items.add(item);
        }
        results.close();
        return items;
    }

    @Override
    public Item getItem(int itemId) throws SQLException, DatabaseSelectHelperException, ItemSetException {
        try {
            Cursor checkResults = driver.getItem(itemId);
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalGet("The entered itemId does not exist");
            }
            checkResults.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        Cursor results = driver.getItem(itemId);
        Item item = new ItemBuilderImpl().build();
        while (results.moveToNext()) {
            item = new ItemBuilderImpl().id(results
                    .getInt(results.getColumnIndex("ID")))
                    .name(results.getString(results.getColumnIndex("NAME")))
                    .price(new BigDecimal(results
                            .getString(results.getColumnIndex("PRICE")))).build();
        }
        results.close();
        return item;
    }

    @Override
    public Inventory getInventory() throws SQLException, DatabaseSelectHelperException, ItemSetException {
        Cursor results = driver.getInventory();
        int itemId;
        int quantity;
        try {
            if (!results.moveToNext()) {
                throw new DatabaseSelectHelperException("Couldn't get rows");
            }
        } catch (Exception e) {
            results.close();
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        results.close();

        Cursor inventoryResults = driver.getInventory();
        Inventory inventory = new StoreInventory();
        inventory.setItemMap(new HashMap<Item, Integer>());
        while (inventoryResults.moveToNext()) {
            itemId = inventoryResults.getInt(inventoryResults.getColumnIndex("ITEMID"));
            quantity = inventoryResults.getInt(inventoryResults
                    .getColumnIndex("QUANTITY"));
            Item item = getItem(itemId);
            inventory.updateMap(item, quantity);
        }

        inventoryResults.close();
        return inventory;
    }

    @Override
    public int getInventoryQuantity(int itemId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkItemId = driver.getItem(itemId);
            if (!checkItemId.moveToNext()) {
                checkItemId.close();
                throw new DatabaseIllegalGet("The entered itemId does not exist in database");
            }
            checkItemId.close();
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
        int quantity = driver.getInventoryQuantity(itemId);
        return quantity;
    }

    @Override
    public SalesLog getSales() throws SQLException, DatabaseSelectHelperException {
        Cursor results = driver.getSales();
        SalesLog log = new SalesLogImpl();
        while (results.moveToNext()) {
            Sale sale = new SaleImpl();
            sale.setId(results.getInt(results.getColumnIndex("ID")));
            sale.setTotalPrice(new BigDecimal(results
                    .getString(results.getColumnIndex("TOTALPRICE"))));
            try {
                sale.setUser(getUserDetails(results
                        .getInt(results.getColumnIndex("USERID"))));
            } catch (SecurityException | IllegalArgumentException
                    | DatabaseSelectHelperException | BuildTypeException
                    | UserSetException e) {
                throw new DatabaseSelectHelperException("Could not get sales.");
            }
            log.addSale(sale);
        }
        results.close();
        return log;
    }

    @Override
    public Sale getSaleById(int saleId) throws SQLException, DatabaseSelectHelperException, BuildTypeException, UserSetException {
        try {
            Cursor checkSaleId = driver.getSaleById(saleId);
            if (!checkSaleId.moveToNext()) {
                checkSaleId.close();
                throw new DatabaseIllegalGet("The entered saleId does not exist in database");
            }
            checkSaleId.close();
        } catch (Exception e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }

        Cursor results = driver.getSaleById(saleId);
        Sale sale = new SaleImpl();
        while (results.moveToNext()) {
            sale.setId(results.getInt(results.getColumnIndex("ID")));
            User user = getUserDetails(results
                    .getInt(results.getColumnIndex("USERID")));
            sale.setUser(user);
            sale.setTotalPrice(new BigDecimal(results
                    .getString(results.getColumnIndex("TOTALPRICE"))));
        }
        results.close();
        return sale;
    }

    @Override
    public List<Sale> getSalesToUser(int userId) throws SQLException, DatabaseSelectHelperException, BuildTypeException, UserSetException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalGet("The entered userId does not exist in database");
            }
            checkUserId.close();
        } catch (Exception e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }

        Cursor results = driver.getSalesToUser(userId);
        List<Sale> sales = new ArrayList<>();
        while (results.moveToNext()) {
            Sale sale = new SaleImpl();
            sale.setId(results.getInt(results.getColumnIndex("ID")));
            User user = getUserDetails(results
                    .getInt(results.getColumnIndex("USERID")));
            sale.setUser(user);
            sale.setTotalPrice(new BigDecimal(results
                    .getString(results.getColumnIndex("TOTALPRICE"))));
            sales.add(sale);
        }
        results.close();
        return sales;
    }

    @Override
    public Sale getItemizedSaleById(int saleId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor results = driver.getItemizedSaleById(saleId);
            if (!results.moveToNext()) {
                results.close();
                throw new DatabaseIllegalGet("The entered saleId does not exist");
            }
            results.close();
        } catch (Exception e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }

        Cursor results = driver.getItemizedSaleById(saleId);
        Sale sale = new SaleImpl();
        HashMap<Item, Integer> items = new HashMap<Item, Integer>();
        try {
            while (results.moveToNext()) {
                sale.setId(results.getInt(results.getColumnIndex("SALEID")));
                items.put(this.getItem(results.getInt(results
                                .getColumnIndex("ITEMID"))),
                        new Integer(results.getInt(results
                                .getColumnIndex("QUANTITY"))));
            }
        } catch (ItemSetException e) {
            throw new DatabaseSelectHelperException("Could not retrieve all items from the table.");
        }
        sale.setItemMap(items);
        results.close();
        return sale;
    }

    @Override
    public SalesLog getItemizedSales() throws SQLException, DatabaseSelectHelperException {
        Cursor results = driver.getItemizedSales();
        int lastSaleId = -1;
        SalesLog log = new SalesLogImpl();
        Sale sale = new SaleImpl();
        HashMap<Item, Integer> items = new HashMap<Item, Integer>();

        if (results.moveToNext()) {
            int saleId = results.getInt(results.getColumnIndex("SALEID"));
            sale.setId(saleId);
            try {
                items.put(this.getItem(results.getInt(results
                        .getColumnIndex("ITEMID"))),
                        results.getInt(results.getColumnIndex("QUANTITY")));
            } catch (ItemSetException e) {
                throw new DatabaseSelectHelperException(e.getMessage());
            }
            lastSaleId = saleId;
        }

        while (results.moveToNext()) {
            int saleId = results.getInt(results.getColumnIndex("SALEID"));
            try {
                if (lastSaleId != saleId) {
                    sale.setItemMap(items);
                    log.addSale(sale);
                    sale = new SaleImpl();
                    items = new HashMap<Item, Integer>();
                    sale.setId(saleId);
                    items.put(this.getItem(results.getInt(results
                                    .getColumnIndex("ITEMID"))),
                            results.getInt(results.getColumnIndex("QUANTITY")));
                    lastSaleId = saleId;
                } else {
                    items.put(this.getItem(results.getInt(results
                                    .getColumnIndex("ITEMID"))),
                            results.getInt(results.getColumnIndex("QUANTITY")));
                }
            } catch (ItemSetException | DatabaseSelectHelperException e) {
                throw new DatabaseSelectHelperException(e.getMessage());
            }
        }

        if (items.size() > 0) {
            sale.setItemMap(items);
            log.addSale(sale);
        }
        results.close();
        return log;
    }

    @Override
    public SalesLog getCompleteSales() throws DatabaseSelectHelperException {
        try {
            SalesLog log = this.getItemizedSales();

            for (Sale sale : log.getSales()) {
                if (sale.getId() > 0) {
                    Sale fromSaleTable = this.getSaleById(sale.getId());
                    sale.setTotalPrice(fromSaleTable.getTotalPrice());
                    sale.setUser(fromSaleTable.getUser());
                }
            }

            return log;
        } catch (UserSetException | SQLException | BuildTypeException e) {
            throw new DatabaseSelectHelperException("Could not retrieve sales.");
        }
    }

    /**
     * Checks if userId exists in database and if so, returns a list of accounts associated with the
     * given user.
     *
     * @param userId value whose existence will be checked if exists in database
     * @return a list of Integer account ids that are associated with the user with userId
     * @throws SQLException if something goes wrong with the database
     * @throws DatabaseSelectHelperException if the userId does not exist
     */
    @Override
    public List<Integer> getUserAccounts(int userId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalGet("The entered userId does not exist in the database");
            }

            Cursor checkAccountExists = driver.getUserAccounts(userId);
            if (!checkAccountExists.moveToNext()) {
                checkAccountExists.close();
                throw new DatabaseIllegalGet("The user associated to the entered userId has no account");
            }

            checkUserId.close();
            checkAccountExists.close();
        } catch (Exception e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }

        Cursor results = driver.getUserAccounts(userId);
        List<Integer> userAccountIds = new ArrayList<>();
        while (results.moveToNext()) {
            userAccountIds.add(results.getInt(results.getColumnIndex("ID")));
        }

        results.close();

        return userAccountIds;
    }

    /**
     * Checks if accountId exists in the database and if so, returns all items and their quantities
     * associated with given account in a HashMap. Where the key is the item id and the value is the
     * quantity
     *
     * @param accountId value whose existence in database is to be checked
     * @return HashMap where the key is the item id and the value is the quantity
     * @throws SQLException if something goes wrong with the database
     * @throws DatabaseSelectHelperException if the accountId does not exist
     */
    @Override
    public HashMap<Integer, Integer> getAccountDetails(int accountId) throws SQLException, DatabaseSelectHelperException {
        try {
            Cursor checkAccountId = driver.getAccountDetails(accountId);
            if (!checkAccountId.moveToNext()) {
                throw new DatabaseIllegalGet("The entered accountId does not exist in database");
            }
        } catch (Exception e) {
            throw new DatabaseSelectHelperException("Failed to get details for the account entered");
        }

        Cursor results = driver.getAccountDetails(accountId);
        HashMap<Integer, Integer> userPreviousCart = new HashMap<>();
        while (results.moveToNext()) {
            userPreviousCart.put(results.getInt(results.getColumnIndex("ITEMID")),
                    results.getInt(results.getColumnIndex("QUANTITY")));
        }

        results.close();

        return userPreviousCart;
    }

    @Override
    public ShoppingCartInterface getShoppingCart(Customer user, int accountId) throws SQLException, DatabaseSelectHelperException, Unauthenticated {
        if (!user.getAuthenticated()) {
            throw new Unauthenticated("The User must be "
                    + "authenticated to retrieve their cart.");
        }

        List<Integer> accounts = getUserAccounts(user.getId());

        boolean hasAccount = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).intValue() == accountId) {
                hasAccount = true;
            }
        }

        if (!hasAccount) {
            throw new DatabaseSelectHelperException("This user does not own"
                    + " an account with that ID.");
        }

        HashMap<Integer, Integer> accDetails = getAccountDetails(accountId);

        if (accDetails.size() <= 0) {
            throw new DatabaseSelectHelperException("This user does not have a cart to retrieve.");
        }

        try {
            ShoppingCartInterface shoppingCart = new ShoppingCart(user, true);
            for (Integer i : accDetails.keySet()) {
                if (accDetails.get(i).intValue() > 0) {
                    shoppingCart.addItem(getItem(i), accDetails.get(i));
                }
            }

            return shoppingCart;
        } catch (Unauthenticated e) {
            throw new Unauthenticated("The User must be authenticated to retrieve their cart.");
        } catch (ItemSetException e) {
            throw new DatabaseSelectHelperException("The cart could not be retrieved.");
        }
    }

    @Override
    public List<Integer> getUserActiveAccounts(int userId) throws DatabaseSelectHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalGet("The entered userId "
                        + "does not exist in the database");
            }

            Cursor activeAccounts = driver.getUserActiveAccounts(userId);
            List<Integer> accountIds = new ArrayList<Integer>();

            while (activeAccounts.moveToNext()) {
                accountIds.add(activeAccounts.getInt(activeAccounts
                        .getColumnIndex("ID")));
            }

            return accountIds;
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getUserInactiveAccounts(int userId) throws DatabaseSelectHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalGet("The entered userId "
                        + "does not exist in the database");
            }

            Cursor inactiveAccounts = driver.getUserInactiveAccounts(userId);
            List<Integer> accountIds = new ArrayList<Integer>();

            while (inactiveAccounts.moveToNext()) {
                accountIds.add(inactiveAccounts.getInt(inactiveAccounts
                        .getColumnIndex("ID")));
            }

            checkUserId.close();
            inactiveAccounts.close();
            return accountIds;
        } catch (DatabaseIllegalGet e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
    }

    @Override
    public List<Account> getAccounts() throws DatabaseSelectHelperException {
        try {
            List<User> users = this.getUsersDetails();
            List<Account> accounts = new ArrayList<Account>();
            for (User user: users) {
                List<Integer> userActiveAccounts = this
                        .getUserActiveAccounts(user.getId());

                for (Integer i : userActiveAccounts) {
                    Account currentAccount = new AccountImpl();
                    currentAccount.setAccountId(i.intValue());
                    currentAccount.setUserId(user.getId());
                    currentAccount.setActive(true);
                    try {
                        currentAccount.setItemMap(this.getAccountDetails(i.intValue()));
                    } catch (DatabaseSelectHelperException e) {
                        currentAccount.setItemMap(null);
                    }
                    accounts.add(currentAccount);
                }

                List<Integer> userInactiveAccounts = this
                        .getUserInactiveAccounts(user.getId());

                for (Integer i : userInactiveAccounts) {
                    Account currentAccount = new AccountImpl();
                    currentAccount.setAccountId(i.intValue());
                    currentAccount.setUserId(user.getId());
                    currentAccount.setActive(false);
                    try {
                        currentAccount.setItemMap(this.getAccountDetails(i.intValue()));
                    } catch (DatabaseSelectHelperException e) {
                        currentAccount.setItemMap(new HashMap<Integer, Integer>());
                    }
                    accounts.add(currentAccount);
                }
            }

            return accounts;
        } catch (SQLException | BuildTypeException | UserSetException e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
    }

    @Override
    public HashMap<Integer, String> getPasswords() throws DatabaseSelectHelperException {
        List<User> users;
        try {
            users = this.getUsersDetails();
            HashMap<Integer, String> passwords = new HashMap<Integer, String>();

            for (User user : users) {
                passwords.put(new Integer(user.getId()), this.getPassword(user.getId()));
            }

            return passwords;
        } catch (SQLException | BuildTypeException | UserSetException e) {
            throw new DatabaseSelectHelperException(e.getMessage());
        }
    }

    private User makeUser(int userId, String name, int age, String address)
            throws SQLException,
            BuildTypeException, UserSetException,
            DatabaseSelectHelperException {
        int roleId = driver.getUserRole(userId);
        String roleName = getRoleName(roleId);
        if (Roles.valueOf(roleName).equals(Roles.ADMIN)) {
            User admin =
                    new UserBuilderImpl(Roles.ADMIN, false)
                            .id(userId)
                            .name(name)
                            .age(age)
                            .address(address)
                            .build();
            return admin;
        } else if (Roles.valueOf(roleName).equals(Roles.CUSTOMER)) {
            User customer =
                    new UserBuilderImpl(Roles.CUSTOMER, false)
                            .id(userId)
                            .name(name)
                            .age(age)
                            .address(address)
                            .build();
            return customer;
        } else {
            User employee =
                    new UserBuilderImpl(Roles.EMPLOYEE, false)
                            .id(userId)
                            .name(name)
                            .age(age)
                            .address(address)
                            .build();
            return employee;
        }
    }
}
