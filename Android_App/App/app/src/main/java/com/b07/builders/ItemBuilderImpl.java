package com.b07.builders;

import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import com.b07.inventory.StoreItem;
import java.math.BigDecimal;

public class ItemBuilderImpl implements ItemBuilder {
  private Item item;
  
  public ItemBuilderImpl() {
    item = new StoreItem();
  }
  
  @Override
  public ItemBuilder id(int id) throws ItemSetException {
    // TODO Auto-generated method stub
    item.setId(id);
    return this;
  }

  @Override
  public ItemBuilder name(String name) throws ItemSetException {
    // TODO Auto-generated method stub
    item.setName(name);
    return this;
  }

  @Override
  public ItemBuilder price(BigDecimal price) throws ItemSetException {
    // TODO Auto-generated method stub
    item.setPrice(price);
    return this;
  }

  @Override
  public Item build() {
    // TODO Auto-generated method stub
    return this.item;
  }

}
