package io.rizwan.chapter01.threadCreation.vaultCaseStudy.hackers;

import io.rizwan.chapter01.threadCreation.vaultCaseStudy.vault.Vault;

import static io.rizwan.chapter01.threadCreation.vaultCaseStudy.Main.MAX_PASSWORD;

public class DescendingHackerThread extends HackerThread {
    public DescendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
            if (vault.isPasswordCorrect(guess)) {
                System.out.println(this.getName() + " guessed the password " + guess);
                System.exit(0);
            }
        }
    }
}
