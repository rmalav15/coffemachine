package org.dunzo;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    Map<InventoryItem, Integer> availabeItems;

    private static Inventory instance = null;

    private Inventory() {
        availabeItems = new HashMap<InventoryItem, Integer>();
    }

    public static synchronized Inventory getInstance() {
        if(instance == null)
        {
            instance = new Inventory();
        }
        return instance;
    }

    public boolean isAvailable(InventoryItem item, int quantity)
    {
        if(! availabeItems.containsKey(item) || availabeItems.get(item) < quantity)
        {
            return false;
        }
        return true;
    }

    public void use(InventoryItem item, int quantity)
    {
        if(isAvailable(item, quantity))
        {
            availabeItems.put(item, availabeItems.get(item) - quantity);
        }
    }

    public void fill(InventoryItem item, int quantity)
    {
        if(availabeItems.containsKey(item))
        {
            availabeItems.put(item, availabeItems.get(item) + quantity);
        } else {
            availabeItems.put(item, quantity);
        }
    }

}
