package com.b07.store;

import com.b07.inventory.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SalesLogImpl implements SalesLog {
  /**
   * Generated Serial id for sales logs.
   */
  private static final long serialVersionUID = -2456346938252533034L;
  private List<Sale> log;
  
  public SalesLogImpl() {
    log = new ArrayList<Sale>();
  }
  
  @Override
  public void addSale(Sale sale) {
    log.add(sale);
  }

  @Override
  public void removeSale(Sale sale) {
    for (int i = 0; i < log.size(); i++) {
      if (log.get(i).getId() == sale.getId()) {
        log.remove(i);
        break;
      }
    }
  }

  @Override
  public List<Sale> getSales() {
    List<Sale> logCopy = new ArrayList<Sale>();
    logCopy.addAll(log);
    return logCopy;
  }

  @Override
  public String toString() {
    String s = "";
    for (int i = 0; i < this.log.size(); i++) {
      s += "User Name: " + this.log.get(i).getUser().getName() + "\n";
      s += "Sale Id: " + this.log.get(i).getId() + "\n";
      s += "Total Price: " + this.log.get(i).getTotalPrice() + "\n";
      
      for (Map.Entry<Item, Integer> entry : this.log.get(i).getItemMap().entrySet()) {
        s += entry.getKey().getName() + ": " + entry.getValue() + "\n";
      }

      s += "-------------------------------------------------------------------------\n";
    }
    return s;
  }
}
