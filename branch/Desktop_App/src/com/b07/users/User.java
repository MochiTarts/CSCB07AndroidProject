package com.b07.users;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.UserSetException;
import com.b07.security.PasswordHelpers;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class User implements Serializable {
  /**
   * Generated Serial id for User.
   */
  private static final long serialVersionUID = -8651432902173600419L;
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  public abstract int getId();

  public abstract void setId(int id) throws UserSetException;

  public abstract String getName();

  public abstract void setName(String name);

  public abstract int getAge();

  public abstract void setAge(int age) throws UserSetException;

  public abstract String getAddress();

  public abstract void setAddress(String address) throws UserSetException;

  public abstract int getRoleId();
  
  /**
   * Checks if the given password matches the password stored for the User in the database.
   * 
   * @param password the password to be checked.
   * @return true if the password matches the one given in the database, false if it does not.
   * @throws SQLException if something goes wrong when accessing the database.
   * @throws DatabaseSelectHelperException if the password cannot be found.
   */
  public final boolean authenticate(String password)
      throws SQLException, DatabaseSelectHelperException {
    String dbPassword = DatabaseSelectAdapter.getPassword(this.getId());
    this.authenticated = PasswordHelpers.comparePassword(dbPassword, password);
    return authenticated;
  }
  
  public boolean getAuthenticated() {
    return this.authenticated;
  }
}