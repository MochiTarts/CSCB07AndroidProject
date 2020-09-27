package com.b07.accounts;

import java.util.HashMap;

public class AccountImpl implements Account {
  /**
   * Generated serial id for Accounts.
   */
  private static final long serialVersionUID = 8552222454506934933L;
  private int accountId;
  private int userId;
  private HashMap<Integer, Integer> map;
  private boolean active;
  
  @Override
  public int getAccountId() {
    return this.accountId;
  }

  @Override
  public void setAccountId(int id) {
    this.accountId = id;
  }

  @Override
  public int getUserId() {
    return this.userId;
  }

  @Override
  public void setUserId(int id) {
    this.userId = id;
  }
  
  @Override
  public boolean getActive() {
    return this.active;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public HashMap<Integer, Integer> getItemMap() {
    return this.map;
  }

  @Override
  public void setItemMap(HashMap<Integer, Integer> map) {
    this.map = map;
  }
}
