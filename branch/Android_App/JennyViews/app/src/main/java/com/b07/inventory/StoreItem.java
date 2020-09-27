package com.b07.inventory;

import com.b07.exceptions.ItemSetException;
import java.math.BigDecimal;

public class StoreItem implements Item {

  /**
   * Generated serial id for Items.
   */
  private static final long serialVersionUID = -7488344736803822064L;
  private int id;
  private String name;
  private BigDecimal price;
  private boolean idSet = false;
  
  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public BigDecimal getPrice() {
    return this.price;
  }
  
  @Override
  public void setId(int id) throws ItemSetException {
    if (!this.idSet) {
      this.id = id;
      this.idSet = true;
    } else {
      throw new ItemSetException("The id is already set for this item and cannot be changed");
    }
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
