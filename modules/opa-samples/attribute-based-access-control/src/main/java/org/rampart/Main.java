package org.rampart;

import org.rampart.opa.core.VM;

import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException {

        byte[] wasm = loadFileFromResources("abac.wasm");
        byte[] data = loadFileFromResources("data.json");
        byte[] input = loadFileFromResources("input.json");
        var vm = new VM(wasm, data, 108,1000);
        String response = vm.eval(input);
        System.out.println(response);

    }

    private static byte[] loadFileFromResources(String filename) throws IOException {
        try (var file = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            return Objects.requireNonNull(file).readAllBytes();
        }
    }
}