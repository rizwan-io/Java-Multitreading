package io.rizwan.chapter01.threadCreation.vaultCaseStudy.vault;

public class Vault {
    private int password;

    public Vault(int password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(int guess) {
        // slowing down the hacker threads by 5 ms
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return this.password == guess;
    }
}
