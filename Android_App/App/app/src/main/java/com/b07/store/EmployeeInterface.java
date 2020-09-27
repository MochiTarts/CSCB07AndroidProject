package com.b07.store;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.Unauthenticated;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Roles;
import com.b07.users.User;
import java.sql.SQLException;
import java.util.List;

public class EmployeeInterface implements EmployeeMenuInterface {

  private User currentEmployee;
  private Inventory inventory;
  
  /**
   * Standard constructor for EmployeeInterfaces. The employee must be authenticated to
   * obtain an interface.
   * @param employee The employee who owns the interface.
   * @param inventory the store's inventory.
   * @throws Unauthenticated if the Employee is not authenticated.
   */
  public EmployeeInterface(User employee, Inventory inventory) throws Unauthenticated {
    if (!employee.getAuthenticated()) {
      throw new Unauthenticated("The employee must be authenticated to use the interface.");
    }
    
    this.currentEmployee = employee;
    this.inventory = inventory;
  }
  
  /**
   * Constructor for EmployeeInterface if the active employee is not known.
   * @param inventory the store's inventory.
   */
  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
  }

  public void setCurrentEmployee(User employee) {
    this.currentEmployee = employee;
  }
  
  /**
   * Returns true if there is an employee using the interface, false otherwise.
   * @return true if there is an active employee, false otherwise.
   */
  public boolean hasCurrentEmployee() {
    return this.currentEmployee != null;
  }
  
  /**
   * Sets the amount of a given Item in the inventory. Returns true if successful, false otherwise. 
   * @param item the item to be updated.
   * @param quantity the new quantity of item.
   */
  public boolean restockInventory(Item item, int quantity) {
    this.inventory.getItemMap().replace(item, quantity);
    return this.inventory.getItemMap().get(item) == quantity;
  }
  
  /**
   * Creates a Customer and adds them to the database.
   * @param name the name of the Customer.
   * @param age the age of the Customer.
   * @param address the address of the Customer.
   * @param password the password of the Customer.
   * @return the userId of the new Customer.
   * @throws DatabaseInsertHelperException if the Customer cannot be put in the database.
   */
  public int createCustomer(String name, int age, String address, String password)
      throws DatabaseInsertHelperException {
    try {
      int customerId = DatabaseInsertAdapter.insertNewUser(name, age, address, password);
      List<Integer> roleIds = DatabaseSelectAdapter.getRoleIds();
      int roleId = -1;
      for (int i = 0; i < roleIds.size(); i++) {
        if (DatabaseSelectAdapter.getRoleName(roleIds.get(i).intValue())
            .equals(Roles.CUSTOMER.toString())) {
          roleId = roleIds.get(i).intValue();
        }
      }
      if (roleId < 0) {
        throw new DatabaseSelectHelperException("Invalid Role.");
      }
      DatabaseInsertAdapter.insertUserRole(customerId, roleId);
      return customerId;
    } catch (DatabaseInsertException
        | SQLException
        | DatabaseInsertHelperException | DatabaseSelectHelperException e) {
      System.out.println(e.getMessage());
      throw new DatabaseInsertHelperException("Failed to create new customer");
    }
  }
  
  /**
   * Creates an Employee and adds them to the database.
   * @param name the name of the Employee.
   * @param age the age of the Employee.
   * @param address the address of the Employee.
   * @param password the password of the Employee.
   * @return the userId of the new Employee.
   * @throws DatabaseInsertHelperException if the Employee cannot be put in the database.
   */
  public int createEmployee(String name, int age, String address, String password)
      throws DatabaseInsertHelperException {
    try {
      int employeeId = DatabaseInsertAdapter.insertNewUser(name, age, address, password);
      
      List<Integer> roles = DatabaseSelectAdapter.getRoleIds();
      int roleId = 0;
      
      for (Integer role : roles) {
        if (DatabaseSelectAdapter.getRoleName(role).equals(Roles.EMPLOYEE.toString())) {
          roleId = role.intValue();
        }
      }
      
      if (roleId == 0) {
        throw new DatabaseInsertHelperException("No Admin Role in the database.");
      }
      
      DatabaseInsertAdapter.insertUserRole(employeeId, roleId);
      return employeeId;
    } catch (DatabaseInsertException
        | SQLException
        | DatabaseInsertHelperException | DatabaseSelectHelperException e) {
      throw new DatabaseInsertHelperException("Failed to create new employee.");
    }
  }
  
  /**
   * Creates an account for the given user and stores it in the database.
   * @param userId the Id of the user to be stored.
   * @return the accountId of the user if successful, -1 otherwise.
   */
  public int createAccount(int userId) {
    try {
      return DatabaseInsertAdapter.insertAccount(userId, true);
    } catch (DatabaseInsertException | SQLException 
        | DatabaseInsertHelperException e) {
      System.out.println("Failed to insert account");
      return -1;
    }
  }
}
