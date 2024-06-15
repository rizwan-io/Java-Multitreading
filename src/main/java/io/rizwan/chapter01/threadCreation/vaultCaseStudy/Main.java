package io.rizwan.chapter01.threadCreation.vaultCaseStudy;

import io.rizwan.chapter01.threadCreation.vaultCaseStudy.hackers.AscendingHackerThread;
import io.rizwan.chapter01.threadCreation.vaultCaseStudy.hackers.DescendingHackerThread;
import io.rizwan.chapter01.threadCreation.vaultCaseStudy.police.PoliceThread;
import io.rizwan.chapter01.threadCreation.vaultCaseStudy.vault.Vault;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> hackerThreads = new ArrayList<>();

        hackerThreads.add(new AscendingHackerThread(vault));
        hackerThreads.add(new DescendingHackerThread(vault));
        hackerThreads.add(new PoliceThread());

        for (Thread thread : hackerThreads) {
            thread.start();
        }
    }
}
