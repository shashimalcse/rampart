package org.rampart.opa.core;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.rampart.opa.core.opa.OPABindings;
import org.rampart.opa.core.opa.OPAExports;
import org.rampart.opa.core.wasm.WASMModule;

import java.nio.ByteBuffer;

public class VM {

    private byte[] policy;
    private byte[] data;
    private int minMemory;
    private int maxMemory;
    private int dataAddr;
    private int baseHeapPtr;
    private int dataHeapPtr;
    private int heapPtr;
    private Value memory;

    private OPAExports wasm;
    private Context context;


    public VM(byte[] policy, byte[] data, int minMemory, int maxMemory) {

        this.policy = policy;
        this.minMemory = minMemory;
        this.maxMemory = maxMemory;
        this.data = data;
        this.context = Context.newBuilder().allowAllAccess(true).build();
        this.context.initialize("wasm");
        WASMModule webAssembly = context.getPolyglotBindings().getMember("WebAssembly")
                .as(WASMModule.class);
        Value mainModule = webAssembly.module_decode(policy);
        this.memory = webAssembly.mem_alloc(this.minMemory, this.maxMemory);
        OPABindings bindings = new OPABindings(this.memory);
        this.wasm = webAssembly.module_instantiate(mainModule, Value.asValue(bindings)).as(OPAExports.class);
        this.baseHeapPtr = this.wasm.opa_heap_ptr_get();
        if (data != null) {
            setData(data);
        }
    }

    public String eval(byte[] input) {

        int inputAddr = setInput(input);
        int ctxAddr = this.wasm.opa_eval_ctx_new();
        this.wasm.opa_eval_ctx_set_input(ctxAddr, inputAddr);
        this.wasm.opa_eval_ctx_set_data(ctxAddr, this.dataAddr);
        wasm.eval(ctxAddr);
        int opaResult = wasm.opa_eval_ctx_get_result(ctxAddr);
        int addr = wasm.opa_json_dump(opaResult);
        int end = addr;
        while (memory.readBufferByte(end) != 0) {
            end++;
        }
        int resultSize = end - addr;
        if (resultSize == 0) {
            return "";
        }
        byte[] result = new byte[resultSize];
        for(int i = 0; i < resultSize; i++) {
            result[i] = memory.readBufferByte(addr + i);
        }
        return new String(result);
    }

    private void setData(byte[] data) {

        this.wasm.opa_heap_ptr_set(baseHeapPtr);
        int addr = writeBytes(data);
        dataAddr = this.wasm.opa_json_parse(addr, data.length);
        this.dataHeapPtr = this.wasm.opa_heap_ptr_get();
        this.heapPtr = this.dataHeapPtr;
    }

    private int setInput(byte[] data) {

        this.wasm.opa_heap_ptr_set(this.dataHeapPtr);
        int addr = writeBytes(data);
        int parseAddr = this.wasm.opa_json_parse(addr, data.length);
        return parseAddr;
    }

    private int writeBytes(byte[] bytes) {

        int addr = this.wasm.opa_malloc(bytes.length);
        for(int i = 0; i < bytes.length; i++ ) {
            this.memory.writeBufferByte(addr + i, bytes[i]);
        }
        return addr;
    }
}
