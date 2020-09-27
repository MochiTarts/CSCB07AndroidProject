package com.test.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;
import com.test.users.DatabaseInserterExtender;
import com.test.users.DatabaseSelectorExtender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
  private static User user;
  
  private static String defaultUserName = "Name";
  private static String defaultUserAddress = "123 ABC";
  private static String defaultUserPassword = "password27";
  private static int defaultUserAge = 10;
  private static int validUserId;
  
  @BeforeClass
  public static void initTests() {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    
    boolean userExists = false;
    try {
      ResultSet results = DatabaseSelectorExtender.getUsersDetails(connection);
      while (results.next()) {
        if (results.getString("NAME").equals(defaultUserName)) {
          userExists = true;
          validUserId = results.getInt("ID");
          defaultUserPassword = DatabaseSelectorExtender.getPassword(validUserId, connection);
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
    
    user = new Employee(validUserId, defaultUserName, defaultUserAge, defaultUserAddress);
    
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void authenticate_validPassword() throws Exception {
    boolean authenticated = user.authenticate(defaultUserPassword);
    
    assertTrue(authenticated);
  }
  
  @Test
  public void authenticate_nonMatchingPassword() throws Exception {
    boolean authenticated = user.authenticate("does not match");
    
    assertFalse(authenticated);
  }
  
  @Test
  public void authenticate_emptyPassword() throws Exception {
    boolean authenticated = user.authenticate("");
    
    assertFalse(authenticated);
  }
  
  @Test
  public void authenticate_nullPassword() throws Exception {
    boolean authenticated = user.authenticate("");
    
    assertFalse(authenticated);
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void authenticate_userNotInDb() throws Exception {
    User user2 = new Customer(Integer.MAX_VALUE, "Bob Smith", 23, "123273");
    
    boolean authenticated = user2.authenticate("password");
    
    assertFalse(authenticated);
  }
  
  @Test(expected = DatabaseSelectHelperException.class)
  public void authenticate_differentUserSamePassword() throws Exception {
    User user2 = new Customer(2, "Bob Smith", 23, "123273");
    
    boolean authenticated = user2.authenticate(defaultUserPassword);
    
    assertFalse(authenticated);
  }
}
