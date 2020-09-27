package com.b07.store.holidaysales;

import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.exceptions.ItemSetException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class HolidayDealsImpl implements HolidayDeals {
    private final HashMap<Item, BigDecimal> christmasSale;
    private final HashMap<Item, BigDecimal> halloweenSale;
    private final HashMap<Item, BigDecimal> cyberMondaySale;
    private final HashMap<Item, BigDecimal> aprilFoolsSale;
    private final HashMap<Item, BigDecimal> fridayThirteenSale;

    public HolidayDealsImpl() {
        HashMap<String, BigDecimal> christmasSaleMap = new HashMap<String, BigDecimal>();
        christmasSaleMap.put("HOCKEY_STICK", new BigDecimal("25"));
        christmasSaleMap.put("SKATES", new BigDecimal("15"));
        christmasSale = makeSale(christmasSaleMap);

        HashMap<String, BigDecimal> halloweenSaleMap = new HashMap<String, BigDecimal>();
        halloweenSaleMap.put("PROTEIN_BAR", new BigDecimal("40"));
        halloweenSale = makeSale(halloweenSaleMap);

        HashMap<String, BigDecimal> cyberMondaySaleMap = new HashMap<String, BigDecimal>();
        cyberMondaySaleMap.put("FISHING_ROD", new BigDecimal("5"));
        cyberMondaySaleMap.put("PROTEIN_BAR", new BigDecimal("30"));
        cyberMondaySaleMap.put("SKATES", new BigDecimal("45"));
        cyberMondaySale = makeSale(cyberMondaySaleMap);

        HashMap<String, BigDecimal> aprilFoolsDayMap = new HashMap<String, BigDecimal>();
        aprilFoolsDayMap.put("FISHING_ROD", new BigDecimal("1"));
        aprilFoolsSale = makeSale(aprilFoolsDayMap);

        HashMap<String, BigDecimal> fridayThirteenSaleMap = new HashMap<String, BigDecimal>();
        fridayThirteenSaleMap.put("RUNNING_SHOES", new BigDecimal("13"));
        fridayThirteenSale = makeSale(fridayThirteenSaleMap);
    }

    @Override
    public HashMap<Item, BigDecimal> getCurrentSales() {
        Calendar curDate = Calendar.getInstance();
        HashMap<Item, BigDecimal> saleList = new HashMap<Item, BigDecimal>();

        Calendar cal = new GregorianCalendar();
        cal.set(curDate.get(Calendar.YEAR),
                Calendar.DECEMBER, 18);
        if (curDate.after(cal)) {
            cal.set(Calendar.DAY_OF_MONTH, 26);
            if (curDate.before(cal)) {
                saleList.putAll(christmasSale);
            }
        }

        cal.set(curDate.get(Calendar.YEAR), Calendar.OCTOBER, 30);

        if ((curDate.get(Calendar.YEAR) == cal.get(Calendar.YEAR))
            && curDate.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
            && curDate.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
            saleList.putAll(halloweenSale);
        }

        return saleList;
    }

    private HashMap<Item, BigDecimal> makeSale(HashMap<String, BigDecimal> sale) {
        HashMap<Item, BigDecimal> saleMap = new HashMap<Item, BigDecimal>();
        List<Item> items;
        try {
            items = DatabaseSelectAdapter.getAllItems();
        } catch (SQLException e) {
            return null;
        } catch (ItemSetException e) {
            return null;
        }

        for (String saleItem : sale.keySet()) {
            try {
                ItemTypes.valueOf(saleItem);
                for (Item item : items) {
                    if (item.getName().equals(saleItem)) {
                        saleMap.put(item, sale.get(saleItem));
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                // The item is not in the enum, continue.
            }
        }

        return saleMap;
    }
}
