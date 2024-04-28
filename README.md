## Open Policy Agent (OPA) JAVA Library
JAVA library for execute WebAssembly (wasm) compiled Open Policy Agent Rego policies.

### Examples

```
public class Main {

    public static void main(String[] args) throws IOException {

        String input = "{\"user\": \"john\"}";
        String data = "{\"role\":{\"john\":\"manager\"}}";
        byte[] wasm = loadFileFromResources("policy.wasm");
        byte[] dataBytes = data.getBytes();
        byte[] inputBytes = input.getBytes();
        var vm = new VM(wasm, dataBytes, 108,1000);
        String response = vm.eval(inputBytes);
        System.out.println(response);
    }

    private static byte[] loadFileFromResources(String filename) throws IOException {
        try (var file = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            return Objects.requireNonNull(file).readAllBytes();
        }
    }
}
```
#### Check the [attribute-based access control sample](https://github.com/shashimalcse/rampart/tree/main/modules/opa-samples/attribute-based-access-control) 

### Contributing
Work in Progress -- Contributions are welcome! 

