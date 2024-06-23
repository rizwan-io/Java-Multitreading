package io.rizwan.chapter02.threadCoordination.threadJoining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 343L, 355L, 234L, 124L, 12L, 124L);
        List<FactorialThread> threadList = new ArrayList<>();

        for (long num : inputNumbers) {
            threadList.add(new FactorialThread(num));
        }

        for (FactorialThread thread : threadList) {
            thread.start();
        }

        for (FactorialThread thread : threadList) {
            thread.join();
        }


        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threadList.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation of " + inputNumbers.get(i) + " is still under progress.");
            }
        }
    }
}
