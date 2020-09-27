package com.b07.store;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.inventory.Item;
import com.b07.users.Customer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ShoppingCartInterface {
  /**
   * Adds a given quantity of item to the cart.
   * @param item the item to be added.
   * @param quantity the amount of the given item to be added.
   */
  public void addItem(Item item, int quantity);
  
  /**
   * Removes a given quantity of item from the cart.
   * @param item the item to be removed.
   * @param quantity the amount of the given item to remove.
   */
  public void removeItem(Item item, int quantity);
  
  /**
   * Gets a list of all the items in the cart.
   * @return the items in the cart.
   */
  public List<Item> getItems();

  public Customer getCustomer();

  public int getItemQuantity(Item item);
  
  /**
   * Returns the pre-tax total of the price of the items in the cart.
   * @return
   */
  public BigDecimal getTotal();

  public BigDecimal getTaxRate();
  
  /**
   * Removes the items in the shopping cart from the database, and removes the items from the cart.
   * Inserts a sale into the database which tracks the items bought. Then, prints the total cost of
   * the transaction.
   * @return true if successful, false if unsuccessful.
   * @throws SQLException if something goes wrong with the database.
   * @throws DatabaseSelectHelperException if the items in the database cannot be accessed.
   * @throws DatabaseInsertException if the itemizedSale cannot be inserted due to 
   *         a database malfunction.
   * @throws DatabaseInsertHelperException if the sale cannot be inserted for other reasons,
   *         such as an invalid field.
   */
  public boolean checkOut()
      throws SQLException, DatabaseSelectHelperException, DatabaseInsertException,
          DatabaseInsertHelperException;
  
  /**
   * Removes all items from the cart.
   */
  public void clearCart();
}
