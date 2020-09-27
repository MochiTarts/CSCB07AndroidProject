package com.b07.database.helper;

import com.b07.database.DatabaseSelector;
import com.b07.database.DatabaseUpdater;
import com.b07.database.adapter.DatabaseSelectInterface;
import com.b07.database.adapter.DatabaseUpdateInterface;
import com.b07.exceptions.DatabaseIllegalInsert;
import com.b07.exceptions.DatabaseIllegalUpdate;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.DatabaseUpdateHelperException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.users.Roles;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUpdateHelper extends DatabaseUpdater implements DatabaseUpdateInterface {
  
  @Override
  public boolean updateRoleName(String name, int id)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {

      if (name == null || name.trim().length() == 0) {
        throw new DatabaseIllegalUpdate("The name cannot be null or empty");
      }
      
      Roles.valueOf(name);
      
      DatabaseSelectInterface selector = new DatabaseSelectHelper();
      
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
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update role");
    }
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateUserName(String name, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {

      if (name == null || name.trim().length() == 0) {
        throw new DatabaseIllegalUpdate("The name cannot be null or empty");
      }

      ResultSet checkUserId = DatabaseUpdateHelper.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalUpdate("The entered userId does not exist in database");
      }
      checkUserId.close();

    } catch (Exception e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update name");
    }
    boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateUserAge(int age, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkUserId = DatabaseUpdateHelper.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalUpdate("The entered userId does not exist in database");
      }
      checkUserId.close();

      if (age < 0) {
        throw new DatabaseIllegalUpdate("Age cannot be less than 0");
      }

    } catch (Exception e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update age");
    }

    boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateUserAddress(String address, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkUserId = DatabaseUpdateHelper.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
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
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update address");
    }

    boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateUserRole(int roleId, int userId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkResults = DatabaseUpdateHelper.getUserDetails(userId, connection);
      if (!checkResults.next()) {
        checkResults.close();
        throw new DatabaseIllegalUpdate("The entered userId does not exist");
      }

      ResultSet roles = DatabaseUpdateHelper.getRoles(connection);
      List<Integer> roleIds = new ArrayList<Integer>();
      while (roles.next()) {
        roleIds.add(roles.getInt("ID"));
      }
      if (!roleIds.contains(roleId)) {
        roles.close();
        throw new DatabaseIllegalUpdate("This roleId does not exist");
      }
      checkResults.close();
      roles.close();
    } catch (DatabaseIllegalUpdate e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Unable to update role for this user");
    }
    boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateItemName(String name, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkItemId = DatabaseUpdateHelper.getItem(itemId, connection);
      if (!checkItemId.next()) {
        checkItemId.close();
        throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
      }
      checkItemId.close();
      
      ItemTypes.valueOf(name);

      if (name == null || name.trim().length() == 0) {
        throw new DatabaseIllegalUpdate("The name cannot be empty or null");
      }
      
      List<Item> items = (new DatabaseSelectHelper()).getAllItems();
      
      for (int i = 0; i < items.size(); i++) {
        if (items.get(i).getName().equals(name)) {
          throw new DatabaseIllegalUpdate("The name cannot already be in the database.");
        }
      }

    } catch (Exception e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update item name");
    }

    boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkItemId = DatabaseUpdateHelper.getItem(itemId, connection);
      if (!checkItemId.next()) {
        checkItemId.close();
        throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
      }
      checkItemId.close();

      if (price == null || price.compareTo(new BigDecimal("0")) <= 0) {
        throw new DatabaseIllegalUpdate("The price cannot be null. The price must be above 0.");
      }

    } catch (Exception e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update item name");
    }

    boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
    connection.close();
    return complete;
  }

  @Override
  public boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      if (quantity < 0) {
        throw new DatabaseIllegalUpdate("Quantity must be greater than 0");
      }

      ResultSet getItem = DatabaseUpdateHelper.getItem(itemId, connection);

      if (!getItem.next()) {
        getItem.close();
        throw new DatabaseIllegalUpdate("The entered itemId does not exist in database");
      }
      getItem.close();
    } catch (DatabaseIllegalUpdate e) {
      connection.close();
      throw new DatabaseUpdateHelperException("Failed to update the inventory");
    }
    boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
    connection.close();
    return complete;
  }
  
  @Override
  public void updateAccountStatus(int accountId, boolean active) 
      throws DatabaseUpdateHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      (new DatabaseSelectHelper()).getAccountDetails(accountId);
      
      DatabaseUpdater.updateAccountStatus(accountId, active, connection);
      
      connection.close();
    } catch (SQLException | DatabaseSelectHelperException e) {
      try {
        connection.close();
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      throw new DatabaseUpdateHelperException(e.getMessage());
    }
  }
  
  /*
   * Private helper methods
   */
  
  private static ResultSet getRoles(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet results = statement.executeQuery("SELECT * FROM ROLES;");
    return results;
  }
  
  private static ResultSet getUserDetails(int userId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM USERS WHERE ID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    return preparedStatement.executeQuery();
  }
  
  private static ResultSet getItem(int itemId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM ITEMS WHERE ID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, itemId);
    return preparedStatement.executeQuery();
  }
}
