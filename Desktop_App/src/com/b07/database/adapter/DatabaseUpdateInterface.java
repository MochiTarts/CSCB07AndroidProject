package com.b07.database.adapter;

import com.b07.exceptions.DatabaseUpdateHelperException;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface DatabaseUpdateInterface {
  public boolean updateRoleName(String name, int id)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateUserName(String name, int userId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateUserAge(int age, int userId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateUserAddress(String address, int userId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateUserRole(int roleId, int userId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateItemName(String name, int itemId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, DatabaseUpdateHelperException;
  
  public void updateAccountStatus(int accountId, boolean active) 
      throws DatabaseUpdateHelperException;
}
