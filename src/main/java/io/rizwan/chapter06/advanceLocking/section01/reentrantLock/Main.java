package io.rizwan.chapter06.advanceLocking.section01.reentrantLock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> hashMap = new HashMap<>();

        // This will be shared between the UI thread and the updater thread
        PriceContainer priceContainer = new PriceContainer();
        PriceUpdater priceUpdater = new PriceUpdater(priceContainer);
        PriceFetcher priceFetcher = new PriceFetcher(priceContainer, hashMap);

        priceUpdater.start();
        priceFetcher.start();

        while (true) {
            System.out.println(hashMap);
            Thread.sleep(1000);
        }
    }

    public static class PriceFetcher extends Thread {
        private PriceContainer priceContainer;
        private Map hashMap;

        public PriceFetcher(PriceContainer priceContainer, Map hashMap) {
            this.priceContainer = priceContainer;
            this.hashMap = hashMap;
        }

        @Override
        public void run() {
            while (true) {
                if (priceContainer.getLock().tryLock())
                {
                    try {
                        hashMap.put("bitcoinPrice", priceContainer.getBitcoinCashPrice());
                        hashMap.put("etherPrice", priceContainer.getEtherPrice());
                        hashMap.put("litecoinPrice", priceContainer.getLitecoinPrice());
                        hashMap.put("bitcoinCashPrice", priceContainer.getBitcoinCashPrice());
                        hashMap.put("ripplePrice", priceContainer.getRipplePrice());

                    } finally {
                        priceContainer.getLock().unlock();
                    }
                }
            }
        }
    }

    public static class PriceUpdater extends Thread {
        private PriceContainer priceContainer;
        private Random random = new Random();

        public PriceUpdater(PriceContainer priceContainer) {
            this.priceContainer = priceContainer;
        }

        @Override
        public void run() {
            // lock the reentrant lock before setting the price,
            // so the prices are correct prices are seen by the gui app.
            while (true) {
                priceContainer.getLock().lock();

                try {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    priceContainer.setBitcoinPrice(random.nextInt(2000));
                    priceContainer.setEtherPrice(random.nextInt(2000));
                    priceContainer.setLitecoinPrice(random.nextInt(500));
                    priceContainer.setBitcoinCashPrice(random.nextInt(5000));
                    priceContainer.setRipplePrice(random.nextDouble());
                } finally {
                    priceContainer.getLock().unlock();
                }

            }
        }
    }

    public static class PriceContainer {
        private Lock lock = new ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }

        public Lock getLock() {
            return lock;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }
    }
}
