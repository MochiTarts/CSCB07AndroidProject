package com.b07.builders;

import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.UserSetException;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;

/**
 * A class which constructs Users efficiently and easily without having 
 * to memorize constructor orders.
 * @author dawson
 *
 */
public class UserBuilderImpl implements UserBuilder {
  private User user;
  
  /**
   * Initializes the private User to some base fields.
   * @param role The Role of the User from the Roles enum.
   * @param authenticated Whether the user has logged in or not.
   * @throws BuildTypeException If The declared Role does not belong to Roles.
   */
  public UserBuilderImpl(Roles role, boolean authenticated) throws BuildTypeException {
    if (role == Roles.ADMIN) {
      user = new Admin(1, "Default Name", 1, "Default Address", authenticated);
    } else if (role == Roles.CUSTOMER) {
      user = new Customer(1, "Default Name", 1, "Default Address", authenticated);
    } else if (role == Roles.EMPLOYEE) {
      user = new Employee(1, "Default Name", 1, "Default Address", authenticated);
    } else {
      throw new BuildTypeException();
    }
  }

  @Override
  public UserBuilder id(int id) throws UserSetException {
    this.user.setId(id);
    return this;
  }

  @Override
  public UserBuilder name(String name) throws UserSetException {
    this.user.setName(name);
    return this;
  }

  @Override
  public UserBuilder age(int age) throws UserSetException {
    this.user.setAge(age);
    return this;
  }

  @Override
  public UserBuilder address(String address) throws UserSetException {
    this.user.setAddress(address);
    return this;
  }

  @Override
  public User build() {
    return this.user;
  }

}
