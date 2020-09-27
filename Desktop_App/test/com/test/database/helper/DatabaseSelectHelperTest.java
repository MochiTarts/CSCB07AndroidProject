package com.test.database.helper;

import static org.junit.Assert.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.users.User;

public class DatabaseSelectHelperTest extends DatabaseSelectHelper {

  private static int validRoleId;
  private static int validUserId;
  
  private static String defaultRoleName = "ADMIN";
  
  private static String defaultUserName = "Name";
  private static String defaultUserAddress = "123 ABC";
  private static String defaultUserPassword = "password";
  private static int defaultUserAge = 10;
  private static int defaultUserRole;
  
  private static String defaultItemName;
  private static int validItemId = 1;
  private static BigDecimal defaultPrice = new BigDecimal("12.00");
  
  private static int defaultQuantity = 100;
  private static int validInventoryId = 1;
  
  private static Connection connection;
  
  @BeforeClass
  public static void setUp() throws ConnectionFailedException, DatabaseInsertException {
    connection = DatabaseDriverExtender.connectOrCreateDataBase();
    //DatabaseDriverExtender.initialize(connection);
    
    boolean adminExists = false;
    boolean employeeExists = false;
    boolean customerExists = false;
    
    try {
      ResultSet results = DatabaseSelectorExtender.getRoles(connection);
      while (results.next()) {
        if (results.getString("NAME").equals("ADMIN")) {
          adminExists = true;
        } else if (results.getString("NAME").equals("EMPLOYEE")) {
          employeeExists = true;
        } else if (results.getString("NAME").contentEquals("CUSTOMER")) {
          customerExists = true;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (!adminExists) {
      try {
        DatabaseInserterExtender.insertRole("ADMIN", connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    if (!employeeExists) {
      try {
        DatabaseInserterExtender.insertRole("EMPLOYEE", connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    if (!customerExists) {
      try {
        DatabaseInserterExtender.insertRole("CUSTOMER", connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
    boolean userExists = false;
    
    try {
      ResultSet results = DatabaseSelectorExtender.getUserDetails(1, connection);
      if (results.next()) {
        if (results.getString("NAME").equals("Cindy")) {
          userExists = true;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (!userExists) {
      try {
        DatabaseInserterExtender.insertNewUser("Cindy", 14, "Address", "password", connection);
        DatabaseInserterExtender.insertUserRole(1, 3, connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
    userExists = false;
    
    try {
      ResultSet results = DatabaseSelectorExtender.getUserDetails(2, connection);
      if (results.next()) {
        if (results.getString("NAME").equals("Jenny")) {
          userExists = true;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (!userExists) {
      try {
        DatabaseInserterExtender.insertNewUser("Jenny", 19, "Address", "password", connection);
        DatabaseInserterExtender.insertUserRole(2, 1, connection);
        DatabaseInserterExtender.insertAccount(2, connection);
        DatabaseInserterExtender.insertAccountLine(1, 2, 100, connection);
        DatabaseInserterExtender.insertAccountLine(1, 3, 100, connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
    userExists = false;
    
    try {
      ResultSet results = DatabaseSelectorExtender.getUserDetails(3, connection);
      if (results.next()) {
        if (results.getString("NAME").equals("Daniel")) {
          userExists = true;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (!userExists) {
      try {
        DatabaseInserterExtender.insertNewUser("Daniel", 12, "Address", "password", connection);
        DatabaseInserterExtender.insertUserRole(3, 3, connection);
        DatabaseInserterExtender.insertAccount(3, connection);
        DatabaseInserterExtender.insertAccount(3, connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
    userExists = false;
    
    try {
      ResultSet results = DatabaseSelectorExtender.getUserDetails(4, connection);
      if (results.next()) {
        if (results.getString("NAME").equals("Athena")) {
          userExists = true;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (!userExists) {
      try {
        DatabaseInserterExtender.insertNewUser("Athena", 19, "Canada", "password", connection);
        DatabaseInserterExtender.insertUserRole(4, 2, connection);
        DatabaseInserterExtender.insertAccount(4, connection);
        DatabaseInserterExtender.insertAccountLine(4, 1, 100, connection);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
  }
  
  @Test
  public void testGetRoles_standard() throws Exception {
    List<Integer> testResults = DatabaseSelectAdapter.getRoleIds();
    
    List<Integer> expectedResults = new ArrayList<>();
    expectedResults.add(1);
    expectedResults.add(2);
    expectedResults.add(3);
    
    assertEquals(expectedResults, testResults);
  }
  
  @Test
  public void testGetRoleName_standardRoleId() throws Exception {
    assertEquals("CUSTOMER", DatabaseSelectAdapter.getRoleName(3));
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetRoleName_invalidRoleId() throws Exception {
    DatabaseSelectAdapter.getRoleName(0);
  }
  
  @Test
  public void testGetUserRoleId_standardUserId() throws Exception {
    assertEquals(3, DatabaseSelectAdapter.getUserRoleId(1));
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetUserRoleId_invalidUserId() throws Exception {
    DatabaseSelectAdapter.getUserRoleId(5);
  }
  
  @Test
  public void testGetUsersByRole_standardRoleId() throws Exception {
    List<Integer> expectedResults = new ArrayList<>();
    expectedResults.add(1);
    expectedResults.add(3);
    
    assertEquals(expectedResults, DatabaseSelectAdapter.getUsersByRole(3));
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetUsersByRole_invalidRoleID() throws Exception {
    DatabaseSelectAdapter.getUsersByRole(5);
  }
  
  @Test
  public void testGetUsersDetails_standard() throws Exception {
    List<Integer> expectedResults = new ArrayList<>();
    expectedResults.add(1);
    expectedResults.add(2);
    expectedResults.add(3);
    expectedResults.add(4);
    
    List<Integer> testResults = new ArrayList<>();
    List<User> results = DatabaseSelectAdapter.getUsersDetails();
    int i = 0;
    while(i < results.size()) {
      testResults.add(results.get(i).getId());
      i++;
    }
    
    assertEquals(expectedResults, testResults);
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetUsersByRole_invalidRoleId() throws Exception {
    DatabaseSelectAdapter.getUsersByRole(4);
  }
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetUserAccountsInt_UserIdDoesNoteExist()
      throws SQLException, DatabaseSelectHelperException {
    DatabaseSelectAdapter.getUserAccounts(0);
  }

  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetUserAccounts_UserExists_NoAccount()
      throws DatabaseInsertException, SQLException, DatabaseSelectHelperException {
    DatabaseSelectAdapter.getUserAccounts(1);
  }

  @Test
  public void testGetUserAccounts_UserExists_OneAccount() throws Exception {
    List<Integer> testResults = DatabaseSelectAdapter.getUserAccounts(2);
    
    List<Integer> expectedResults = new ArrayList<>();
    expectedResults.add(1);
    
    assertEquals(expectedResults, testResults);
  }
  
  @Test
  public void testGetUserAccounts_UserExists_ManyAccounts() throws Exception {
    List<Integer> testResults = DatabaseSelectAdapter.getUserAccounts(3);
    
    List<Integer> expectedResults = new ArrayList<>();
    expectedResults.add(2);
    expectedResults.add(3);
    
    assertEquals(expectedResults, testResults);
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void testGetAccountDetails_AccountDoesNotExist() throws Exception {
    DatabaseSelectAdapter.getAccountDetails(0);
  }
  
  @Test
  public void testGetAccountDetails_AccountExists_OneItem() throws Exception {
    HashMap<Integer, Integer> testResults = DatabaseSelectAdapter.getAccountDetails(4);
    
    HashMap<Integer, Integer> expectedResults = new HashMap<>();
    expectedResults.put(1, 100);
    
    assertEquals(expectedResults, testResults);
  }
  
  @Test
  public void testGetAccountDetails_AccountExists_ManyItems() throws Exception {
    HashMap<Integer, Integer> testResults = DatabaseSelectAdapter.getAccountDetails(1);
    
    HashMap<Integer, Integer> expectedResults = new HashMap<>();
    expectedResults.put(2, 100);
    expectedResults.put(3, 100);
    
    assertEquals(expectedResults, testResults);
  }

  @AfterClass
  public static void tearDown() throws SQLException, IOException {
    connection.close();
  }
}