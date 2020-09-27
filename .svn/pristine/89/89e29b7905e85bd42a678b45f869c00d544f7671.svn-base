package com.b07.store;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.database.serializer.DatabaseSerializable;
import com.b07.database.serializer.DatabaseSerializableImpl;
import com.b07.database.serializer.DatabaseSerializer;
import com.b07.database.serializer.DatabaseSerializerImpl;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.inventory.StoreInventory;
import com.b07.store.ShoppingCartAccountManager;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesApplication {

  /**
   * This is the main method to run your entire program! Follow the "Pulling it together"
   * instructions to finish this off.
   *
   * @param argv unused.
   */
  
  public static void main(String[] argv) {
    DatabaseInsertAdapter.setInserter(new DatabaseInsertHelper());
    DatabaseSelectAdapter.setSelector(new DatabaseSelectHelper());
    DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelper());

    int userId;
    String name;
    int userAge;
    String userAddress;
    String adminPassword;
    String employeePassword;
    String customerPassword;
    List<Integer> newEmployees = new ArrayList<>();
    Inventory inventory = new StoreInventory();
    HashMap<Item, Integer> restockInventory;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    int checkRunOnce = 0;

    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("NOOO");
    }
    
    boolean exit = false;
    
    while (!exit) {
      try {
        // TODO Check what is in argv
        // If it is -1
        /*
         * TODO This is for the first run only!
         * Add this code:
         * DatabaseDriverExtender.initialize(connection);
         * Then add code to create your first account, an administrator with a password
         * Once this is done, create an employee account as well.
         *
         */
        if (argv.length > 0 && argv[0].equals("-1") && checkRunOnce == 0) {
          DatabaseDriverExtender.initialize(connection);

          insertDefaults();

          System.out.println("Please enter your password for admin account: ");
          adminPassword = bufferedReader.readLine();
          System.out.println("Please enter your password for employee account: ");
          employeePassword = bufferedReader.readLine();

          try {
            int adminId = DatabaseInsertAdapter
                .insertNewUser("Admin", 19, "Address", adminPassword);
            int employeeId =
                DatabaseInsertAdapter.insertNewUser("Employee", 19, "Address", employeePassword);
            DatabaseInsertAdapter.insertUserRole(adminId, 1);
            DatabaseInsertAdapter.insertUserRole(employeeId, 2);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          
          System.out.println("Your id for this initial admin account is 1");
          System.out.println("Your id for this initial employee account is 2");

          checkRunOnce += 1;
          exit = true;
        } else if (argv.length > 0 && argv[0].equals("1")) {

          System.out.println("Please enter your id: ");
          int userId1 = -1;
          while (userId1 == -1) {
            try {
              userId1 = Integer.parseInt("0" + bufferedReader.readLine());
            } catch (NumberFormatException e) {
              System.out.println("The id must be a number.");
            }
          }
          System.out.println("Please enter your password: ");
          String adminPassword1 = bufferedReader.readLine();
          try {
            User admin = DatabaseSelectAdapter.getUserDetails(userId1);
            
            if (!DatabaseSelectAdapter.getRoleName(admin.getRoleId()).equals("ADMIN")) {
              System.out.println("User is not an admin, exiting program.");
              break;
            }
            
            if (!admin.authenticate(adminPassword1)) {
              System.out.println("Incorrect password");
              break;
            }
          } catch (Exception e) {
            System.out.println("Incorrect ID");
            System.out.println(e.getMessage());
            break;
          }

          User admin = DatabaseSelectAdapter.getUserDetails(userId1);
          
          String input = "";
          
          while (!exit) {
            System.out.println("Choose one of the following:\n"
                                 + "1. Promote Employee\n"
                                 + "2. Print Sales History\n"
                                 + "3. Display Active Accounts for a Customer\n"
                                 + "4. Display Inactive Accounts for a Customer\n"
                                 + "5. Exit\n"
                                 + "6. Serialize Database\n"
                                 + "7. Deserialize Database\n");
            input = bufferedReader.readLine();
            
            if (input.equals("1")) {
              List<Integer> employees = DatabaseSelectAdapter.getUsersByRole(2);
              int i = 0;
              while (i < employees.size()) {
                User employee = DatabaseSelectAdapter.getUserDetails(employees.get(i));
                System.out.println("Promote this employee?\n1 - Yes\n2 - No\n");
                System.out.println("Name: " + employee.getName());
                String choice = bufferedReader.readLine();
                if (choice.equals("1")) {
                  ((Admin) admin).promoteEmployee((Employee) employee);
                  employee = DatabaseSelectAdapter.getUserDetails(employees.get(i));
                } else {
                  System.out.println("Moving on then");
                }
                i++;
              }
            } else if (input.equals("2")) {
              SalesLog log = DatabaseSelectAdapter.getCompleteSales();
              System.out.println(log.toString());
            } else if (input.equals("3")) {
              System.out.println("Enter the Id of a customer.");
              String idInput = "";
              int customerId = 0;
              
              while (idInput.length() == 0) {
                idInput = bufferedReader.readLine();
                
                try {
                  customerId = Integer.parseInt(idInput);
                } catch (NumberFormatException e) {
                  idInput = "";
                  System.out.println("The input must be a number.");
                }
              }
              
              User customer;
              try {
                customer = DatabaseSelectAdapter.getUserDetails(customerId);
              } catch (DatabaseSelectHelperException e) {
                System.out.println("Unable to get customer information.");
                continue;
              }
              
              if (!DatabaseSelectAdapter.getRoleName(customer.getRoleId())
                  .equals(Roles.CUSTOMER.toString())) {
                System.out.println("This user is not a customer.");
                continue;
              }
              
              List<Integer> activeAccounts = new ArrayList<Integer>();
              try {
                activeAccounts = DatabaseSelectAdapter.getUserActiveAccounts(customerId);
              } catch (DatabaseSelectHelperException e) {
                System.out.println("Unable to get user accounts.");
                continue;
              }
              
              if (activeAccounts.size() == 0)  {
                System.out.println("This User does not have any active accounts.");
                continue;
              }
              
              System.out.println("Active Accounts for: " + customer.getName() + ", id " 
                  + customer.getId());
              for (Integer i : activeAccounts) {
                System.out.println("---------------------------------------------------");
                System.out.println("Account ID: " + i
                    + "\nStored Item: Quantity");
                try {
                  HashMap<Integer, Integer> accountMap = DatabaseSelectAdapter
                      .getAccountDetails(i.intValue());
                  
                  for (Integer itemId : accountMap.keySet()) {
                    System.out.println(DatabaseSelectAdapter.getItem(itemId).getName() + ": " 
                        + accountMap.get(itemId));
                  }
                } catch (DatabaseSelectHelperException e) {
                  System.out.println("No item information is available for this account,\n"
                      + "perhaps it is empty.");
                }
              }
            } else if (input.equals("4")) {
              System.out.println("Enter the Id of a customer.");
              String idInput = "";
              int customerId = 0;
              
              while (idInput.length() == 0) {
                idInput = bufferedReader.readLine();
                
                try {
                  customerId = Integer.parseInt(idInput);
                } catch (NumberFormatException e) {
                  idInput = "";
                  System.out.println("The input must be a number.");
                }
              }
              
              User customer;
              try {
                customer = DatabaseSelectAdapter.getUserDetails(customerId);
              } catch (DatabaseSelectHelperException e) {
                System.out.println("Unable to get customer information.");
                continue;
              }
              
              if (!DatabaseSelectAdapter.getRoleName(customer.getRoleId())
                  .equals(Roles.CUSTOMER.toString())) {
                System.out.println("This user is not a customer.");
                continue;
              }
              
              List<Integer> inactiveAccounts = new ArrayList<Integer>();
              try {
                inactiveAccounts = DatabaseSelectAdapter.getUserInactiveAccounts(customerId);
              } catch (DatabaseSelectHelperException e) {
                System.out.println("Unable to get user accounts.");
                continue;
              }
              
              if (inactiveAccounts.size() == 0)  {
                System.out.println("This User does not have any inactive accounts.");
                continue;
              }
              
              System.out.println("Inactive Accounts for: " + customer.getName() + ", id " 
                  + customer.getId());
              for (Integer i : inactiveAccounts) {
                System.out.println("---------------------------------------------------");
                System.out.println("Account ID: " + i
                    + "\nStored Item: Quantity");
                try {
                  HashMap<Integer, Integer> accountMap = DatabaseSelectAdapter
                      .getAccountDetails(i.intValue());
                  
                  for (Integer itemId : accountMap.keySet()) {
                    System.out.println(DatabaseSelectAdapter.getItem(itemId).getName() + ": " 
                        + accountMap.get(itemId));
                  }
                } catch (DatabaseSelectHelperException e) {
                  System.out.println("No item information is available for this account,\n"
                      + "perhaps it is empty.");
                }
              }
            } else if (input.equals("5")) {
              exit = true;
            } else if (input.equals("6")) {
              System.out.println("Enter the path of the location to serialize to,\n"
                  + "ending with the file separator character\n"
                  + "(i.e. C:\\users\\bob\\folderName\\ on windows.)\n"
                  + "For the default path, leave this field empty.");
              String path = bufferedReader.readLine();
              
              if (path.trim().length() == 0) {
                path = "";
              }
              
              System.out.println("Serializing database...");
              
              DatabaseSerializable toSerialize = new DatabaseSerializableImpl();
              if (toSerialize.pullDatabase()) {
                DatabaseSerializer serializer = new DatabaseSerializerImpl();
                String outputPathName = serializer
                    .serialize(toSerialize, path, "database_copy.ser");
                
                if (outputPathName.length() == 0) {
                  System.out.println("Failed to serialize database object.");
                } else {
                  System.out.println("Database serialized to " + outputPathName);
                }
              } else {
                System.out.println("Unable to retrieve Database.");
                continue;
              }
            } else if (input.equals("7")) {
              System.out.println("Enter the path of the location to deserialize from,\n"
                  + "ending with the file separator character\n"
                  + "(i.e. C:\\users\\bob\\folderName\\ on windows.)\n"
                  + "For the default path, leave this field empty.\n"
                  + "To deserialize the last backup, enter 'backup'");
              String path = bufferedReader.readLine();
              
              if (path.trim().length() == 0) {
                path = "";
              }
              
              System.out.println("Deserializing database...");
              
              DatabaseSerializer serializer = new DatabaseSerializerImpl();
              
              DatabaseSerializable deserializedDB;
              
              boolean backingUp = false;
              
              if (!path.equals("backup")) {
                deserializedDB =  (DatabaseSerializableImpl)serializer
                    .deserialize("database_copy.ser", path);
              } else {
                deserializedDB =  (DatabaseSerializableImpl)serializer
                    .deserialize("backup.ser", System.getProperty("user.dir") + "\\dbbackup\\");
                backingUp = true;
              }
              
              if (deserializedDB == null) {
                System.out.println("Unable to deserialize file, aborting process.");
                continue;
              }
              
              DatabaseSerializable backup = new DatabaseSerializableImpl();
              
              if (!backingUp) {
                System.out.println("Creating backup of current database...");
                
                backup = new DatabaseSerializableImpl();
                
                if (!(backup.pullDatabase() && !(serializer.serialize(backup, 
                    System.getProperty("user.dir") + "\\dbbackup\\", "backup.ser") == null))) {
                  System.out.println("Unable to backup old database, are you sure you "
                      + "want to continue?\n"
                      + "(If something goes wrong, the database may not be recovered)\n");
                  
                  String affirm = "";
                  
                  while (!(affirm.equals("Y") || affirm.equals("N"))) {
                    System.out.println("Enter Y/N");
                    
                    affirm = bufferedReader.readLine();
                  }
                  
                  if (affirm.equals("N")) {
                    System.out.println("Aborting process.");
                    continue;
                  } else {
                    System.out.println("Continuing process.");
                  }
                }
              }
              
              System.out.println("Clearing old database...");
              
              connection.close();
              DatabaseInsertAdapter.setInserter(null);
              DatabaseSelectAdapter.setSelector(null);
              DatabaseUpdateAdapter.setUpdater(null);
              
              if (!SalesApplication.clearDatabase()) {
                System.out.println("Unable to clear old database, aborting process.");
                DatabaseInsertAdapter.setInserter(new DatabaseInsertHelper());
                DatabaseSelectAdapter.setSelector(new DatabaseSelectHelper());
                DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelper());
                continue;
              }
              
              DatabaseInsertAdapter.setInserter(new DatabaseInsertHelper());
              DatabaseSelectAdapter.setSelector(new DatabaseSelectHelper());
              DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelper());
              connection = DatabaseDriverExtender.connectOrCreateDataBase();
              
              if (!initNewDb()) {
                System.out.println("Could not intialize a new database file,"
                    + "unfortunately, this does not support rollback.");
              }
              
              if (deserializedDB.storeDatabase()) {
                System.out.println("Deserialization complete.");
              } else {
                System.out.println("Unable to store deserialized database, reverting to\n"
                    + "previous database.");
                if (!backingUp && backup.storeDatabase()) {
                  System.out.println("Rollback sucessful.");
                } else {
                  System.out.println("Could not revert database to previous version.");
                }
                continue;
              }
            }
          }
        } else {
          String inputHere = "3";
          while (!inputHere.equals("0")) {
            System.out.println("1 - Employee Login\n2 - Customer Login\n0 - Exit");
            inputHere = bufferedReader.readLine();
            if (inputHere.equals("0")) {
              System.out.println("Exit");
              exit = true;
              break;
            } else if (inputHere.equals("1")) {
              System.out.println("Please enter your id: ");
              userId = Integer.parseInt("0" + bufferedReader.readLine());
              System.out.println("Please enter your password: ");
              employeePassword = bufferedReader.readLine();
              
              User employee = null;
              
              try {
                if (2 != DatabaseSelectAdapter.getUserRoleId(userId)) {
                  System.out.println("Not an employee");
                  exit = true;
                  break;
                }

                employee = DatabaseSelectAdapter.getUserDetails(userId);

                employee.authenticate(employeePassword);
                if (!employee.authenticate(employeePassword)) {
                  System.out.println("Incorrect password");
                  exit = true;
                  break;
                }
              } catch (Exception e) {
                System.out.println("Incorrect ID");
                e.printStackTrace();
                break;
              }

              EmployeeInterface employeeInterface =
                  new EmployeeInterface((Employee) employee, inventory);

              String employeeInterfaceInput = "";
              while (!employeeInterfaceInput.equals("6")) {
                System.out.println(
                    "1. Authenticate new employees\n2. Make new User\n3. "
                    + "Make new account\n4. Make new Employee\n5. Restock Inventory\n6. Exit");
                employeeInterfaceInput = bufferedReader.readLine();
                if (employeeInterfaceInput.equals("6")) {
                  System.out.println("End session");
                  exit = true;
                  break;
                } else if (employeeInterfaceInput.equals("1")) {
                  System.out.println("Please enter your id: ");
                  userId = Integer.parseInt("0" + bufferedReader.readLine());
                  System.out.println("Please enter your password: ");
                  employeePassword = bufferedReader.readLine();
                  
                  User newEmployee = null;
                  
                  try {
                    if (2 != DatabaseSelectAdapter.getUserRoleId(userId)) {
                      System.out.println("Not an employee");
                      exit = true;
                      continue;
                    }

                    newEmployee = DatabaseSelectAdapter.getUserDetails(userId);

                    newEmployee.authenticate(employeePassword);
                    if (!newEmployee.authenticate(employeePassword)) {
                      System.out.println("Incorrect password");
                      exit = true;
                      continue;
                    }
                  } catch (Exception e) {
                    System.out.println("Incorrect ID");
                    e.printStackTrace();
                    break;
                  }
                  
                  employeeInterface.setCurrentEmployee(newEmployee);

                } else if (employeeInterfaceInput.equals("2")) {
                  System.out.println("Please enter full name: ");
                  name = bufferedReader.readLine();
                  System.out.println("Please enter age");
                  userAge = Integer.parseInt("0" + bufferedReader.readLine());
                  System.out.println("Please enter address: ");
                  userAddress = bufferedReader.readLine();
                  System.out.println("Please enter a new password: ");
                  customerPassword = bufferedReader.readLine();

                  int customerId =
                      employeeInterface.createCustomer(
                          name, userAge, userAddress, customerPassword);
                  
                  System.out.println("The id for customer " + name + ": " + customerId);
                } else if (employeeInterfaceInput.equals("3")) {
                  
                  System.out.println("Please enter the user id: ");
                  userId = Integer.parseInt("0" + bufferedReader.readLine());
                  
                  boolean valid = true;
                  try {
                    if (3 != DatabaseSelectAdapter.getUserRoleId(userId)) {
                      System.out.println("Not a customer");
                      valid = false;
                    }
                    
                    if (DatabaseSelectAdapter.getUserActiveAccounts(userId).size() > 0) {
                      System.out.println("The User already has an active account.");
                      valid = false;
                    }
                  } catch (DatabaseSelectHelperException e) {
                    valid = false;
                  }
                  
                  if (valid) {
                    employeeInterface.createAccount(userId);
                    System.out.println("Account succesfully created for User with id " + userId);
                  }
                } else if (employeeInterfaceInput.equals("4")) {
                  System.out.println("Please enter full name: ");
                  name = bufferedReader.readLine();
                  System.out.println("Please enter age");
                  userAge = Integer.parseInt("0" + bufferedReader.readLine());
                  System.out.println("Please enter address: ");
                  userAddress = bufferedReader.readLine();
                  System.out.println("Please enter a new password: ");
                  employeePassword = bufferedReader.readLine();

                  int employeeId =
                      employeeInterface.createEmployee(
                          name, userAge, userAddress, employeePassword);
                  newEmployees.add(employeeId);
                  
                  System.out.println("The id for employee " + name + ": " + employeeId);
                  
                } else {
                  inventory = DatabaseSelectAdapter.getInventory();
                  restockInventory = inventory.getItemMap();
                  System.out.println(restockInventory);

                  for (Item i : restockInventory.keySet()) {
                    System.out.println("Item: " + i.getName());
                    System.out.println("Item ID: " + i.getId());
                    System.out.println(
                        "Current quantity: "
                            + restockInventory.get(i)
                            + "\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                            + "\n");
                  }
                  System.out.println("Please enter the id of the item you want to restock: ");
                  int restockItemId = Integer.parseInt("0" + bufferedReader.readLine());

                  System.out.println("Please enter the quantity of the item you want to restock: ");
                  int quantity = Integer.parseInt("0" + bufferedReader.readLine());

                  try {
                    DatabaseUpdateAdapter.updateInventoryQuantity(quantity, restockItemId);
                  } catch (Exception e) {
                    System.out.println(e.getMessage());
                  }
                  employeeInterface = new EmployeeInterface(inventory);
                  for (Item i : inventory.getItemMap().keySet()) {
                    if (i.getId() == restockItemId) {
                      employeeInterface.restockInventory(i, quantity);
                      break;
                    }
                  }
                }
              }
            } else if (inputHere.equals("2")) {

              boolean success = false;
              int customerId = 0;
              customerPassword = "";
              
              User customer = null;
              
              while (!success) {
                System.out.println("Please enter your id: ");
                try {
                  customerId = Integer.parseInt("0" + bufferedReader.readLine());
                  success = true;
                } catch (NumberFormatException e) {
                  System.out.println("Invalid id.");
                  success = false;
                }
                if (success) {
                  System.out.println("Please enter your password: ");
                  customerPassword = bufferedReader.readLine();
                } else {
                  customerPassword = "";
                }
                success = false;
                
                try {
                  if (3 != DatabaseSelectAdapter.getUserRoleId(customerId)) {
                    System.out.println("Not a customer");
                  }

                  customer = DatabaseSelectAdapter.getUserDetails(customerId);

                  customer.authenticate(customerPassword);
                  if (!customer.authenticate(customerPassword)) {
                    System.out.println("Incorrect password");
                  }

                  if (customer.authenticate(customerPassword)
                      && 3 == DatabaseSelectAdapter.getUserRoleId(customerId)) {
                    success = true;
                  }
                } catch (Exception e) {
                  customerPassword = "";
                  System.out.println("Incorrect ID");
                  System.out.println(e.getMessage());
                  success = false;
                }
              }
              
              User authCustomer = DatabaseSelectAdapter.getUserDetails(customerId);
              authCustomer.authenticate(customerPassword);
              ShoppingCartInterface shoppingCart = new ShoppingCart((Customer) authCustomer);

              String customerMenu = "";
              while (!customerMenu.equals("6")) {
                System.out.println(
                    "1. List current items in cart\n2. "
                    + "Add a quantity of an item to the cart\n3. "
                    + "Check total price of items in the cart\n4. "
                    + "Remove a quantity of an item from the cart\n5. "
                    + "check out\n6. Exit\n"
                    + "7. Store Cart\n"
                    + "8. Retrieve Cart");
                customerMenu = bufferedReader.readLine();

                if (customerMenu.equals("6")) {

                  System.out.println("End session");
                  exit = true;
                  break;

                } else if (customerMenu.equals("1")) {

                  System.out.println("Shopping cart contents:");
                  int i = 0;
                  List<Item> currentcart = shoppingCart.getItems();
                  while (i < currentcart.size()) {
                    System.out.println(currentcart.get(i).getName());
                    i++;
                  }

                } else if (customerMenu.equals("2")) {

                  Inventory currentInventory = DatabaseSelectAdapter.getInventory();
                  HashMap<Item, Integer> items = currentInventory.getItemMap();
                  System.out.println("Things to buy:\n");
                  for (Item i : items.keySet()) {
                    System.out.println("ID: " + i.getId());
                    System.out.println("Item: " + i.getName());
                    System.out.println("Price: " + i.getPrice());
                    System.out.println(
                        "Total quantity: " + items.get(i) + "\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                  }

                  System.out.println(
                      "Please select the id of the item you want to add to your cart");
                  int selectedItem = Integer.parseInt("0" + bufferedReader.readLine());

                  boolean valid = false;
                  while (!valid) {
                    System.out.println(
                        "Please select the amount of the item you want to add to your cart");
                    int chosenQuantity = Integer.parseInt("0" + bufferedReader.readLine());
                    if (chosenQuantity >= 0
                        && chosenQuantity
                            <= DatabaseSelectAdapter.getInventoryQuantity(selectedItem)) {
                      shoppingCart.addItem(
                          DatabaseSelectAdapter.getItem(selectedItem), chosenQuantity);
                      valid = true;
                    } else {
                      System.out.println("Please enter a valid amount from the given list");
                    }
                  }

                } else if (customerMenu.equals("3")) {
                  System.out.println("Total price");
                  System.out.println(shoppingCart.getTotal());
                } else if (customerMenu.equals("4")) {
                  System.out.println(
                      "Please select the id of the item whose quantity you want to remove");
                  int selectedItem = Integer.parseInt("0" + bufferedReader.readLine());
                  System.out.println(
                      "Please select the amount of the item you want to remove from your cart");
                  int chosenQuantity = Integer.parseInt("0" + bufferedReader.readLine());

                  if (chosenQuantity < 0
                      || chosenQuantity > DatabaseSelectAdapter
                      .getInventoryQuantity(selectedItem)) {
                    throw new IOException(
                        "Please enter a valid amount to remove from current amount");
                  }

                  shoppingCart.removeItem(
                      DatabaseSelectAdapter.getItem(selectedItem), chosenQuantity);
                } else if (customerMenu.equals("5")) {
                  shoppingCart.checkOut();
                  shoppingCart.clearCart();
                } else if (customerMenu.equals("7")) {
                  try {
                    int accountId = DatabaseSelectAdapter.getUserActiveAccounts(customerId)
                        .get(0).intValue();
                    DatabaseInsertAdapter
                        .insertShoppingCart((ShoppingCartQuantityManager) shoppingCart,
                        accountId, customerId);
                    ((ShoppingCartAccountManager)shoppingCart).setAccountCart(true);
                  } catch (Exception e) {
                    System.out.println("Could not store cart, "
                        + "either you already have an active cart stored,"
                        + " or you do not yet have an account and"
                        + " must contact an Employee for one.");
                  }
                } else if (customerMenu.equals("8")) {
                  try {
                    shoppingCart = DatabaseSelectAdapter.getShoppingCart((Customer) authCustomer,
                        DatabaseSelectAdapter.getUserActiveAccounts(customerId).get(0).intValue());
                  } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Could not retrieve cart, perhaps you don't have one.");
                  }
                }
              }
            }
          }
        }
        // If it is 1
        /*
         * TODO In admin mode, the user must first login with a valid admin account
         * This will allow the user to promote employees to admins. Currently, this
         * is all an admin can do.
         */
        // If anything else - including nothing
        /*
         * TODO Create a context menu, where the user is prompted with:
         * 1 - Employee Login
         * 2 - Customer Login
         * 0 - Exit
         * Enter Selection:
         */
        // If the user entered 1
        /*
         * TODO Create a context menu for the Employee interface
         * Prompt the employee for their id and password
         * Attempt to authenticate them.
         * If the Id is not that of an employee or password is incorrect, end the session
         * If the Id is an employee, and the password is correct, create an EmployeeInterface object
         * then give them the following options:
         * 1. authenticate new employee
         * 2. Make new User
         * 3. Make new account
         * 4. Make new Employee
         * 5. Restock Inventory
         * 6. Exit
         *
         * Continue to loop through as appropriate, ending once you get an exit code (9)
         */
        // If the user entered 2
        /*
         * TODO create a context menu for the customer Shopping cart
         * Prompt the customer for their id and password
         * Attempt to authenticate them
         * If the authentication fails or they are not a customer, repeat
         * If they get authenticated and are a customer, give them this menu:
         * 1. List current items in cart
         * 2. Add a quantity of an item to the cart
         * 3. Check total price of items in the cart
         * 4. Remove a quantity of an item from the cart
         * 5. check out
         * 6. Exit
         *
         * When checking out, be sure to display the customers total, and ask them if they wish
         * to continue shopping for a new order
         *
         * For each of these, loop through and continue prompting for the information needed
         * Continue showing the context menu, until the user gives a 6 as input.
         */
        // If the user entered 0
        /*
         * TODO Exit condition
         */
        // If the user entered anything else:
        /*
         * TODO Re-prompt the user
         */

      } catch (Exception e) {
        // TODO Improve this!
        e.printStackTrace();
        exit = true;
        e.printStackTrace();
      } finally {
        try {
          connection.close();
        } catch (Exception e) {
          System.out.println("Looks like it was closed already :)");
        }
      }
    }
  }
  
  private static boolean clearDatabase() {
    Path path = Paths.get("inventorymgmt.db");
    try {
      Files.deleteIfExists(path);
      
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  private static boolean initNewDb() {
    try {
      Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
      DatabaseDriverExtender.initialize(connection);
      connection.close();
      return true;
    } catch (ConnectionFailedException e) {
      return false;
    } catch (SQLException e) {
      return false;
    }
  }
  
  private static void insertDefaults() throws DatabaseSelectHelperException,
      DatabaseInsertHelperException, SQLException, DatabaseInsertException {
    
    for (Roles role : Roles.values()) {
      DatabaseInsertAdapter.insertRole(role.toString());
    }
    
    for (ItemTypes item : ItemTypes.values()) {
      int itemId = DatabaseInsertAdapter.insertItem(item.toString(), new BigDecimal("12"));
      DatabaseInsertAdapter.insertInventory(itemId, 100);
    }
  }
}