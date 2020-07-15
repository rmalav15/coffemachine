package org.dunzo;

import lombok.Getter;

import java.util.Map;

@Getter
public class Coffee {
    Map<InventoryItem, Integer> items;

    Coffee(CoffeeBuilder builder)
    {
        items = builder.getCoffeeItems();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Coffee {");
        for (Map.Entry<InventoryItem, Integer> entry: items.entrySet())
        {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" : ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(", ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
