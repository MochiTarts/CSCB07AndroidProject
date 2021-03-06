package com.b07.database.adapter;

import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.DatabaseUpdateHelperException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseUpdateAdapter {
  private static DatabaseUpdateInterface updater;
  
  public static void setUpdater(DatabaseUpdateInterface updater) {
    if (updater != null) {
      DatabaseUpdateAdapter.updater = updater;
    }
  }
  
  public static boolean updateRoleName(String name, int id)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateRoleName(name, id);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateUserName(String name, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateUserName(name, userId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateUserAge(int age, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateUserAge(age, userId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateUserAddress(String address, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateUserAddress(address, userId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateUserRole(int roleId, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateUserRole(roleId, userId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateItemName(String name, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateItemName(name, itemId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateItemPrice(price, itemId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    if (updater != null) {
      return updater.updateInventoryQuantity(quantity, itemId);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
  
  public static void updateAccountStatus(int accountId, boolean active) 
      throws DatabaseUpdateHelperException {
    if (updater != null) {
      updater.updateAccountStatus(accountId, active);
    } else {
      throw new DatabaseUpdateHelperException("No updater.");
    }
  }
}
