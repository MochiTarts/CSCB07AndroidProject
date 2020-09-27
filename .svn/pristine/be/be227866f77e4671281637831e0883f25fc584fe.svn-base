package com.b07.store;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.Unauthenticated;
import com.b07.inventory.Item;
import com.b07.users.Customer;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart implements ShoppingCartQuantityManager,
    ShoppingCartAccountManager {

  private HashMap<Item, Integer> items;
  private Customer customer;
  private BigDecimal total;
  private boolean accountCart;
  private static final BigDecimal TAXRATE = new BigDecimal("1.13");
  
  /**
   * Standard constructor for ShoppingCart's, assigns this shopping cart to the given User.
   * The user must be authenticated.
   * @param customer the owner of the Shopping Cart
   * @throws Unauthenticated if the given user is not authenticated.
   */
  public ShoppingCart(Customer customer)
      throws Unauthenticated {
    try {
      if (!customer.getAuthenticated()) {
        throw new Unauthenticated("Customer not authenticated");
      }
      this.customer = customer;
    } catch (Unauthenticated e) {
      System.out.println(e.getMessage());
    }
    this.items = new HashMap<Item, Integer>();
    this.accountCart = false;
  }
  
  public ShoppingCart(Customer customer, boolean accountCart)
      throws Unauthenticated {
    try {
      if (!customer.getAuthenticated()) {
        throw new Unauthenticated("Customer not authenticated");
      }
      this.customer = customer;
    } catch (Unauthenticated e) {
      System.out.println(e.getMessage());
    }
    this.items = new HashMap<Item, Integer>();
    this.accountCart = accountCart;
  }
  
  @Override
  public void addItem(Item item, int quantity) {
    try {
      for (Item i : this.items.keySet()) {
        if (i.getId() == item.getId()) {
          item = i;
          break;
        }
      }

      if (!this.items.containsKey(item)) {
        this.items.put(item, quantity);
      } else {
        int newQuantity = this.items.get(item) + quantity;
        this.items.replace(item, newQuantity);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  @Override
  public void removeItem(Item item, int quantity) {
    try {
      for (Item i : this.items.keySet()) {
        if (i.getId() == item.getId()) {
          item = i;
          break;
        }
      }
      int newQuantity = this.items.get(item) - quantity;
      if (newQuantity <= 0) {
        this.items.remove(item);
      } else {
        this.items.replace(item, newQuantity);
      }
    } catch (Exception e) {
      System.out.println("Item does not exist in cart");
    }
  }
  
  @Override
  public List<Item> getItems() {
    List<Item> contents = new ArrayList<>();
    for (Item i : this.items.keySet()) {
      contents.add(i);
    }
    return contents;
  }

  @Override
  public Customer getCustomer() {
    return customer;
  }
  
  @Override
  public int getItemQuantity(Item item) {
    for (Item i: this.items.keySet()) {
      if (item.getId() == i.getId()) {
        return items.get(i).intValue();
      }
    }
    
    return -1;
  }
    
  @Override
  public void setItemQuantity(Item item, int quantity) {
    for (Item i: this.items.keySet()) {
      if (item.getId() == i.getId()) {
        this.items.put(i, quantity);
        break;
      }
    }
  }
  
  @Override
  public BigDecimal getTotal() {
    total = new BigDecimal("0");
    for (Item i : this.items.keySet()) {
      BigDecimal itemPrice = i.getPrice().multiply(new BigDecimal(this.items.get(i)));
      total = total.add(itemPrice);
    }
    return total;
  }

  @Override
  public BigDecimal getTaxRate() {
    return TAXRATE;
  }

  public boolean checkOut()
          throws SQLException, DatabaseSelectHelperException, DatabaseInsertException,
          DatabaseInsertHelperException {
    if (this.items.size() == 0) {
      return false;
    }

    if (this.customer != null) {
      BigDecimal total = new BigDecimal("0");
      BigDecimal totalAfterTax = new BigDecimal("0");
      for (Item i : this.items.keySet()) {
        if (this.items.get(i) > DatabaseSelectAdapter.getInventoryQuantity(i.getId())) {
          throw new DatabaseSelectHelperException("Insufficient Items.");
        }
        BigDecimal itemPrice = i.getPrice().multiply(new BigDecimal(this.items.get(i)));
        total = total.add(itemPrice);
      }

      try {
        int salesId = DatabaseInsertAdapter.insertSale(this.customer.getId(), total);
        for (Item i : this.items.keySet()) {
          DatabaseInsertAdapter.insertItemizedSale(salesId, i.getId(), this.items.get(i));
          /*System.out.println(DatabaseSelectHelper
              .getInventoryQuantity(i.getId())
              - this.items.get(i));
          System.out.println(i.getId());*/
          DatabaseUpdateAdapter.updateInventoryQuantity(DatabaseSelectAdapter
                  .getInventoryQuantity(i.getId())
                  - this.items.get(i), i.getId());
        }

        if (accountCart) {
          DatabaseUpdateAdapter.updateAccountStatus(DatabaseSelectAdapter
                  .getUserActiveAccounts(this.customer.getId()).get(0), false);
        }
      } catch (Exception e) {
        System.out.println("Sorry. Something went wrong with the sales transaction");
        return false;
      }

      totalAfterTax = total.multiply(TAXRATE);
      System.out.println("The total was " + totalAfterTax);
    } else {
      return false;
    }

    clearCart();
    return true;
  }

  public void clearCart() {
    this.items = new HashMap<Item, Integer>();
  }

  @Override
  public boolean getAccountCart() {
    return accountCart;
  }

  @Override
  public void setAccountCart(boolean isAccountCart) {
    this.accountCart = isAccountCart;
  }
}
