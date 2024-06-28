package io.rizwan.chapter04.criticalSection;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        IncrementCounter incrementCounter = new IncrementCounter(inventoryCounter);
        DecrementCounter decrementCounter = new DecrementCounter(inventoryCounter);

        incrementCounter.start();
        decrementCounter.start();

        incrementCounter.join();
        decrementCounter.join();

        System.out.println("Inventory Count is :" + inventoryCounter.getInventoryCount());
    }

    public static class DecrementCounter extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementCounter(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementCounter extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementCounter(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int inventoryCount = 0;

        public void increment() {
            inventoryCount++;
        }

        public void decrement() {
            inventoryCount--;
        }

        public int getInventoryCount() {
            return inventoryCount;
        }
    }
}
