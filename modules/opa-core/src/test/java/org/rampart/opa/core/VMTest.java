package org.rampart.opa.core;

import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class VMTest {

    @Test
    public void initVM() throws Exception {
        String input = "{\"user\": \"john\"}";
        String data = "{\"role\":{\"john\":\"admin\"}}";
        byte[] wasm = loadWasm();
        byte[] stringBytes = data.getBytes();
        var vm = new VM(wasm, stringBytes, 108,1000);
        byte[] inputBytes = input.getBytes();
        vm.eval(inputBytes);
        assertNotNull(vm);
    }

    private static byte[] loadWasm() throws IOException {
        try (var wasm = Thread.currentThread().getContextClassLoader().getResourceAsStream("policy.wasm")) {
            return Objects.requireNonNull(wasm).readAllBytes();
        }
    }
}