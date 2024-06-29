package io.rizwan.chapter05.concurrency.deadlock;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Random random = new Random();

        Thread thread1 = new Thread(() -> {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadA();
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadB();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread: " + Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        // swap the order of locking to break the circular wait condition of a deadlock
        public void takeRoadB() {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread: " + Thread.currentThread().getName());

                synchronized (roadA) {
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
