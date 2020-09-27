package com.b07.database.adapter;

import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.store.ShoppingCartQuantityManager;
import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseInsertAdapter {
  private static DatabaseInsertInterface inserter;
  
  public static void setInserter(DatabaseInsertInterface inserter) {
    if (inserter != null) {
      DatabaseInsertAdapter.inserter = inserter;
    }
  }
  
  public static int insertRole(String name) throws DatabaseInsertException, 
      SQLException, DatabaseInsertHelperException, DatabaseSelectHelperException {
    if (inserter != null) {
      return inserter.insertRole(name);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertNewUser(name, age, address, password);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }
  
  protected static int insertNewUser(String name, int age, String address, String password,
      boolean passwordHashed)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertNewUser(name, age, address, password, passwordHashed);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertUserRole(int userId, int roleId)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertUserRole(userId, roleId);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertItem(String name, BigDecimal price)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertItem(name, price);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertInventory(int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertInventory(itemId, quantity);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertSale(int userId, BigDecimal totalPrice)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertSale(userId, totalPrice);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertItemizedSale(saleId, itemId, quantity);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertAccount(int userId, boolean active)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertAccount(userId, active);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      return inserter.insertAccountLine(accountId, itemId, quantity);
    } else {
      throw new DatabaseInsertHelperException("There is no inserter.");
    }
  }

  public static void insertShoppingCart(ShoppingCartQuantityManager cart, int accountId,int userId)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    if (inserter != null) {
      inserter.insertShoppingCart(cart, accountId, userId);
    }
  }
}
