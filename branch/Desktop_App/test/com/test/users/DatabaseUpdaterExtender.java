package com.test.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.b07.database.DatabaseUpdater;

public class DatabaseUpdaterExtender extends DatabaseUpdater {
  /**
   * Update the role name of a given role in the role table.
   * @param name the new name of the role.
   * @param id the current ID of the role.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateRoleName(String name, int id, Connection connection) {
    return DatabaseUpdater.updateRoleName(name, id, connection);
  }
  
  /**
   * Use this to update the user's name.
   * @param name the new name
   * @param id the current id
   * @param connection the database
   * @return true if it works, false otherwise.
   */
  protected static boolean updateUserName(String name, int id, Connection connection) {
    return DatabaseUpdater.updateUserName(name, id, connection);
  }
  
  /**
   * Use this to update the user's age.
   * @param age the new age.
   * @param id the current id
   * @param connection the connection.
   * @return true if it succeeds, false otherwise.
   */
  protected static boolean updateUserAge(int age, int id, Connection connection) {
    return DatabaseUpdater.updateUserAge(age, id, connection);
  }
  
  /**
   * Use this to update user's address.
   * @param address the new address.
   * @param id the current id.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateUserAddress(String address, int id, Connection connection) {
    return DatabaseUpdater.updateUserAddress(address, id, connection);
  }

  /**
   * update the role of the user.
   * @param roleId the new role.
   * @param id the current id.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateUserRole(int roleId, int userId, Connection connection) {
    return DatabaseUpdater.updateUserRole(roleId, userId, connection);
  }
  
  /**
   * Update the name of an item currently in the database.
   * @param name the new name.
   * @param id the id of the current item.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateItemName(String name, int id, Connection connection) {
    return DatabaseUpdater.updateItemName(name, id, connection);
  }
  
  /**
   * update the price of an item in the database.
   * @param price the new price for the item.
   * @param id the id of the item.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateItemPrice(BigDecimal price, int id, Connection connection) {
    return DatabaseUpdater.updateItemPrice(price, id, connection);
  }
  
  /**
   * update the quantity available in inventory for a given item.
   * @param quantity the new quantity.
   * @param itemId the item to be updated.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateInventoryQuantity(int quantity, int itemId, 
      Connection connection) {
    return DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
  }
}
