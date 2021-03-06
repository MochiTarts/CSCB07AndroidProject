package com.b07.database.adapter;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.store.ShoppingCartQuantityManager;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface DatabaseInsertInterface {
  public int insertRole(String name) throws DatabaseInsertException, 
      SQLException, DatabaseInsertHelperException, DatabaseSelectHelperException;
  
  public int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertNewUser(String name, int age, String address, String password,
      boolean passwordHashed) throws DatabaseInsertException, SQLException,
          DatabaseInsertHelperException;
  
  public int insertUserRole(int userId, int roleId)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertItem(String name, BigDecimal price)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertInventory(int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertSale(int userId, BigDecimal totalPrice)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertAccount(int userId, boolean active)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
  
  public void insertShoppingCart(ShoppingCartQuantityManager cart, int accountId,int userId)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException;
}
