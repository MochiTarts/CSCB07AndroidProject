package com.test.database.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.b07.database.DatabaseSelector;
import com.b07.database.DatabaseUpdater;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseUpdateHelperException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseUpdateHelperTest extends DatabaseUpdateHelper {
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
    
    defaultUserRole = validRoleId;
    
    try {
      ResultSet result = DatabaseSelectorExtender.getItem(validItemId, connection);
      result.next();
      defaultItemName = result.getString("NAME");
      System.out.println(defaultItemName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_standardCustomerName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("CUSTOMER", validRoleId);
    
    assertEquals("CUSTOMER", DatabaseSelectorExtender.getRole(validRoleId, connection));
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateRoleName_standardAdminName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName("CUSTOMER", validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("ADMIN", validRoleId);
    
    assertEquals("ADMIN", DatabaseSelectorExtender.getRole(validRoleId, connection));
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_sameName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName(defaultRoleName, validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_lowerName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("customer", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_mixedCaseName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("CuStOmEr", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_notInRolesName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("Person", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_numberedName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("324", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_leadSpaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("   CUSTOMER", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_trailingSpaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("CUSTOMER   ", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_spaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("   ", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_emptyName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("", validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_nullName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName(null, validRoleId);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateRoleName_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    DatabaseUpdateAdapter.updateRoleName("ADMIN", -1);
    
    DatabaseUpdater.updateRoleName(defaultRoleName, validRoleId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserName_standardName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("Tom", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("Tom", results.getString("NAME"));
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserName_withWhitespaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("Bob Smith", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("Bob Smith", results.getString("NAME"));
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserName_whitespaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("     ", validUserId);
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserName_leadingWhitespaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("    Bob Smith", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("    Bob Smith", results.getString("NAME"));
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserName_trailingWhiteSpaceName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("Bob Smith     ", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("Bob Smith     ", results.getString("NAME"));
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserName_emptyName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("", validUserId);
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserName_nullName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName(null, validUserId);
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserName_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserName("Name", -1);
    
    DatabaseUpdater.updateUserName(defaultUserName, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAge_standardAge() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(65, validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals(65, results.getInt("AGE"));
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAge_zeroAge() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(0, validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals(0, results.getInt("AGE"));
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAge_negativeAge() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(-1, validUserId);
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAge_maxIntAge() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(Integer.MAX_VALUE, validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals(Integer.MAX_VALUE, results.getInt("AGE"));
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAge_minIntAge() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(Integer.MIN_VALUE, validUserId);
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAge_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAge(20, -1);
    
    DatabaseUpdater.updateUserAge(defaultUserAge, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_standardAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("Typical Address 25", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("Typical Address 25", results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_nonNumericAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("Typical Address", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("Typical Address", results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_nonAlphaAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("253487", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("253487", results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_leadingWhitespaceAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("    253487", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("    253487", results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_trailingWhitespaceAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("253487    ", validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("253487    ", results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAddress_allWhitespaceAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("    ", validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAddress_emptyAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("", validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserAddress_atMaxValidCharAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("01234567890123456789012345678901234567890123456789012"
                                              + "34567890123456789012345678901234567890123456789",
                                              validUserId);
    
    ResultSet results = DatabaseSelectorExtender.getUserDetails(validUserId, connection);
    
    results.next();
    
    assertEquals("01234567890123456789012345678901234567890123456789012"
                   + "34567890123456789012345678901234567890123456789", 
                   results.getString("ADDRESS"));
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAddress_aboveMaxCharAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("01234567890123456789012345678901234567890123456789012"
                                              + "345678901234567890123456789012345678901234567890",
                                              validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAddress_nullAddress() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress(null, validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserAddress_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserAddress("123 ABC", -1);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateUserRole_standardRole() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserRole(defaultUserRole, validUserId, connection);
    
    List<Integer> roles = DatabaseSelectAdapter.getRoleIds();
    
    int roleId = roles.get(roles.size() - 1);
    
    DatabaseUpdateAdapter.updateUserRole(roleId, validUserId);
    
    int userRole = DatabaseSelectorExtender.getUserRole(validUserId, connection);
    
    assertEquals(roles.get(roles.size() - 1).intValue(), userRole);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserRole_notInListRole() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserRole(defaultUserRole, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserRole(Integer.MAX_VALUE, validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserRole_negativeRole() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserRole(defaultUserRole, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserRole(-1, validUserId);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateUserRole_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateUserRole(defaultUserRole, validUserId, connection);
    
    DatabaseUpdateAdapter.updateUserRole(1, -1);
    
    DatabaseUpdater.updateUserAddress(defaultUserAddress, validUserId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemName_nameNotInEnum() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemName("NAME", validItemId);
    
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemName_nameInDB() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemName("FISHING_ROD", validItemId);
    
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemName_emptyName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemName("", validItemId);
    
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemName_nullName() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemName(null, validItemId);
    
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemName_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemName("FISHING_ROD", -1);
    
    DatabaseUpdater.updateItemName(defaultItemName, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateItemPrice_standardPrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(new BigDecimal("21.34"), validItemId);
    
    ResultSet item = DatabaseSelectorExtender.getItem(validItemId, connection);
    
    item.next();
    
    assertEquals(new BigDecimal("21.34"), new BigDecimal(item.getString("PRICE")));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemPrice_zeroPrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(new BigDecimal("0"), validItemId);
    
    ResultSet item = DatabaseSelectorExtender.getItem(validItemId, connection);
    
    item.next();
    
    assertEquals(new BigDecimal("0"), new BigDecimal(item.getString("PRICE")));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateItemPrice_verySmallPrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(new BigDecimal("0.00002"), validItemId);
    
    ResultSet item = DatabaseSelectorExtender.getItem(validItemId, connection);
    
    item.next();
    
    assertEquals(new BigDecimal("0.00002"), new BigDecimal(item.getString("PRICE")));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateItemPrice_samePrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(defaultPrice, validItemId);
    
    ResultSet item = DatabaseSelectorExtender.getItem(validItemId, connection);
    
    item.next();
    
    assertEquals(defaultPrice, new BigDecimal(item.getString("PRICE")));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemPrice_negativePrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(new BigDecimal("-1.34"), validItemId);
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemPrice_nullPrice() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(null, validItemId);
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateItemPrice_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    DatabaseUpdateAdapter.updateItemPrice(new BigDecimal("10"), -2);
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateInventoryQuantity_standardQuantity() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateInventoryQuantity(defaultQuantity, validInventoryId, connection);
    
    DatabaseUpdateAdapter.updateInventoryQuantity(55, validInventoryId);
    
    assertEquals(55, DatabaseSelectorExtender.getInventoryQuantity(validItemId, connection));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateInventoryQuantity_zeroQuantity() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateInventoryQuantity(defaultQuantity, validInventoryId, connection);
    
    DatabaseUpdateAdapter.updateInventoryQuantity(0, validInventoryId);
    
    assertEquals(0, DatabaseSelectorExtender.getInventoryQuantity(validItemId, connection));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test
  public void updateInventoryQuantity_maxIntQuantity() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateInventoryQuantity(defaultQuantity, validInventoryId, connection);
    
    DatabaseUpdateAdapter.updateInventoryQuantity(Integer.MAX_VALUE, validInventoryId);
    
    assertEquals(Integer.MAX_VALUE, DatabaseSelectorExtender.getInventoryQuantity(validItemId,
                                                                                    connection));
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateInventoryQuantity_negativeQuantity() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateInventoryQuantity(defaultQuantity, validInventoryId, connection);
    
    DatabaseUpdateAdapter.updateInventoryQuantity(-20, validInventoryId);
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
  
  @Test(expected = DatabaseUpdateHelperException.class)
  public void updateInventoryQuantity_invalidId() throws Exception {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    DatabaseUpdater.updateInventoryQuantity(defaultQuantity, validInventoryId, connection);
    
    DatabaseUpdateAdapter.updateInventoryQuantity(20, -2);
    
    DatabaseUpdater.updateItemPrice(defaultPrice, validItemId, connection);
    
    connection.close();
  }
}
