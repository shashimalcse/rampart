package org.rampart.opa.core;

public class OPA {

    private final Pool pool;
    private byte[] policy;

    public OPA(int minMemory, int maxMemory, int poolSize) {
        this.pool = new Pool(poolSize, minMemory, maxMemory);
    }

    public void setPolicyData(byte[] policy, byte[] data) {
        this.policy = policy;
        pool.setPolicyData(policy, data);
    }

    public void setData(byte[] data) {
        pool.setPolicyData(this.policy,data);
    }

    public String eval(byte[] input) {

        VM vm = null;
        try {
            vm = pool.acquire();
            return vm.eval(input);
        } catch (InterruptedException e) {
            return "";
        } finally {
            pool.release(vm);
        }
    }

    public void close() {
        pool.close();
    }
}
