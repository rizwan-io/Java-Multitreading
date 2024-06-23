package io.rizwan.chapter02.threadCoordination.threadJoining;

import java.math.BigInteger;

public class FactorialThread extends Thread {
    private final long inputNumber;
    private BigInteger result = BigInteger.ZERO;
    private boolean isFinished = false;

    public FactorialThread(long inputNumber) {
        this.inputNumber = inputNumber;
    }

    @Override
    public void run() {
        result = factorial(inputNumber);
        isFinished = true;
    }

    private BigInteger factorial(long inputNumber) {
        BigInteger tempResult = BigInteger.ONE;
        
        for (long i = 1; i < inputNumber; i++) {
            tempResult = tempResult.multiply(BigInteger.valueOf(i));
        }

        return tempResult;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public BigInteger getResult() {
        return result;
    }
}
