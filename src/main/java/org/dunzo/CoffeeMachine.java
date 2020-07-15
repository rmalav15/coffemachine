package org.dunzo;

import sun.security.krb5.internal.Ticket;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CoffeeMachine implements Closeable {

    private static final int STARTING_QUANTITY = 5;

    private ExecutorService executorService;
    private int capacity;
    private Inventory inventory;

    CoffeeMachine(int capacity)
    {
        this.capacity = capacity;
        executorService = Executors.newFixedThreadPool(capacity);
        inventory = Inventory.getInstance();

        for(InventoryItem item: InventoryItem.values())
        {
            inventory.fill(item, STARTING_QUANTITY);
        }
    }

    public void fill(InventoryItem item, int quantity)
    {
        this.inventory.fill(item, quantity);
    }

    public void refill()
    {
        for(InventoryItem item: InventoryItem.values())
        {
            inventory.fill(item, STARTING_QUANTITY);
        }
    }

    private synchronized boolean updateInventory(Coffee coffee)
    {
        for (Map.Entry<InventoryItem, Integer> entry: coffee.getItems().entrySet())
        {
            if(!inventory.isAvailable(entry.getKey(), entry.getValue()))
            {
                return false;
            }
        }

        for (Map.Entry<InventoryItem, Integer> entry: coffee.getItems().entrySet())
        {
            inventory.use(entry.getKey(), entry.getValue());
        }
        return true;
    }

    private void makeCoffee(Coffee coffee) {
        if(updateInventory(coffee))
        {
            try {
                TimeUnit.SECONDS.sleep(1); // Time To Process Coffee
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("[INFO]: your coffee is ready: " + coffee);
        } else {
            System.out.println("[ERROR]: Items not available for Coffee: " + coffee);
        }
    }

    public void process(final Coffee coffee)
    {
        executorService.submit(() -> this.makeCoffee(coffee));
    }

    public void close() throws IOException {
        try{
            System.out.println("attempt to shutdown executor");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Task Terminated");
        } finally {
            if(!executorService.isTerminated()){
                System.err.println("cancel non-finishing task");
            }
            executorService.shutdownNow();
            System.out.println("Shutdown complete");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CoffeeMachine  coffeeMachine = new CoffeeMachine(2);

        Coffee blackCoffee = new CoffeeBuilder().addBlackCoffeeItems().build();
        Coffee normalCoffee = new CoffeeBuilder().addNormalCoffeeItems().build();
        Coffee customCoffee = new CoffeeBuilder()
                .addNormalCoffeeItems()
                .add(InventoryItem.MILK, 2)
                .add(InventoryItem.BROWN_SUGAR, 2)
                .build();
        Coffee outOfBoundCoffee = new CoffeeBuilder()
                .addNormalCoffeeItems()
                .add(InventoryItem.BROWN_SUGAR, 4)
                .build();

        coffeeMachine.process(blackCoffee);
        coffeeMachine.process(normalCoffee);
        coffeeMachine.process(normalCoffee);
        coffeeMachine.process(blackCoffee);
        coffeeMachine.process(customCoffee);

        coffeeMachine.process(outOfBoundCoffee);


        TimeUnit.SECONDS.sleep(5);
        System.out.println("Refilling...");

        coffeeMachine.fill(InventoryItem.BROWN_SUGAR, 5);
        coffeeMachine.refill();
        coffeeMachine.process(outOfBoundCoffee);

        TimeUnit.SECONDS.sleep(5);

        coffeeMachine.close();
    }
}
