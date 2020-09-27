package com.b07.store;

import java.io.Serializable;
import java.util.List;

public interface SalesLog extends Serializable {
  public void addSale(Sale sale);
  
  public void removeSale(Sale sale);
  
  public List<Sale> getSales();
}
