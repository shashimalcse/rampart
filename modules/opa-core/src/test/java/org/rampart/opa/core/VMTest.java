package org.rampart.opa.core;

import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class VMTest {

    @Test
    public void validInputEvaluate() throws Exception {

        String input = "{\"user\": \"john\"}";
        String data = "{\"role\":{\"john\":\"admin\"}}";
        byte[] wasm = loadWasm();
        byte[] stringBytes = data.getBytes();
        var vm = new VM(wasm, stringBytes, 108,1000);
        byte[] inputBytes = input.getBytes();
        String response = vm.eval(inputBytes);
        assertEquals("[{\"result\":true}]", response);
    }

    @Test
    public void validInputEvaluateWithPool() throws Exception {

        String input = "{\"user\": \"john\"}";
        String data = "{\"role\":{\"john\":\"admin\"}}";
        byte[] wasm = loadWasm();
        byte[] stringBytes = data.getBytes();
        var opa = new OPA(108,1000, 5);
        opa.setPolicyData(wasm, stringBytes);
        byte[] inputBytes = input.getBytes();
        String response = opa.eval(inputBytes);
        assertEquals("[{\"result\":true}]", response);
    }

    @Test
    public void invalidInputEvaluate() throws Exception {

        String input = "{\"user\": \"john\"}";
        String data = "{\"role\":{\"john\":\"manager\"}}";
        byte[] wasm = loadWasm();
        byte[] stringBytes = data.getBytes();
        var vm = new VM(wasm, stringBytes, 108,1000);
        byte[] inputBytes = input.getBytes();
        String response = vm.eval(inputBytes);
        assertEquals("[{\"result\":false}]", response);
    }

    private static byte[] loadWasm() throws IOException {
        try (var wasm = Thread.currentThread().getContextClassLoader().getResourceAsStream("policy.wasm")) {
            return Objects.requireNonNull(wasm).readAllBytes();
        }
    }
}