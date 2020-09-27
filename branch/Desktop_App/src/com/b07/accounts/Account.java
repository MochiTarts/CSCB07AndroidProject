package com.b07.accounts;

import java.io.Serializable;
import java.util.HashMap;

public interface Account extends Serializable {
  public int getAccountId();
  
  public void setAccountId(int id);
  
  public int getUserId();
  
  public void setUserId(int id);
  
  public boolean getActive();
  
  public void setActive(boolean active);
  
  public HashMap<Integer, Integer> getItemMap();
  
  public void setItemMap(HashMap<Integer, Integer> map);
}
