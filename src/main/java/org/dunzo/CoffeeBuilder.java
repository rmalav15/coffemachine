package org.dunzo;

import lombok.Getter;

import java.util.*;

@Getter
public class CoffeeBuilder {

    private static final List<InventoryItem> BLACK_COFFEE_ITEMS = Arrays.asList(InventoryItem.WATER,
            InventoryItem.COFFEE_BEANS);
    private static final List<InventoryItem> NORMAL_COFFEE_ITEMS = Arrays.asList(InventoryItem.WATER,
            InventoryItem.COFFEE_BEANS, InventoryItem.WHITE_SUGAR, InventoryItem.MILK);

    private Map<InventoryItem, Integer> coffeeItems;

    CoffeeBuilder() {
        coffeeItems = new HashMap<InventoryItem, Integer>();
    }


    public CoffeeBuilder addBlackCoffeeItems()
    {
        for(InventoryItem item: BLACK_COFFEE_ITEMS)
        {
           add(item, 1);
        }
        return this;
    }

    public CoffeeBuilder addNormalCoffeeItems()
    {
        for(InventoryItem item: NORMAL_COFFEE_ITEMS)
        {
            add(item, 1);
        }
        return this;
    }

    public CoffeeBuilder add(InventoryItem item, int quantity)
    {
        if(coffeeItems.containsKey(item))
        {
            coffeeItems.put(item, coffeeItems.get(item) + quantity);
        } else {
            coffeeItems.put(item, quantity);
        }
        return this;
    }

    public Coffee build()
    {
        return new Coffee(this);
    }

}
