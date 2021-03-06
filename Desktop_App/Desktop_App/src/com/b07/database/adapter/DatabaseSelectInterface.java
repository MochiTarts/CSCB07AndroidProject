package com.b07.database.adapter;

import com.b07.accounts.Account;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.ItemSetException;
import com.b07.exceptions.Unauthenticated;
import com.b07.exceptions.UserSetException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.Customer;
import com.b07.users.User;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface DatabaseSelectInterface {
  public List<Integer> getRoleIds() throws SQLException;
  
  public String getRoleName(int roleId) throws SQLException, DatabaseSelectHelperException;
  
  public int getUserRoleId(int userId) throws SQLException, DatabaseSelectHelperException;
  
  public List<Integer> getUsersByRole(int roleId)
      throws SQLException, DatabaseSelectHelperException;
  
  public List<User> getUsersDetails() throws SQLException,  
      BuildTypeException, UserSetException, DatabaseSelectHelperException;
  
  public User getUserDetails(int userId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException, 
                           UserSetException;
  
  public String getPassword(int userId) throws SQLException, DatabaseSelectHelperException;
  
  public List<Item> getAllItems() throws SQLException, ItemSetException;
  
  public Item getItem(int itemId)
      throws SQLException, DatabaseSelectHelperException, ItemSetException;
  
  public Inventory getInventory()
      throws SQLException, DatabaseSelectHelperException, ItemSetException;
  
  public int getInventoryQuantity(int itemId)
      throws SQLException, DatabaseSelectHelperException;
  
  public SalesLog getSales() throws SQLException, DatabaseSelectHelperException;
  
  public Sale getSaleById(int saleId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException, UserSetException;
  
  public List<Sale> getSalesToUser(int userId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException,
      UserSetException;
  
  public Sale getItemizedSaleById(int saleId)
      throws SQLException, DatabaseSelectHelperException;
  
  public SalesLog getItemizedSales() throws SQLException, DatabaseSelectHelperException;
  
  public SalesLog getCompleteSales() throws DatabaseSelectHelperException;
  
  public List<Integer> getUserAccounts(int userId)
      throws SQLException, DatabaseSelectHelperException;
  
  public HashMap<Integer, Integer> getAccountDetails(int accountId)
      throws SQLException, DatabaseSelectHelperException;
  
  public ShoppingCartInterface getShoppingCart(Customer user, int accountId)
      throws SQLException, DatabaseSelectHelperException, Unauthenticated;
  
  public List<Integer> getUserActiveAccounts(int userId) throws DatabaseSelectHelperException;
  
  public List<Integer> getUserInactiveAccounts(int userId) throws DatabaseSelectHelperException;
  
  public List<Account> getAccounts() throws DatabaseSelectHelperException;
  
  public HashMap<Integer, String> getPasswords() throws DatabaseSelectHelperException;
}
