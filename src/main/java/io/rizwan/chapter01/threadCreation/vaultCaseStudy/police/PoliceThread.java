package io.rizwan.chapter01.threadCreation.vaultCaseStudy.police;

public class PoliceThread extends Thread {
    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            // wait for 10 secs and display the count down
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Time remaining: " + i);
        }

        System.out.println("Game over for you");
        System.exit(0);
    }
}
