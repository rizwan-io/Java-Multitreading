package io.rizwan.chapter02.threadCoordination.threadTermination;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();

        thread = new Thread(new LongComputationTask(new BigInteger("10"), new BigInteger("2")));
        thread.start();
        thread.interrupt();
    }
}
