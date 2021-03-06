package com.b07.inventory;

import com.b07.exceptions.ItemSetException;
import java.io.Serializable;
import java.math.BigDecimal;

public interface Item extends Serializable {
  
  public int getId();
  
  /**
   * Sets the id of the item.
   * @param id a valid item id from the database.
   * @throws ItemSetException if the id has already been set.
   */
  public void setId(int id) throws ItemSetException;

  public String getName();

  /**
   * Sets the name of the Item.
   * @param name a valid item name from the ItemTypes enum.
   */
  public void setName(String name);

  public BigDecimal getPrice();
  
  /**
   * Sets the price of the item.
   * @param price the price to be set.
   */
  public void setPrice(BigDecimal price);
}
