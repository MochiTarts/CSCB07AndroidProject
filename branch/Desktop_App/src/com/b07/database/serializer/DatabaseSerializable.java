package com.b07.database.serializer;

import java.io.Serializable;

public interface DatabaseSerializable extends Serializable {
  public boolean pullDatabase();
  
  public boolean pullItems();
  
  public boolean pullInventory();
  
  public boolean pullUsers();
  
  public boolean pullSales();
  
  public boolean pullAccounts();
  
  public boolean pullPasswords();
  
  public boolean pullRoles();
  
  public boolean storeDatabase();
  
  public boolean storeItems();
  
  public boolean storeInventory();
  
  public boolean storeUsers();
  
  public boolean storeSales();
  
  public boolean storeAccounts();
  
  public boolean storeRoles();
}
