package org.rampart.opa.core.opa;

import org.graalvm.polyglot.Value;

public class OPABindings {

    public final Env env;

    public OPABindings(Value memory) {

        env = new Env(memory);
    }

    public static class Env {
        public final Value memory;

        public Env(Value memory) {
            this.memory = memory;
        }

        public void opa_abort(int addr) {

        }

        public void opa_builtin0(int builtin_id, int ctx) {

        }

        public void opa_builtin1(int builtin_id, int ctx, int arg1) {

        }

        public void opa_builtin2(int builtin_id, int ctx, int arg1, int arg2) {

        }

        public void opa_builtin3(int builtin_id, int ctx, int arg1, int arg2, int arg3) {

        }

        public void opa_builtin4(int builtin_id, int ctx, int arg1, int arg2, int arg3, int arg4) {

        }
    }
}
