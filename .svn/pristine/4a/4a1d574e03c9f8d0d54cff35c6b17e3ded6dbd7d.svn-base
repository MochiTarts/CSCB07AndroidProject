package com.b07.store;

import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.inventory.Item;
import com.b07.users.User;

public interface EmployeeMenuInterface {
  public void setCurrentEmployee(User employee);
  
  public boolean hasCurrentEmployee();
  
  public boolean restockInventory(Item item, int quantity);
  
  public int createCustomer(String name, int age, String address, String password)
      throws DatabaseInsertHelperException;
  
  public int createEmployee(String name, int age, String address, String password)
      throws DatabaseInsertHelperException;
  
  public int createAccount(int userId);
}
