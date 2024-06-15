package io.rizwan.chapter01.threadCreation;

public class ThreadExceptionHandler {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("External exception");
            }
        });

        thread.setName("Misbehaving thread");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // this method is used to catch any exception which might not be handled during the execution of the thread.
                // this is the place where we can log additional data or clean up the resources.
                System.out.println("A critical error happened in the thread: " + t.getName() +
                        ", the error is " + e.getMessage());
            }
        });

        thread.start();

    }
}
