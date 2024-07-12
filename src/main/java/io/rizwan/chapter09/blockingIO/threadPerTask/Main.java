package io.rizwan.chapter09.blockingIO.threadPerTask;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int NUMBER_OF_TASKS = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to start");
        scanner.nextLine();

        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms time to complete", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        // Creates a thread pool that creates new threads as needed,
        // but will reuse previously constructed threads when they are available.
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        try {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(Main::blockingIOOperation);
            }
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException e) {

        }
    }

    public static void blockingIOOperation() {
        System.out.println("Executing a blocking task from the thread: " + Thread.currentThread());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
