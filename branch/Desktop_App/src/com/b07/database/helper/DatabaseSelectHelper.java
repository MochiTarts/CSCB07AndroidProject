package com.b07.database.helper;

import com.b07.accounts.Account;
import com.b07.accounts.AccountImpl;
import com.b07.builders.ItemBuilderImpl;
import com.b07.builders.UserBuilderImpl;
import com.b07.database.DatabaseSelector;
import com.b07.database.adapter.DatabaseSelectInterface;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseIllegalGet;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.ItemSetException;
import com.b07.exceptions.Unauthenticated;
import com.b07.exceptions.UserSetException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.StoreInventory;
import com.b07.inventory.StoreItem;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.store.ShoppingCart;
import com.b07.store.ShoppingCartInterface;
import com.b07.users.Customer;
import com.b07.users.Roles;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * TODO: Complete the below methods to be able to get information out of the database.
 * TODO: The given code is there to aide you in building your methods.  You don't have
 * TODO: to keep the exact code that is given (for example, DELETE the System.out.println())
 * TODO: and decide how to handle the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseSelector implements DatabaseSelectInterface {
  /**
   * Gets a list of all of the role ids in the database.
   * @return a list of the role ids
   * @throws SQLException if a generic issue occurs with the database.
   */
  @Override
  public List<Integer> getRoleIds() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> ids = new ArrayList<>();
    ResultSet results = DatabaseSelector.getRoles(connection);
    while (results.next()) {
      ids.add(results.getInt("ID"));
    }
    results.close();
    connection.close();
    return ids;
  }
  
  /**
   * Gets the name of a role from the database.
   * @param roleId the id of the role, must be in the database.
   * @return the name of the role associated with roleId.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws DatabaseSelectHelperException If the input is invalid.
   */
  @Override
  public String getRoleName(int roleId) throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkResults = DatabaseSelector.getRoles(connection);
      List<Integer> roleIds = new ArrayList<Integer>();
      while (checkResults.next()) {
        roleIds.add(checkResults.getInt("ID"));
      }
      if (!roleIds.contains(roleId)) {
        checkResults.close();
        throw new DatabaseIllegalGet("This roleId does not exist");
      }
    } catch (DatabaseIllegalGet | SQLException e) {
      System.out.println(e.getMessage());
      connection.close();
      throw new DatabaseSelectHelperException("Failed to get role name");
    }
    String role = DatabaseSelector.getRole(roleId, connection);
    connection.close();
    return role;
  }
  
  /**
   * Returns the role id associated with the given user.
   * @param userId a user in the database.
   * @return the role id of the user.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws DatabaseSelectHelperException if the input is invalid.
   */
  @Override
  public int getUserRoleId(int userId) throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkTable = DatabaseSelector.getRoles(connection);
      if (!checkTable.next()) {
        checkTable.close();
        throw new DatabaseIllegalGet(
            "The USERROLE table is currently empty. Please insert a user role");
      }

      ResultSet checkResults = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkResults.next()) {
        checkResults.close();
        throw new DatabaseIllegalGet("This userId does not exist");
      }
      checkTable.close();
      checkResults.close();
    } catch (DatabaseIllegalGet | SQLException e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get user role id");
    }
    int roleId = DatabaseSelector.getUserRole(userId, connection);
    connection.close();
    return roleId;
  }
  
  /**
   * Returns the ids of all of the users associated with a given role.
   * @param roleId the id of the role, must me in the Roles enum.
   * @return a list containing the users associated with the given role.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws DatabaseSelectHelperException if the input is invalid.
   */
  @Override
  public List<Integer> getUsersByRole(int roleId)
      throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> userIds = new ArrayList<>();
    ResultSet results;
    try {
      ResultSet checkTable = DatabaseSelector.getRoles(connection);
      if (!checkTable.next()) {
        checkTable.close();
        throw new DatabaseIllegalGet(
            "The USERROLE table is currently empty. Please insert a user role");
      }

      ResultSet checkResults = DatabaseSelector.getRoles(connection);
      List<Integer> roleIds = new ArrayList<Integer>();
      while (checkResults.next()) {
        roleIds.add(checkResults.getInt("ID"));
      }
      if (!roleIds.contains(roleId)) {
        checkTable.close();
        checkResults.close();
        throw new DatabaseIllegalGet("This roleId does not exist");
      }
      checkTable.close();
      checkResults.close();
      results = DatabaseSelector.getUsersByRole(roleId, connection);
    } catch (DatabaseIllegalGet e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get users with this roleId");
    } catch (SQLException e) {
      connection.close();
      throw new SQLException(e.getMessage());
    }
    while (results.next()) {
      userIds.add(results.getInt("USERID"));
    }
    results.close();
    connection.close();
    return userIds;
  }
  
  /**
   * Returns a list of all the Users in the database.
   * @return a list containing User objects representing every user in the database.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws BuildTypeException if one of the Users has an invalid role.
   * @throws UserSetException if one of the users has an invalid field.
   * @throws DatabaseSelectHelperException if the Users cannot be retrieved from the database.
   */
  @Override
  public List<User> getUsersDetails() throws SQLException,  
      BuildTypeException, UserSetException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<User> users = new ArrayList<>();
    ResultSet results;
    try {
      results = DatabaseSelector.getUsersDetails(connection);
    } catch (SQLException e) {
      connection.close();
      throw new SQLException(e.getMessage());
    }
    int id;
    int age;
    String name;
    String address;
    while (results.next()) {
      id = results.getInt("ID");
      name = results.getString("NAME");
      age = results.getInt("AGE");
      address = results.getString("ADDRESS");
      users.add(makeUser(id, name, age, address));
    }
    results.close();
    connection.close();
    return users;
  }
  
  /**
   * Returns the User associated with the given userId.
   * 
   * @param userId the id of the User, must be in the table.
   * @return a User object representing the user in the database.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws BuildTypeException if the User has an invalid role.
   * @throws UserSetException if the User has an invalid field.
   * @throws DatabaseSelectHelperException if the User cannot be found.
   */
  @Override
  public User getUserDetails(int userId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException, 
                           UserSetException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet checkResults = DatabaseSelector.getUserDetails(userId, connection);
    try {
      if (!checkResults.next()) {
        checkResults.close();
        throw new DatabaseIllegalGet("The entered userId does not exist");
      }
    } catch (DatabaseIllegalGet | SQLException e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Couldn't get details for this user");
    }
    int id;
    int age;
    String name;
    /*while (results.next()) {
      System.out.println(results.getInt("ID"));
      System.out.println(results.getString("NAME"));
      System.out.println(results.getInt("AGE"));
      System.out.println(results.getString("ADDRESS"));
    }*/
    id = checkResults.getInt("ID");
    name = checkResults.getString("NAME");
    age = checkResults.getInt("AGE");
    String address;
    address = checkResults.getString("ADDRESS");
    checkResults.close();
    connection.close();
    return makeUser(id, name, age, address);
  }
  
  private User makeUser(int userId, String name, int age, String address)
      throws SQLException,
             BuildTypeException, UserSetException, 
             DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseSelector.getUserRole(userId, connection);
    connection.close();
    String roleName = getRoleName(roleId);
    if (Roles.valueOf(roleName).equals(Roles.ADMIN)) {
      User admin =
          new UserBuilderImpl(Roles.ADMIN, false)
              .id(userId)
              .name(name)
              .age(age)
              .address(address)
              .build();
      return admin;
    } else if (Roles.valueOf(roleName).equals(Roles.CUSTOMER)) {
      User customer =
          new UserBuilderImpl(Roles.CUSTOMER, false)
              .id(userId)
              .name(name)
              .age(age)
              .address(address)
              .build();
      return customer;
    } else {
      User employee =
          new UserBuilderImpl(Roles.EMPLOYEE, false)
              .id(userId)
              .name(name)
              .age(age)
              .address(address)
              .build();
      return employee;
    }
  }

  /**
   * Returns the hashed password in the database associated with the given user.
   * @param userId the id of a user in the database.
   * @return the hashed password of the user.
   * @throws SQLException if a generic issue occurs with the database.
   * @throws DatabaseSelectHelperException if the user cannot be found.
   */
  @Override
  public String getPassword(int userId) throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkResults = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkResults.next()) {
        checkResults.close();
        throw new DatabaseIllegalGet("The entered userId does not exist");
      }
      checkResults.close();
    } catch (DatabaseIllegalGet e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get password for this user");
    }
    String password = DatabaseSelector.getPassword(userId, connection);
    connection.close();
    return password;
  }
  
  @Override
  public List<Item> getAllItems() throws SQLException, ItemSetException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAllItems(connection);
    List<Item> items = new ArrayList<>();
    while (results.next()) {
      Item item = new StoreItem();
      item.setId(results.getInt("ID"));
      item.setName(results.getString("NAME"));
      item.setPrice(new BigDecimal(results.getString("PRICE")));
      items.add(item);
    }
    results.close();
    connection.close();
    return items;
  }
  
  @Override
  public Item getItem(int itemId)
      throws SQLException, DatabaseSelectHelperException, ItemSetException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkResults = DatabaseSelector.getItem(itemId, connection);
      if (!checkResults.next()) {
        checkResults.close();
        throw new DatabaseIllegalGet("The entered itemId does not exist");
      }
      checkResults.close();
    } catch (DatabaseIllegalGet | SQLException e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get item");
    }
    ResultSet results = DatabaseSelector.getItem(itemId, connection);
    Item item = new ItemBuilderImpl().build();
    while (results.next()) {
      item = new ItemBuilderImpl().id(results.getInt("ID"))
          .name(results.getString("NAME")).price(new BigDecimal(results
                                                                  .getString("PRICE"))).build();
    }
    results.close();
    connection.close();
    return item;
  }
  
  @Override
  public Inventory getInventory()
      throws SQLException, DatabaseSelectHelperException, ItemSetException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getInventory(connection);
    int itemId;
    int quantity;
    try {
      if (!results.next()) {
        throw new DatabaseSelectHelperException("Couldn't get rows");
      }
      /*while (results.next()) {
        System.out.println(results.getInt("ITEMID"));
        System.out.println(results.getInt("QUANTITY"));
        itemId = results.getInt("ITEMID");
        quantity = results.getInt("QUANTITY");
        Item item = getItem(itemId);
      }*/
    } catch (Exception e) {
      results.close();
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get inventory");
    }
    results.close();

    ResultSet inventoryResults = DatabaseSelector.getInventory(connection);
    Inventory inventory = new StoreInventory();
    inventory.setItemMap(new HashMap<Item, Integer>());
    while (inventoryResults.next()) {
      itemId = inventoryResults.getInt("ITEMID");
      quantity = inventoryResults.getInt("QUANTITY");
      Item item = getItem(itemId);
      inventory.updateMap(item, quantity);
    }

    inventoryResults.close();
    connection.close();
    return inventory;
  }
  
  @Override
  public int getInventoryQuantity(int itemId)
      throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkItemId = DatabaseSelector.getItem(itemId, connection);
      if (!checkItemId.next()) {
        checkItemId.close();
        throw new DatabaseIllegalGet("The entered itemId does not exist in database");
      }
      checkItemId.close();
    } catch (DatabaseIllegalGet e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get inventory quantity for this item");
    }
    int quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
    connection.close();
    return quantity;
  }
  
  @Override
  public SalesLog getSales() throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSales(connection);
    SalesLog log = new SalesLogImpl();
    while (results.next()) {
      Sale sale = new SaleImpl();
      sale.setId(results.getInt("ID"));
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      try {
        sale.setUser(getUserDetails(results.getInt("USERID")));
      } catch (SecurityException | IllegalArgumentException 
          | DatabaseSelectHelperException | BuildTypeException 
          | UserSetException e) {
        results.close();
        connection.close();
        throw new DatabaseSelectHelperException("Could not get sales.");
      }
      log.addSale(sale);
    }
    results.close();
    connection.close();
    return log;
  }
  
  @Override
  public Sale getSaleById(int saleId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException, UserSetException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkSaleId = DatabaseSelector.getSaleById(saleId, connection);
      if (!checkSaleId.next()) {
        checkSaleId.close();
        throw new DatabaseIllegalGet("The entered saleId does not exist in database");
      }
      checkSaleId.close();
    } catch (Exception e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("The entered saleId does not exist");
    }

    ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
    Sale sale = new SaleImpl();
    while (results.next()) {
      sale.setId(results.getInt("ID"));
      User user = getUserDetails(results.getInt("USERID"));
      sale.setUser(user);
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
    }
    results.close();
    connection.close();
    return sale;
  }
  
  @Override
  public List<Sale> getSalesToUser(int userId)
      throws SQLException, DatabaseSelectHelperException, BuildTypeException,
          UserSetException {

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkUserId = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalGet("The entered userId does not exist in database");
      }
      checkUserId.close();
    } catch (Exception e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get sales to user");
    }

    ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
    List<Sale> sales = new ArrayList<>();
    while (results.next()) {
      Sale sale = new SaleImpl();
      sale.setId(results.getInt("ID"));
      User user = getUserDetails(results.getInt("USERID"));
      sale.setUser(user);
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      sales.add(sale);
    }
    results.close();
    connection.close();
    return sales;
  }
  
  @Override
  public Sale getItemizedSaleById(int saleId)
      throws SQLException, DatabaseSelectHelperException {

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
      if (!results.next()) {
        results.close();
        throw new DatabaseIllegalGet("The entered saleId does not exist");
      }
      results.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("The saleId does not exist");
    }

    ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
    Sale sale = new SaleImpl();
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    try {
      while (results.next()) {
        sale.setId(results.getInt("SALEID"));
        items.put(this.getItem(results.getInt("ITEMID")), new Integer(results.getInt("QUANTITY")));
      }
    } catch (ItemSetException e) {
      throw new DatabaseSelectHelperException("Could not retrieve all items from the table.");
    }
    sale.setItemMap(items);
    results.close();
    connection.close();
    return sale;
  }
  
  @Override
  public SalesLog getItemizedSales() throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSales(connection);
    int lastSaleId = -1;
    SalesLog log = new SalesLogImpl();
    Sale sale = new SaleImpl();
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    
    if (results.next()) {
      int saleId = results.getInt("SALEID");
      sale.setId(saleId);
      try {
        items.put(this.getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
      } catch (ItemSetException e) {
        results.close();
        connection.close();
        throw new DatabaseSelectHelperException(e.getMessage());
      }
      lastSaleId = saleId;
    }
    
    while (results.next()) {
      int saleId = results.getInt("SALEID");
      try {
        if (lastSaleId != saleId) {
          sale.setItemMap(items);
          log.addSale(sale);
          sale = new SaleImpl();
          items = new HashMap<Item, Integer>();
          sale.setId(saleId);
          items.put(this.getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
          lastSaleId = saleId;
        } else {
          items.put(this.getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
        }
      } catch (ItemSetException | DatabaseSelectHelperException e) {
        results.close();
        connection.close();
        throw new DatabaseSelectHelperException(e.getMessage());
      }
    }
    
    if (items.size() > 0) {
      sale.setItemMap(items);
      log.addSale(sale);
    }
    results.close();
    connection.close();
    return log;
  }
  
  @Override
  public SalesLog getCompleteSales() throws DatabaseSelectHelperException {
    try {
      SalesLog log = this.getItemizedSales();
      
      for (Sale sale : log.getSales()) {
        if (sale.getId() > 0) {
          Sale fromSaleTable = this.getSaleById(sale.getId());
          sale.setTotalPrice(fromSaleTable.getTotalPrice());
          sale.setUser(fromSaleTable.getUser());
        }
      }
      
      return log;
    } catch (UserSetException | SQLException | BuildTypeException e) {
      throw new DatabaseSelectHelperException("Could not retrieve sales.");
    }
  }

  /**
   * Checks if userId exists in database and if so, returns a list of accounts associated with the
   * given user.
   *
   * @param userId value whose existence will be checked if exists in database
   * @return a list of Integer account ids that are associated with the user with userId
   * @throws SQLException if something goes wrong with the database
   * @throws DatabaseSelectHelperException if the userId does not exist
   */
  @Override
  public List<Integer> getUserAccounts(int userId)
      throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkUserId = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalGet("The entered userId does not exist in the database");
      }

      ResultSet checkAccountExists = DatabaseSelector.getUserAccounts(userId, connection);
      if (!checkAccountExists.next()) {
        checkAccountExists.close();
        throw new DatabaseIllegalGet("The user associated to the entered userId has no account");
      }

      checkUserId.close();
      checkAccountExists.close();
    } catch (Exception e) {
      connection.close();
      System.out.println(e.getMessage());
      throw new DatabaseSelectHelperException("Failed to get user accounts");
    }

    ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);
    List<Integer> userAccountIds = new ArrayList<>();
    while (results.next()) {
      userAccountIds.add(results.getInt("ID"));
    }

    results.close();
    connection.close();

    return userAccountIds;
  }

  /**
   * Checks if accountId exists in the database and if so, returns all items and their quantities
   * associated with given account in a HashMap. Where the key is the item id and the value is the
   * quantity
   *
   * @param accountId value whose existence in database is to be checked
   * @return HashMap where the key is the item id and the value is the quantity
   * @throws SQLException if something goes wrong with the database
   * @throws DatabaseSelectHelperException if the accountId does not exist
   */
  @Override
  public HashMap<Integer, Integer> getAccountDetails(int accountId)
      throws SQLException, DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      ResultSet checkAccountId = DatabaseSelector.getAccountDetails(accountId, connection);
      if (!checkAccountId.next()) {
        throw new DatabaseIllegalGet("The entered accountId does not exist in database");
      }
    } catch (Exception e) {
      connection.close();
      throw new DatabaseSelectHelperException("Failed to get details for the account entered");
    }

    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
    HashMap<Integer, Integer> userPreviousCart = new HashMap<>();
    while (results.next()) {
      userPreviousCart.put(results.getInt("ITEMID"), results.getInt("QUANTITY"));
    }

    results.close();
    connection.close();

    return userPreviousCart;
  }
  
  @Override
  public ShoppingCartInterface getShoppingCart(Customer user, int accountId)
      throws SQLException, DatabaseSelectHelperException, Unauthenticated {
    if (!user.getAuthenticated()) {
      throw new Unauthenticated("The User must be authenticated to retrieve their cart.");
    }
    
    List<Integer> accounts = getUserAccounts(user.getId());
    
    boolean hasAccount = false;
    for (int i = 0; i < accounts.size(); i++) {
      if (accounts.get(i).intValue() == accountId) {
        hasAccount = true;
      }
    }
    
    if (!hasAccount) {
      throw new DatabaseSelectHelperException("This user does not own an account with that ID.");
    }
    
    HashMap<Integer, Integer> accDetails = getAccountDetails(accountId);
    
    if (accDetails.size() <= 0) {
      throw new DatabaseSelectHelperException("This user does not have a cart to retrieve.");
    }

    try {
      ShoppingCartInterface shoppingCart = new ShoppingCart(user, true);
      for (Integer i : accDetails.keySet()) {
        if (accDetails.get(i).intValue() > 0) {
          shoppingCart.addItem(getItem(i), accDetails.get(i));
        }
      }
      
      return shoppingCart;
    } catch (Unauthenticated e) {
      throw new Unauthenticated("The User must be authenticated to retrieve their cart.");
    } catch (ItemSetException e) {
      throw new DatabaseSelectHelperException("The cart could not be retrieved.");
    }
  }
  
  @Override
  public List<Integer> getUserActiveAccounts(int userId) throws DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkUserId = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalGet("The entered userId does not exist in the database");
      }
      
      ResultSet activeAccounts = DatabaseSelector.getUserActiveAccounts(userId, connection);
      List<Integer> accountIds = new ArrayList<Integer>();
      
      while (activeAccounts.next()) {
        accountIds.add(new Integer(activeAccounts.getInt("ID")));
      }
      
      connection.close();
      
      return accountIds;
    } catch (SQLException | DatabaseIllegalGet e) {
      try {
        connection.close();
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      throw new DatabaseSelectHelperException(e.getMessage());
    }
  }
  
  @Override
  public List<Integer> getUserInactiveAccounts(int userId) throws DatabaseSelectHelperException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      ResultSet checkUserId = DatabaseSelector.getUserDetails(userId, connection);
      if (!checkUserId.next()) {
        checkUserId.close();
        throw new DatabaseIllegalGet("The entered userId does not exist in the database");
      }
      
      ResultSet inactiveAccounts = DatabaseSelector.getUserInactiveAccounts(userId, connection);
      List<Integer> accountIds = new ArrayList<Integer>();
      
      while (inactiveAccounts.next()) {
        accountIds.add(inactiveAccounts.getInt("ID"));
      }
      
      connection.close();
      return accountIds;
    } catch (SQLException | DatabaseIllegalGet e) {
      try {
        connection.close();
      } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      throw new DatabaseSelectHelperException(e.getMessage());
    }
  }

  @Override
  public List<Account> getAccounts() throws DatabaseSelectHelperException {
    try {
      List<User> users = this.getUsersDetails();
      List<Account> accounts = new ArrayList<Account>();
      for (User user: users) {
        List<Integer> userActiveAccounts = this
            .getUserActiveAccounts(user.getId());
        
        for (Integer i : userActiveAccounts) {
          Account currentAccount = new AccountImpl();
          currentAccount.setAccountId(i.intValue());
          currentAccount.setUserId(user.getId());
          currentAccount.setActive(true);
          try {
            currentAccount.setItemMap(this.getAccountDetails(i.intValue()));
          } catch (DatabaseSelectHelperException e) {
            currentAccount.setItemMap(new HashMap<Integer, Integer>());
          }
          accounts.add(currentAccount);
        }
        
        List<Integer> userInactiveAccounts = this
            .getUserInactiveAccounts(user.getId());
        
        for (Integer i : userInactiveAccounts) {
          Account currentAccount = new AccountImpl();
          currentAccount.setAccountId(i.intValue());
          currentAccount.setUserId(user.getId());
          currentAccount.setActive(false);
          try {
            currentAccount.setItemMap(this.getAccountDetails(i.intValue()));
          } catch (DatabaseSelectHelperException e) {
            currentAccount.setItemMap(null);
          }
          accounts.add(currentAccount);
        }
      }
      
      return accounts;
    } catch (SQLException | BuildTypeException | UserSetException e) {
      throw new DatabaseSelectHelperException(e.getMessage());
    }
  }

  @Override
  public HashMap<Integer, String> getPasswords() throws DatabaseSelectHelperException {
    List<User> users;
    try {
      users = this.getUsersDetails();
      HashMap<Integer, String> passwords = new HashMap<Integer, String>();
      
      for (User user : users) {
        passwords.put(new Integer(user.getId()), this.getPassword(user.getId()));
      }
      
      return passwords;
    } catch (SQLException | BuildTypeException | UserSetException e) {
      throw new DatabaseSelectHelperException(e.getMessage());
    }
  }
}
