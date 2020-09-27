package com.b07.database.helper;

import android.content.Context;
import android.database.Cursor;

import com.b07.database.adapter.DatabaseSelectInterface;
import com.b07.database.adapter.DatabaseUpdateInterface;
import com.b07.exceptions.DatabaseIllegalUpdate;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.DatabaseUpdateHelperException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.users.Roles;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUpdateHelperAndroid implements DatabaseUpdateInterface {
    private DatabaseDriverAndroidExtender driver;
    private Context appContext;

    public DatabaseUpdateHelperAndroid(Context context) {
        this.appContext = context;
        driver = new DatabaseDriverAndroidExtender(context);
    }

    @Override
    public boolean updateRoleName(String name, int id)
            throws SQLException, DatabaseUpdateHelperException {
        try {

            if (name == null || name.trim().length() == 0) {
                throw new DatabaseIllegalUpdate("The name cannot be null or empty");
            }

            Roles.valueOf(name);

            DatabaseSelectInterface selector = new DatabaseSelectHelperAndroid(appContext);

            List<Integer> checkId = selector.getRoleIds();
            boolean idExists = false;
            boolean nameInTable = false;
            for (int i = 0; i < checkId.size(); i++) {
                if (checkId.get(i).intValue() == id) {
                    idExists = true;
                }
                if (selector.getRoleName(checkId.get(i).intValue()).equals(name)) {
                    nameInTable = true;
                    break;
                }
            }
            if (!idExists || nameInTable) {
                throw new DatabaseIllegalUpdate("The role id does not exist in database");
            }
        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update role");
        }
        boolean complete = driver.updateRoleName(name, id);
        return complete;
    }

    @Override
    public boolean updateUserName(String name, int userId)
            throws SQLException, DatabaseUpdateHelperException {
        try {

            if (name == null || name.trim().length() == 0) {
                throw new DatabaseIllegalUpdate("The name cannot be null or empty");
            }

            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalUpdate("The entered "
                        + "userId does not exist in database");
            }
            checkUserId.close();

        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update name");
        }
        boolean complete = driver.updateUserName(name, userId);
        return complete;
    }

    @Override
    public boolean updateUserAge(int age, int userId) throws SQLException, DatabaseUpdateHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalUpdate("The entered userId does not exist in database");
            }
            checkUserId.close();

            if (age < 0) {
                throw new DatabaseIllegalUpdate("Age cannot be less than 0");
            }

        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update age");
        }

        boolean complete = driver.updateUserAge(age, userId);
        return complete;
    }

    @Override
    public boolean updateUserAddress(String address, int userId) throws SQLException, DatabaseUpdateHelperException {
        try {
            Cursor checkUserId = driver.getUserDetails(userId);
            if (!checkUserId.moveToNext()) {
                checkUserId.close();
                throw new DatabaseIllegalUpdate("The entered userId does not exist in database");
            }
            checkUserId.close();

            if (address == null || address.trim().equals("")) {
                throw new DatabaseIllegalUpdate("Address cannot be empty or null");
            }
            if (address.trim().length() > 100) {
                throw new DatabaseIllegalUpdate("Address cannot exceed 100 characters");
            }

        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update address");
        }

        boolean complete = driver.updateUserAddress(address, userId);
        return complete;
    }

    @Override
    public boolean updateUserRole(int roleId, int userId) throws SQLException, DatabaseUpdateHelperException {
        try {
            Cursor checkResults = driver.getUserDetails(userId);
            if (!checkResults.moveToNext()) {
                checkResults.close();
                throw new DatabaseIllegalUpdate("The entered userId does not exist");
            }

            Cursor roles = driver.getRoles();
            List<Integer> roleIds = new ArrayList<Integer>();
            while (roles.moveToNext()) {
                roleIds.add(roles.getInt(roles.getColumnIndex("ID")));
            }
            if (!roleIds.contains(roleId)) {
                roles.close();
                throw new DatabaseIllegalUpdate("This roleId does not exist");
            }
            checkResults.close();
            roles.close();
        } catch (DatabaseIllegalUpdate e) {
            throw new DatabaseUpdateHelperException("Unable to update role for this user");
        }
        boolean complete = driver.updateUserRole(roleId, userId);
        return complete;
    }

    @Override
    public boolean updateItemName(String name, int itemId) throws SQLException, DatabaseUpdateHelperException {
        try {
            Cursor checkItemId = driver.getItem(itemId);
            if (!checkItemId.moveToNext()) {
                checkItemId.close();
                throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
            }
            checkItemId.close();

            ItemTypes.valueOf(name);

            if (name == null || name.trim().length() == 0) {
                throw new DatabaseIllegalUpdate("The name cannot be empty or null");
            }

            List<Item> items = (new DatabaseSelectHelperAndroid(appContext)).getAllItems();

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getName().equals(name)) {
                    throw new DatabaseIllegalUpdate("The name cannot already be in the database.");
                }
            }

        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update item name");
        }

        boolean complete = driver.updateItemName(name, itemId);
        return complete;
    }

    @Override
    public boolean updateItemPrice(BigDecimal price, int itemId) throws SQLException, DatabaseUpdateHelperException {
        try {
            Cursor checkItemId = driver.getItem(itemId);
            if (!checkItemId.moveToNext()) {
                checkItemId.close();
                throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
            }
            checkItemId.close();

            if (price == null || price.compareTo(new BigDecimal("0")) <= 0) {
                throw new DatabaseIllegalUpdate("The price cannot be null. The price must be above 0.");
            }

        } catch (Exception e) {
            throw new DatabaseUpdateHelperException("Failed to update item name");
        }

        boolean complete = driver.updateItemPrice(price, itemId);
        return complete;
    }

    @Override
    public boolean updateInventoryQuantity(int quantity, int itemId) throws SQLException, DatabaseUpdateHelperException {
        try {
            if (quantity < 0) {
                throw new DatabaseIllegalUpdate("Quantity must be greater than 0");
            }

            Cursor getItem = driver.getItem(itemId);

            if (!getItem.moveToNext()) {
                getItem.close();
                throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
            }
            getItem.close();
        } catch (DatabaseIllegalUpdate e) {
            throw new DatabaseUpdateHelperException("Failed to update the inventory");
        }
        boolean complete = driver.updateInventoryQuantity(quantity, itemId);
        return complete;
    }

    @Override
    public void updateAccountStatus(int accountId, boolean active) throws DatabaseUpdateHelperException {
        try {
            (new DatabaseSelectHelperAndroid(appContext)).getAccountDetails(accountId);

            driver.updateAccountStatus(accountId, active);
        } catch (SQLException | DatabaseSelectHelperException e) {
            throw new DatabaseUpdateHelperException(e.getMessage());
        }
    }
}
