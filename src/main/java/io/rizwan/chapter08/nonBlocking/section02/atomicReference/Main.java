package io.rizwan.chapter08.nonBlocking.section02.atomicReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class Main {
    // we are going to implement a stack using the linked list.
    public static void main(String[] args) throws InterruptedException {
//        StandardStack<Integer> stack = new StandardStack<>();
        LockFreeStack<Integer> stack = new LockFreeStack();
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int pushingThreads = 2;
        int pullingThreads = 2;

        for (int i = 0; i < pushingThreads; i++) {
            Thread thread = new Thread(() -> {
               while (true) {
                   stack.push(random.nextInt());
               }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < pullingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.pop();
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(5000);
        System.out.printf("The number of operations ran on the stack are %,d ", stack.getCounter());
    }

    public static class LockFreeStack<T> {
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);

        public void push(T value) {
            StackNode<T> newHead = new StackNode<>(value);

            // we will repeatedly try to make the newHead the head of the stack
            // we are using the while loop, since multiple threads may access it.
            while (true) {
                StackNode<T> currentNode = head.get();
                newHead.next = currentNode;
                // if he head is the currentNode then do head = newHead;
                if (head.compareAndSet(currentNode, newHead)) {
                    break;
                } else {
                    // before trying again, wait for 1 seconds
                    LockSupport.parkNanos(1);
                }
            }

            counter.incrementAndGet();
        }

        public T pop() {
            StackNode<T> currentNode = head.get();
            StackNode<T> newHeadNode;

            while (currentNode != null) {
                newHeadNode = currentNode.next;
                if (head.compareAndSet(currentNode, newHeadNode)) {
                    break;
                } else {
                    LockSupport.parkNanos(1);
                }
            }

            counter.incrementAndGet();
            return currentNode.value;
        }

        public int getCounter() {
            return counter.get();
        }
    }

    public static class StandardStack<T> {
        private StackNode<T> head;
        private int counter;

        public synchronized void push(T value) {
            StackNode<T> newHead = new StackNode<>(value);
            newHead.next = head;
            head = newHead;
            counter++;
        }

        public synchronized T pop() {
            if (head == null) {
                counter++;
                return null;
            }
            StackNode<T> tempNode = head;
            head = head.next;
            counter++;
            return tempNode.value;
        }

        public int getCounter() {
            return counter;
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
        }

        public StackNode(T value, StackNode<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
