package com.b07.users;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.DatabaseUpdateHelperException;
import com.b07.exceptions.UserSetException;
import java.sql.SQLException;

public class Admin extends User {

  /**
   * Generated serial id for Users.
   */
  private static final long serialVersionUID = 1124150882384563860L;
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean alreadySet = false;
  
  /**
   * Standard Admin constructor, will automatically set authenticated to false.
   * @param id a valid user id from the database which corresponds to an Admin.
   * @param name the user's name.
   * @param age the user's age, must be greater than zero.
   * @param address the user's address, must be less than or equal to 100 characters in length.
   */
  public Admin(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
  }
  
  /**
   * Admin constructor for when the state of authentication is known.
   * @param id a valid user id from the database which corresponds to an Admin.
   * @param name the user's name.
   * @param age the user's age, must be greater than zero.
   * @param address the user's address, must be less than or equal to 100 characters in length.
   * @param authenticated determines whether the user will be initialized with authenticated
   *        set to true. User must have a password in the database.
   */
  public Admin(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    if (authenticated) {
      try {
        authenticate(DatabaseSelectAdapter.getPassword(id));
      } catch (SQLException | DatabaseSelectHelperException e) {
        // Cannot authenticate the user, likely due to invalid id. This should be
        // checked before calling the constructor.
      }
    }
  }
  
  /**
   * Promotes an employee to an Admin, removing their old role.
   * @param employee the employee to be promoted.
   * @return true if successful, false if unsuccessful.
   * @throws SQLException if something goes wrong with the database.
   * @throws DatabaseUpdateHelperException if the role cannot be updated.
   */
  public boolean promoteEmployee(Employee employee)
      throws SQLException,
          DatabaseUpdateHelperException {
    int userId = employee.getId();
    System.out.println(userId);
    boolean success = DatabaseUpdateAdapter.updateUserRole(1, userId);
    System.out.println(success);
    return success;
  }

  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return this.id;
  }

  @Override
  public void setId(int id) throws UserSetException {
    // TODO Auto-generated method stub
    if (!alreadySet) {
      this.id = id;
      this.alreadySet = true;
    } else {
      throw new UserSetException("The id is already set for this user and cannot be changed");
    }
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return this.name;
  }

  @Override
  public void setName(String name) {
    // TODO Auto-generated method stub
    this.name = name;
  }

  @Override
  public int getAge() {
    // TODO Auto-generated method stub
    return this.age;
  }

  @Override
  public void setAge(int age) throws UserSetException {
    // TODO Auto-generated method stub
    if (this.age >= 0) {
      this.age = age;
    } else {
      throw new UserSetException("Age must be 0 or greater");
    }
  }

  @Override
  public String getAddress() {
    // TODO Auto-generated method stub
    return this.address;
  }

  @Override
  public void setAddress(String address) throws UserSetException {
    // TODO Auto-generated method stub
    if (this.address != null
        && this.address.trim().length() <= 100
        && !this.address.trim().equals("")) {
      this.address = address;
    } else {
      throw new UserSetException("Address does not follow guidelines");
    }
  }

  @Override
  public int getRoleId() {
    try {
      roleId = DatabaseSelectAdapter.getUserRoleId(this.id);
    } catch (SQLException | DatabaseSelectHelperException e) {
      return -1;
    }
    return this.roleId;
  }
}
