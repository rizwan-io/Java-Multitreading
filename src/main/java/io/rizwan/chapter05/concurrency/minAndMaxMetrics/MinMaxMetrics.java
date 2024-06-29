package io.rizwan.chapter05.concurrency.minAndMaxMetrics;

import io.rizwan.chapter05.concurrency.Main;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long minSample;
    private volatile long maxSample;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        this.maxSample = Long.MIN_VALUE;
        this.minSample = Long.MAX_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        maxSample = Math.max(maxSample, newSample);
        minSample = Math.min(minSample, newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return minSample;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return maxSample;
    }
}

