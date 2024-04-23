package org.rampart.opa.core;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.IOException;
import java.util.Objects;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {

        int basedHeapPrt;
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {

            context.initialize("wasm");
            var webAssembly = context.getPolyglotBindings().getMember("WebAssembly").as(WASMModule.class);
            byte[] source = loadWasm();
            var mainModule = webAssembly.module_decode(source);
            Value memory = webAssembly.mem_alloc(108, 1000);
            Bindings bindings = new Bindings(memory);
            var wasm = webAssembly.module_instantiate(mainModule, Value.asValue(bindings)).as(OPAWASM.class);
            basedHeapPrt = wasm.opa_heap_ptr_get();
            wasm.eval(1);
            int result = wasm.opa_eval_ctx_get_result(1);
            int dumpjson = wasm.opa_json_dump(result);
            byte[] result2 = new byte[1000];
            for(int i = 0; i < 1000; i++) {
                result2[i] = memory.readBufferByte(dumpjson + i);
            }
            System.out.println("Result: " + new String(result2));
            // Get the exported function and execute it
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

//    public int loadJson(String json, OPAWASM wasm, Value memory) {
//
//        byte[] stringBytes = json.getBytes();
//        int addr = wasm.opa_malloc(stringBytes.length);
//    }

    private static byte[] loadWasm() throws IOException {
        try (var wasm = Thread.currentThread().getContextClassLoader().getResourceAsStream("policy.wasm")) {
            return Objects.requireNonNull(wasm).readAllBytes();
        }
    }
}