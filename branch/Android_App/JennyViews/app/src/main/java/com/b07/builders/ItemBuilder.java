package com.b07.builders;

import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import java.math.BigDecimal;

public interface ItemBuilder {
  public ItemBuilder id(int id) throws ItemSetException;
  
  public ItemBuilder name(String name) throws ItemSetException;
  
  public ItemBuilder price(BigDecimal price) throws ItemSetException;
  
  public Item build();
}
