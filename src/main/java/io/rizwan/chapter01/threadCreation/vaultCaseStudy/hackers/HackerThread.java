package io.rizwan.chapter01.threadCreation.vaultCaseStudy.hackers;

import io.rizwan.chapter01.threadCreation.vaultCaseStudy.vault.Vault;

public abstract class HackerThread extends Thread {
    protected Vault vault;

    public HackerThread(Vault vault) {
        this.vault = vault;
        this.setName(this.getClass().getSimpleName());
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public synchronized void start() {
        System.out.println("Starting thread: " + this.getName());
        super.start();
    }
}
