package com.b07.inventory;

import java.util.HashMap;

public class StoreInventory implements Inventory {

  /**
   * Generated serial id for inventories.
   */
  private static final long serialVersionUID = 1345198205395262935L;
  private HashMap<Item, Integer> inventory; // Item is item, Integer is quantity of item

  @Override
  public HashMap<Item, Integer> getItemMap() {
    // TODO Auto-generated method stub
    return this.inventory;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    // TODO Auto-generated method stub
    this.inventory = itemMap;
  }

  @Override
  public void updateMap(Item item, Integer value) {
    // TODO Auto-generated method stub
    this.inventory.put(item, value);
  }

  @Override
  public int getTotalItems() {
    // TODO Auto-generated method stub
    int total = 0;
    for (Integer i : this.inventory.values()) {
      total += i;
    }
    return total;
  }

  @Override
  public void setTotalItems(int total) {
    // TODO Auto-generated method stub
    
  }
}
