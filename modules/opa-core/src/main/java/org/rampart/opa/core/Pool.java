package org.rampart.opa.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Pool {

    private final BlockingQueue<VM> pool;

    public Pool(int poolSize, int minMemory, int maxMemory) {

        this.pool = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool.add(new VM(minMemory, maxMemory));
        }
    }

    public VM acquire() throws InterruptedException {
        return pool.take();
    }

    public void release(VM vm) {
        if (vm != null && !pool.offer(vm)) { // Check if the pool is full
            System.out.println("Attempted to release a VM to a full pool.");
        }
    }

    public void setPolicyData(byte[] policy, byte[] data) {
        for (VM vm : pool) {
            vm.setPolicy(policy);
            vm.setData(data);
            release(vm);
        }
    }

    public void close() {
        for (VM vm : pool) {
            vm.close();
        }
    }
}
