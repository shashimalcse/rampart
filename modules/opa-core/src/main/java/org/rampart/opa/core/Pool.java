package org.rampart.opa.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Pool {

    private BlockingQueue<VM> pool;

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
        pool.offer(vm);
    }


}
