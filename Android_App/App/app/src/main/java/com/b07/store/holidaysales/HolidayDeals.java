package com.b07.store.holidaysales;

import com.b07.inventory.Item;
import java.math.BigDecimal;
import java.util.HashMap;

public interface HolidayDeals {
    /**
     * Returns the current holiday sales based on the current date.
     * @return A map of the Items on sale to the percentage discount.
     */
    public HashMap<Item, BigDecimal> getCurrentSales();
}
