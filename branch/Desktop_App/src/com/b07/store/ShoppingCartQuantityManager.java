package com.b07.store;

import com.b07.inventory.Item;

public interface ShoppingCartQuantityManager extends ShoppingCartInterface {
  /**
   * Returns the quantity of the given item.
   * @param item an item in the cart.
   * @return the amount of the given item currently in the cart.
   */
  public int getItemQuantity(Item item);
  
  /**
   * Sets the quantity of the given item.
   * @param item an item in the cart
   * @param quantity the amount of the given item to set in the cart.
   */
  public void setItemQuantity(Item item, int quantity);
}
