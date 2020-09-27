package com.test.database.helper;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Item;
import com.b07.inventory.StoreItem;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.store.ShoppingCartQuantityManager;
import com.b07.users.Customer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseInsertHelperTest {
  private static int validRoleId;
  private static int validUserId;
  
  private static String defaultRoleName = "CUSTOMER";
  
  private static String defaultUserName = "Name";
  private static String defaultUserAddress = "123 ABC";
  private static String defaultUserPassword = "password";
  private static int defaultUserAge = 10;
  private static int defaultUserRole;
  private static int defaultUserAccountId;
  
  private static String defaultItemName;
  private static int validItemId = 1;
  private static BigDecimal defaultPrice = new BigDecimal("12.00");
  
  private static int defaultQuantity = 100;
  private static int validInventoryId = 1;
  private static int defaultCustomerAccountId;
  
  private static Customer customer;
  private static int validCustomerId;
  
  @BeforeClass
  public static void initTests() {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    
    boolean roleExists = false;
    try {
      ResultSet results = DatabaseSelectorExtender.getRoles(connection);
      while (results.next()) {
        if (results.getString("NAME").equals(defaultRoleName)) {
          roleExists = true;
          validRoleId = results.getInt("ID");
          break;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    
    if (!roleExists) {
      try {
        validRoleId = DatabaseInserterExtender.insertRole(defaultRoleName, connection);
      } catch (DatabaseInsertException e) {
        e.printStackTrace();
      }
    }
    
    boolean userExists = false;
    try {
      ResultSet results = DatabaseSelectorExtender.getUsersDetails(connection);
      while (results.next()) {
        if (results.getString("NAME").equals(defaultUserName)) {
          userExists = true;
          validUserId = results.getInt("ID");
          break;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    
    if (!userExists) {
      try {
        validUserId = DatabaseInserterExtender.insertNewUser(defaultUserName,
                                                               defaultUserAge,
                                                               defaultUserAddress,
                                                               defaultUserPassword,
                                                               connection);
      } catch (DatabaseInsertException e) {
        e.printStackTrace();
      }
    }
    
    try {
      validCustomerId = DatabaseInserterExtender.insertNewUser("Customer",
          defaultUserAge,
          defaultUserAddress,
          defaultUserPassword,
          connection);
    } catch (DatabaseInsertException e2) {
      e2.printStackTrace();
    }
    
    customer = new Customer(validCustomerId, defaultUserName, 
                              defaultUserAge, defaultUserAddress);
    
    try {
      DatabaseSelectorExtender.getRole(DatabaseSelectorExtender
                                                           .getUserRole(validUserId, connection),
                                                           connection);
    } catch (Exception e) {
      try {
        DatabaseInserterExtender.insertUserRole(validUserId, validRoleId, connection);
      } catch (DatabaseInsertException e1) {
        e1.printStackTrace();
      }
    }
    
    try {
      defaultUserAccountId = DatabaseInserterExtender.insertAccount(validUserId, connection);
      defaultCustomerAccountId = DatabaseInserterExtender.insertAccount(validCustomerId, 
                                                                          connection);
    } catch (DatabaseInsertException e1) {
      e1.printStackTrace();
    }
    
    defaultUserRole = validRoleId;
    
    try {
      ResultSet result = DatabaseSelectorExtender.getItem(validItemId, connection);
      result.next();
      defaultItemName = result.getString("NAME");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  @After
  public void incrementAccountId() {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    try {
      defaultCustomerAccountId = DatabaseInserterExtender.insertAccount(validCustomerId, 
          connection);
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void insertShoppingCart_oneItemCart() throws Exception {
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    Item item = new StoreItem();
    item.setName(defaultItemName);
    item.setId(validItemId);
    items.put(item, 10);
    
    Field authenticated = getCustomerField("authenticated");
    authenticated.set(customer, true);
    
    ShoppingCartQuantityManager cart = new ShoppingCart(customer);
    
    Field field = getCartField("items");
    field.set(cart, items);
    
    DatabaseInsertAdapter.insertShoppingCart(cart, defaultCustomerAccountId, validCustomerId);
    
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    ResultSet results = DatabaseSelectorExtender.getAccountDetails(defaultCustomerAccountId,
                                                                     connection);
    
    while (results.next()) {
      if (!(results.getInt("itemId") == validItemId 
              && results.getInt("quantity") == 10)) {
        connection.close();
        authenticated.set(customer, false);
        fail();
      }
    }
    
    connection.close();
    authenticated.set(customer, false);
    return;
  }
  
  @Test
  public void insertShoppingCart_multiItemCart() throws Exception {
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    Item item = new StoreItem();
    item.setName(defaultItemName);
    item.setId(validItemId);
    items.put(item, 10);
    items.put(item, 10);
    items.put(item, 10);
    
    Field authenticated = getCustomerField("authenticated");
    authenticated.set(customer, true);
    
    ShoppingCartQuantityManager cart = new ShoppingCart(customer);
    
    Field field = getCartField("items");
    field.set(cart, items);
    
    DatabaseInsertAdapter.insertShoppingCart(cart, defaultCustomerAccountId, validCustomerId);
    
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    ResultSet results = DatabaseSelectorExtender.getAccountDetails(defaultCustomerAccountId,
                                                                     connection);
    
    while (results.next()) {
      if (!(results.getInt("itemId") == validItemId 
              && results.getInt("quantity") == 10)) {
        connection.close();
        authenticated.set(customer, false);
        fail();
      }
    }
    
    connection.close();
    authenticated.set(customer, false);
    return;
  }

  private Field getCartField(String name) throws Exception {
    Field f = ShoppingCart.class.getDeclaredField(name);
    f.setAccessible(true);
    return f;
  }
  
  private Field getCustomerField(String name) throws Exception {
    Field f = Customer.class.getDeclaredField(name);
    f.setAccessible(true);
    return f;
  }
}
