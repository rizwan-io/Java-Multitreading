package io.rizwan.chapter01.threadCreation;

public class ThreadCreation02 {

    public static void main(String[] args) {
        Thread thread = new NewThread();
        thread.start();
    }

    public static class NewThread extends Thread {
        @Override
        public void run() {
            // code that executes on a new thread
            System.out.println("We are in a new thread: " + this.getName());
        }
    }
}
