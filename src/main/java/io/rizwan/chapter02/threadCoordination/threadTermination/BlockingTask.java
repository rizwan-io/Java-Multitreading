package io.rizwan.chapter02.threadCoordination.threadTermination;

public class BlockingTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            System.out.println("Exiting blocking thread due to interrupt signal");
        }
    }
}
