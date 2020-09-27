package com.b07.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.adapter.DatabaseInsertInterface;
import com.b07.exceptions.DatabaseIllegalGet;
import com.b07.exceptions.DatabaseIllegalInsert;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCartQuantityManager;
import com.b07.users.Roles;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseInsertHelperAndroid implements DatabaseInsertInterface {
    private DatabaseDriverAndroidExtender driver;
    private Context appContext;

    public DatabaseInsertHelperAndroid(Context context) {
        this.appContext = context;
        driver = new DatabaseDriverAndroidExtender(context);
    }

    /**
     * Inserts a new role into the database, assigning it an auto-incrementing Id.
     * @param name The name of the role. Must be from the Roles enum.
     * @return the id of the new role.
     * @throws DatabaseInsertException if the role cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException if the name is not valid or already in the database.
     * @throws DatabaseSelectHelperException if the roles table cannot be accessed.
     */
    @Override
    public int insertRole(String name) throws DatabaseInsertException,
            SQLException, DatabaseInsertHelperException, DatabaseSelectHelperException {
        try {
            List<String> validRoles = new ArrayList<String>();

            for (Roles role : Roles.values()) {
                validRoles.add(role.toString());
            }

            if (!validRoles.contains(name)) {
                throw new DatabaseIllegalInsert("Not a valid role");
            }

            List<String> allRoles = new ArrayList<>();
            List<Integer> roleIdsList = (new DatabaseSelectHelperAndroid(appContext)).getRoleIds();
            for (int i = 0; i < roleIdsList.size(); i++) {
                allRoles.add((new DatabaseSelectHelperAndroid(appContext))
                        .getRoleName(roleIdsList.get(i)));
            }
            if (allRoles.contains(name)) {
                throw new DatabaseIllegalInsert("Already in database");
            }

        } catch (DatabaseIllegalInsert e) {
            System.out.println(e.getMessage());
            throw new DatabaseInsertHelperException("Failed to insert role");
        }
        long roleId = driver.insertRole(name);
        return Math.toIntExact(roleId);
    }

    /**
     * Inserts a User into the database, assigning it an auto-incrementing id.
     * @param name The name of the user.
     * @param age The age of the user, must be greater than zero.
     * @param address The address of the user, can be 100 characters maximum.
     * @param password The password of the user.
     * @return the new userId
     * @throws DatabaseInsertException if the role cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertNewUser(String name, int age, String address, String password)
        throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
        try {
            if (name == null || name.trim().equals("")) {
                throw new DatabaseIllegalInsert("Name cannot be empty or null");
            }
            if (address == null || address.trim().equals("")) {
                throw new DatabaseIllegalInsert("Address cannot be empty or null");
            }
            if (address.trim().length() > 100) {
                throw new DatabaseIllegalInsert("Address cannot exceed 100 characters");
            }
            if (password == null || password.trim().equals("")) {
                throw new DatabaseIllegalInsert("Password cannot be empty or null");
            }
            if (age < 0) {
                throw new DatabaseIllegalInsert("Age must be 0 or greater");
            }
        } catch (DatabaseIllegalInsert e) {
            System.out.println(e.getMessage());
            throw new DatabaseInsertHelperException("Failed to insert new user");
        }

        long userId = driver.insertNewUser(name, age, address, password);
        return Math.toIntExact(userId);
    }

    /**
     * Inserts a User into the database, assigning it an auto-incrementing id, with the option
     * to specify that the password is already hashed.
     *
     * @param name The name of the user.
     * @param age The age of the user, must be greater than zero.
     * @param address The address of the user, can be 100 characters maximum.
     * @param password The password of the user.
     * @param passwordHashed true if the password is already hashed, false otherwise.
     * @return the new userId
     * @throws DatabaseInsertException if the role cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    public int insertNewUser(String name, int age, String address, String password,
                             boolean passwordHashed) throws DatabaseInsertException, SQLException,
            DatabaseInsertHelperException {
        try {
            if (name == null || name.trim().equals("")) {
                throw new DatabaseIllegalInsert("Name cannot be empty or null");
            }
            if (address == null || address.trim().equals("")) {
                throw new DatabaseIllegalInsert("Address cannot be empty or null");
            }
            if (address.trim().length() > 100) {
                throw new DatabaseIllegalInsert("Address cannot exceed 100 characters");
            }
            if (password == null || password.trim().equals("")) {
                throw new DatabaseIllegalInsert("Password cannot be empty or null");
            }
            if (age < 0) {
                throw new DatabaseIllegalInsert("Age must be 0 or greater");
            }
        } catch (DatabaseIllegalInsert e) {
            System.out.println(e.getMessage());
            throw new DatabaseInsertHelperException("Failed to insert new user");
        }

        if (!passwordHashed) {
            return this.insertNewUser(name, age, address, password);
        } else {
            long userId = this.insertUser(name, age, address);
            this.insertHashedPassword(password, Math.toIntExact(userId));
            return Math.toIntExact(userId);
        }
    }

    /**
     * Assigns a new role to a user in the database.
     * @param userId A valid userId in the database.
     * @param roleId A valid roleId in the database.
     * @return the new roleId
     * @throws DatabaseInsertException if the role cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertUserRole(int userId, int roleId)
        throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
        try {
            Cursor checkresults = driver.getUserDetails(userId);
            if (!checkresults.moveToNext()) {
                checkresults.close();
                throw new DatabaseIllegalInsert("The entered userId does not exist");
            }
            Cursor roles = driver.getRoles();
            List<Integer> roleIds = new ArrayList<Integer>();
            while (roles.moveToNext()) {
                roleIds.add(roles.getInt(roles.getColumnIndex("ID")));
            }
            if (!roleIds.contains(roleId)) {
                roles.close();
                throw new DatabaseIllegalInsert("This roleId does not exist");
            }

            Cursor checkUserRoleTable = driver.getUsersByRole(roleId);
            List<Integer> existingUsers = new ArrayList<>();
            while (checkUserRoleTable.moveToNext()) {
                existingUsers.add(checkUserRoleTable
                        .getInt(checkUserRoleTable.getColumnIndex("USERID")));
            }
            if (existingUsers.contains(userId)) {
                checkUserRoleTable.close();
                throw new DatabaseIllegalInsert("This user already has a role. Please use update method");
            }

            checkresults.close();
            roles.close();
            checkUserRoleTable.close();
        } catch (DatabaseIllegalInsert e) {
            System.out.println(e.getMessage());
            throw new DatabaseInsertHelperException("Failed to insert user role");
        }
        long userRoleId = driver.insertUserRole(userId, roleId);
        return Math.toIntExact(userRoleId);
    }

    /**
     * Inserts a new item into the database.
     * @param name The name of the item from the ItemTypes enum.
     * @param price The price of the item, must be greater than zero.
     * @return the new id of the item
     * @throws DatabaseInsertException if the item cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertItem(String name, BigDecimal price) throws DatabaseInsertException,
            SQLException, DatabaseInsertHelperException {
        try {
            if (name == null) {
                throw new DatabaseIllegalInsert("Name cannot be null");
            }
            if (name.length() >= 64) {
                throw new DatabaseIllegalInsert("Name must be less than 64 characters");
            }
            if (price.compareTo(new BigDecimal("0.00")) == -1) {
                throw new DatabaseIllegalInsert("Price must be greater than 0");
            }
            if (price.compareTo(new BigDecimal("0.00")) == 0) {
                throw new DatabaseIllegalInsert("Price must be greater than 0");
            }
        } catch (DatabaseIllegalInsert e) {
            System.out.println(e.getMessage());
            throw new DatabaseInsertHelperException("Failed to insert item");
        }
        price = price.setScale(2, RoundingMode.UP);
        long itemId = driver.insertItem(name, price);
        return Math.toIntExact(itemId);
    }

    /**
     * Adds an item to the store inventory.
     * @param itemId the itemId to be added to the inventory, the id must be in the database already.
     * @param quantity the amount of the item in stock.
     * @return the id of the new inventory.
     * @throws DatabaseInsertException if the inventory cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertInventory(int itemId, int quantity) throws DatabaseInsertException,
            SQLException, DatabaseInsertHelperException {
        Cursor checkresults = driver.getItem(itemId);
        try {
            if (!checkresults.moveToNext()) {
                checkresults.close();
                throw new DatabaseIllegalInsert("The entered itemId does not exist");
            }
            if (quantity < 0) {
                throw new DatabaseIllegalInsert("Quantity must be 0 or greater");
            }
        } catch (DatabaseIllegalInsert e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }
        checkresults.close();
        long inventoryId = driver.insertInventory(itemId, quantity);
        return Math.toIntExact(inventoryId);
    }

    /**
     * Inserts a new sale into the database.
     * @param userId the user who made the purchase, must be an id already in the database.
     * @param totalPrice the total value of the sale.
     * @return the new saleId
     * @throws DatabaseInsertException if the sale cannot be inserted due to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertSale(int userId, BigDecimal totalPrice)
            throws DatabaseInsertHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalInsert("The entered userId does not exist in database");
            }
        } catch (DatabaseIllegalInsert e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }
        long saleId = driver.insertSale(userId, totalPrice);
        return Math.toIntExact(saleId);
    }

    /**
     * Adds an itemized sale (stores the quantity of items sold) to the database.
     * @param saleId the id of the sale this belonged to, must be in the database already.
     * @param itemId the id of the item to be added.
     * @param quantity the amount of the given item to be added.
     * @return the new Id of the itemizedSale.
     * @throws DatabaseInsertException if the itemized sale cannot be inserted due
     *        to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertItemizedSale(int saleId, int itemId, int quantity)
            throws DatabaseInsertHelperException {
        try {
            Cursor checkSaleId = driver.getSaleById(saleId);
            if (!checkSaleId.moveToNext()) {
                checkSaleId.close();
                throw new DatabaseIllegalInsert("The entered saleId does not exist in database");
            }

            Cursor checkItemId = driver.getItem(itemId);
            if (!checkItemId.moveToNext()) {
                checkItemId.close();
                throw new DatabaseIllegalInsert("The entered itemId does not exist in database");
            }

        } catch (DatabaseIllegalInsert e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }

        long itemizedId = driver.insertItemizedSale(saleId, itemId, quantity);
        return Math.toIntExact(itemizedId);
    }

    /**
     * Inserts a new active or inactive user account into the database.
     * @param userId the id of the user this account belongs to, must be in the database already.
     * @param active whether the account is currently the user's 'active' account.
     * @return the id of the new account.
     * @throws DatabaseInsertException if the account cannot be inserted due
     *         to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertAccount(int userId, boolean active) throws DatabaseInsertHelperException {
        try {
            Cursor checkresults = driver.getUserDetails(userId);
            if (!checkresults.moveToNext()) {
                checkresults.close();
                throw new DatabaseIllegalInsert("The entered userId does not exist");
            }

        } catch (DatabaseIllegalInsert e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }
        long accountId = driver.insertAccount(userId, active);
        return Math.toIntExact(accountId);
    }

    /**
     * Inserts an item attached to a users account into the database.
     * @param accountId the id of the account to add to, must already be in the database.
     * @param itemId the id of the item to add, must already be in the database.
     * @param quantity the amount of the given item to add, must be greater than zero.
     * @return the id of the new account line.
     * @throws DatabaseInsertException if the account line cannot be inserted due
     *         to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public int insertAccountLine(int accountId, int itemId, int quantity)
            throws DatabaseInsertHelperException {
        try {
            Cursor checkResults = driver.getItem(itemId);
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalInsert("The entered itemId does not exist");
            }
            if (quantity < 0) {
                throw new DatabaseIllegalInsert("Quantity must be 0 or greater");
            }
            checkResults.close();
            Cursor checkAccountId = driver.getAccountDetails(accountId);
            if (!checkAccountId.moveToNext()) {
                checkAccountId.close();
                throw new DatabaseIllegalInsert("The entered accountId does not exist");
            }
            checkAccountId.close();
        } catch (DatabaseIllegalInsert e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }

        long accountLineId = driver.insertAccountLine(accountId, itemId, quantity);
        return Math.toIntExact(accountLineId);
    }

    /**
     * Inserts a shopping cart's contents into the database so they can be retrieved later.
     * @param cart the shopping cart to be stored.
     * @param accountId the id of the account to add the items to, must already be in the database.
     * @param userId the id of the user the cart belongs to, must be in the database already.
     * @throws DatabaseInsertException if the cart cannot be inserted due
     *         to database failure.
     * @throws SQLException if a generic database issue occurs.
     * @throws DatabaseInsertHelperException If any input is invalid.
     */
    @Override
    public void insertShoppingCart(ShoppingCartQuantityManager cart, int accountId, int userId)
            throws DatabaseInsertHelperException {
        if (cart == null) {
            throw new DatabaseInsertHelperException("The Cart cannot be null.");
        }

        try {
            DatabaseSelectHelperAndroid selector = new DatabaseSelectHelperAndroid(appContext);
            List<Integer> accIds = selector.getUserAccounts(userId);

            boolean hasId = false;
            for (int i = 0; i < accIds.size(); i++) {
                if (accIds.get(i).intValue() == accountId) {
                    hasId = true;
                    break;
                }
            }

            if (!hasId) {
                throw new DatabaseInsertHelperException("The user must have the given account ID.");
            }

            boolean cartStored = false;
            try {
                if (selector.getAccountDetails(accountId).size() > 0) {
                    cartStored = true;
                }
            } catch (Exception e) {
                // The user doesn't already have a cart stored, continue.
            }

            if (cartStored) {
                throw new DatabaseInsertHelperException("This user already has a stored cart.");
            }

            List<Item> items = cart.getItems();
            Log.d("Got here", "" + items.size());

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                if (cart.getItemQuantity(item) > 0) {
                    driver.insertAccountLine(accountId, item.getId(),
                            cart.getItemQuantity(item));
                }
            }
        } catch (Exception e) {
            throw new DatabaseInsertHelperException(e.getMessage());
        }
    }

    private long insertUser(String name, int age, String address) {
        SQLiteDatabase sqLiteDatabase = driver.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("AGE", age);
        contentValues.put("ADDRESS", address);

        long id = sqLiteDatabase.insert("USERS", null, contentValues);
        sqLiteDatabase.close();

        return id;
    }

    private void insertHashedPassword(String password, int userId) {
        SQLiteDatabase sqLiteDatabase = driver.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("USERID", userId);
        contentValues.put("PASSWORD", password);
        sqLiteDatabase.insert("USERPW", null, contentValues);
        sqLiteDatabase.close();
    }
}
