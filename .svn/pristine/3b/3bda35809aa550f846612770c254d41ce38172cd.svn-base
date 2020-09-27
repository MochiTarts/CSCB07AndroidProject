package com.b07.database.serializer;

import com.b07.accounts.Account;
import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.ItemSetException;
import com.b07.exceptions.UserSetException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.users.Roles;
import com.b07.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DatabaseSerializableImpl implements DatabaseSerializable {
  /**
   * Generated Serial identifier for DatabaseSerializable.
   */
  private static final long serialVersionUID = 9105313992146631533L;
  
  private List<Item> items;
  private Inventory inventory;
  //Users maps users to their role ids.
  private HashMap<User, Integer> users;
  private SalesLog sales;
  private List<Account> accounts;
  private HashMap<Integer, String> passwords;
  private HashMap<Integer, String> roles;
  
  @Override
  public boolean pullDatabase() {
    return pullItems() 
        && pullInventory()
        && pullUsers()
        && pullSales()
        && pullAccounts()
        && pullPasswords()
        && pullRoles();
  }

  @Override
  public boolean pullItems() {
    try {
      items = DatabaseSelectAdapter.getAllItems();
      return true;
    } catch (SQLException | ItemSetException e) {
      return false;
    }
  }

  @Override
  public boolean pullInventory() {
    try {
      inventory = DatabaseSelectAdapter.getInventory();
      return true;
    } catch (SQLException | DatabaseSelectHelperException | ItemSetException e) {
      return false;
    }
  }

  @Override
  public boolean pullUsers() {
    try {
      List<User> userList = DatabaseSelectAdapter.getUsersDetails();
      users = new HashMap<User, Integer>();
      
      for (User user : userList) {
        users.put(user, user.getRoleId());
      }
      return true;
    } catch (SQLException | BuildTypeException | UserSetException 
        | DatabaseSelectHelperException e) {
      return false;
    }
  }

  @Override
  public boolean pullSales() {
    try {
      sales = DatabaseSelectAdapter.getCompleteSales();
      return true;
    } catch (SQLException | DatabaseSelectHelperException e) {
      return false;
    }
  }

  @Override
  public boolean pullAccounts() {
    try {
      accounts = DatabaseSelectAdapter.getAccounts();
      return true;
    } catch (DatabaseSelectHelperException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean pullPasswords() {
    try {
      passwords = DatabaseSelectAdapter.getPasswords();
      return true;
    } catch (DatabaseSelectHelperException e) {
      return false;
    }
  }

  @Override
  public boolean pullRoles() {
    roles = new HashMap<Integer, String>();
    
    try {
      List<Integer> roleIds = DatabaseSelectAdapter.getRoleIds();
      
      for (Integer i : roleIds) {
        roles.put(i.intValue(), DatabaseSelectAdapter.getRoleName(i.intValue()));
      }
      
      return true;
    } catch (SQLException | DatabaseSelectHelperException e) {
      return false;
    }
  }
  
  @Override
  public boolean storeDatabase() {
    return storeItems()
        && storeRoles()
        && storeInventory()
        && storeUsers()
        && storeSales()
        && storeAccounts();
  }

  @Override
  public boolean storeItems() {
    if (items == null) {
      return true;
    }
    
    Collections.sort(items, new Comparator<Item>() {
      @Override
      public int compare(Item item1, Item item2) {
        return (new Integer(item1.getId())).compareTo(new Integer(item2.getId()));
      }
    });
    
    for (Item item : items) {
      try {
        DatabaseInsertAdapter.insertItem(item.getName(), item.getPrice());
      } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public boolean storeInventory() {
    if (inventory == null) {
      return true;
    }
    HashMap<Item, Integer> map = inventory.getItemMap();
    
    if (map == null) {
      return true;
    }
    
    Set<Item> itemSet = map.keySet();
    List<Item> itemList = new ArrayList<Item>();
    itemList.addAll(itemSet);
    
    Collections.sort(itemList, new Comparator<Item>() {
      @Override
      public int compare(Item item1, Item item2) {
        return (new Integer(item1.getId())).compareTo(new Integer(item2.getId()));
      }
    });
    
    for (Item item : itemList) {
      try {
        DatabaseInsertAdapter.insertInventory(item.getId(), map.get(item));
      } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public boolean storeUsers() {
    if (users == null || (users.size() == 0)) {
      return true;
    }
    
    if (passwords == null || (passwords.size() != users.size())) {
      return false;
    }
    
    Set<User> userSet = users.keySet();
    List<User> userList = new ArrayList<User>();
    userList.addAll(userSet);
    
    Collections.sort(userList, new Comparator<User>() {
      @Override
      public int compare(User user1, User user2) {
        return (new Integer(user1.getId())).compareTo(new Integer(user2.getId()));
      }
    });
    
    List<String> validRoles = new ArrayList<String>();
    
    for (Roles role : Roles.values()) {
      validRoles.add(role.toString());
    }
    
    for (User user : userList) {
      try {
        if (validRoles.contains(roles.get(users.get(user)))) {
          DatabasePasswordInsertExtender.insertNewUser(user.getName(), user.getAge(),
              user.getAddress(), passwords.get(user.getId()), true);
          DatabaseInsertAdapter.insertUserRole(user.getId(), users.get(user));
        }
      } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public boolean storeSales() {
    if (sales == null) {
      return true;
    }
    
    List<Sale> saleList = sales.getSales();
    
    Collections.sort(saleList, new Comparator<Sale>() {
      @Override
      public int compare(Sale sale1, Sale sale2) {
        return (new Integer(sale1.getId())).compareTo(new Integer(sale2.getId()));
      }
    });
    
    for (Sale sale : saleList) {
      try {
        int saleId = DatabaseInsertAdapter.insertSale(sale.getUser().getId(), sale.getTotalPrice());
        
        HashMap<Item, Integer> itemMap = sale.getItemMap();
        
        for (Item item : itemMap.keySet()) {
          DatabaseInsertAdapter.insertItemizedSale(saleId, item.getId(), itemMap.get(item));
        }
      } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public boolean storeAccounts() {
    if (accounts == null) {
      return true;
    }
    
    Collections.sort(accounts, new Comparator<Account>() {
      @Override
      public int compare(Account account1, Account account2) {
        return (new Integer(account1.getAccountId()))
            .compareTo(new Integer(account2.getAccountId()));
      }
    });
    
    for (Account account : accounts) {
      int accountId = 0;
      try {
        accountId = DatabaseInsertAdapter.insertAccount(account.getUserId(), account.getActive());
        
        HashMap<Integer, Integer> map = account.getItemMap();
        
        if (map != null) {
          for (Integer i : map.keySet()) {
            DatabaseInsertAdapter.insertAccountLine(accountId, i.intValue(), map.get(i));
          }
        }
      } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
        e.printStackTrace();
        return false;
      }
    }
    
    return true;
  }

  @Override
  public boolean storeRoles() {
    if (roles == null) {
      return true;
    }
    
    Set<Integer> rolesSet = roles.keySet();
    List<Integer> rolesList = new ArrayList<Integer>();
    rolesList.addAll(rolesSet);
    
    Collections.sort(rolesList);
    
    for (Integer i : rolesList) {
      try {
        DatabaseInsertAdapter.insertRole(roles.get(i));
      } catch (DatabaseInsertException | SQLException | DatabaseSelectHelperException e) {
        return false;
      } catch (DatabaseInsertHelperException e) {
        // This role is either already in the database, or it is not in the Roles enum,
        // we can skip this role and continue.
      }
    }
    
    return true;
  }
}
