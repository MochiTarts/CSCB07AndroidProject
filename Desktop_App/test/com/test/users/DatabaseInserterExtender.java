package com.test.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.b07.database.DatabaseInserter;
import com.b07.exceptions.DatabaseInsertException;

public class DatabaseInserterExtender extends DatabaseInserter {
  /**
   * Use this to insert new roles into the database.
   * @param role the new role to be added.
   * @param connection the database.
   * @return the id of the role that was inserted.
   * @throws DatabaseInsertException  on failure.
   */
  protected static int insertRole(String role, Connection connection) 
      throws DatabaseInsertException {
    return DatabaseInserter.insertRole(role, connection);
  }
  
  /**
   * Use this to insert a new user.
   * @param name the user's name.
   * @param age the user's age.
   * @param address the user's address.
   * @param password the user's password (not hashsed).
   * @param connection the database connection.
   * @return the user id
   * @throws DatabaseInsertException if there is a failure on the insert
   */
  protected static int insertNewUser(String name, int age, String address,
        String password, Connection connection) throws DatabaseInsertException {
    return DatabaseInserter.insertNewUser(name, age, address, password, connection);
  }
  
  /**
   * Insert a relationship between a user and a role.
   * @param userId the id of the user.
   * @param roleId the role id of the user.
   * @param connection the database connection.
   * @return the unique relationship id.
   * @throws DatabaseInsertException if there is a failure on the insert.
   */
  protected static int insertUserRole(int userId, int roleId, 
      Connection connection) throws DatabaseInsertException {
    return DatabaseInserter.insertUserRole(userId, roleId, connection);
  }
  
  /**
   * insert an item into the database.
   * @param name the name of the item.
   * @param price the price of the item.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertItem(String name, BigDecimal price, Connection connection) 
      throws DatabaseInsertException {
    return DatabaseInserter.insertItem(name, price, connection);
  }
  
  /**
   * insert inventory into the database.
   * @param itemId the id of the item.
   * @param quantity the quantity of the item.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertInventory(int itemId, int quantity,
      Connection connection) throws DatabaseInsertException {
    return DatabaseInserter.insertInventory(itemId, quantity, connection);
  }
  
  /**
   * insert a sale into the database.
   * @param userId the id of the user.
   * @param totalPrice the total price of the sale.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertSale(int userId, BigDecimal totalPrice, Connection connection) 
      throws DatabaseInsertException {
    return DatabaseInserter.insertSale(userId, totalPrice, connection);
  }
  
  /**
   * insert an itemized record for a specifc item in a sale.
   * @param saleId the id of the sale.
   * @param itemId the id of the item.
   * @param quantity the number of the item purchased.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertItemizedSale(int saleId, int itemId, int quantity, 
      Connection connection) throws DatabaseInsertException {
    return DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
  }
  
  /**
   * Insert a new account into the database.
   * @param userId the userId for the user of the account.
   * @param connection the connection to the database.
   * @return the id of the account.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertAccount(int userId, Connection connection) 
      throws DatabaseInsertException {
    return DatabaseInserter.insertAccount(userId, connection);
  }
  
  /**
   * insert a single item into a given account for recovery next login.
   * @param accountId the id of the account.
   * @param itemId the item to be inserted.
   * @param quantity the quantity of that item.
   * @param connection connection to the database.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertAccountLine(int accountId, int itemId, int quantity, 
      Connection connection) throws DatabaseInsertException {
    return DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
  }
}
