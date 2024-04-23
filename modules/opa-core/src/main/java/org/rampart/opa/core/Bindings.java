package org.rampart.opa.core;

import org.graalvm.polyglot.Value;

public class Bindings {
    public final Env env;
    public Bindings(Value memory) {
        env = new Env(memory);
    }
    public static class Env {
        /**
         * WASM memory that is used to quickly share data between java and WASM
         */
        public final Value memory;

        public Env(Value memory) {
            this.memory = memory;
        }

        public void opa_abort(int code) {

        }

        public void opa_builtin0(int code1, int code2, int code3) {

        }

        public void opa_builtin1(int code1, int code2, int code3) {

        }

        public void opa_builtin2(int code1, int code2, int code3, int code4, int code5) {

        }

        public void opa_builtin3(int code1, int code2, int code3, int code4, int code5, int code6) {

        }

        public void opa_builtin4(int code1, int code2, int code3, int code4, int code5, int code6, int code7) {

        }
    }
}
