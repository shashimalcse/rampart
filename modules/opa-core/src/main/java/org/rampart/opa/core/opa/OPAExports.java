package org.rampart.opa.core.opa;

public interface OPAExports {

    int eval(int ctxAddr);

    int opa_eval_ctx_get_result(int ctxAddr);

    int opa_eval_ctx_new();

    void opa_eval_ctx_set_data(int ctxAddr, int valueAddr);

    void opa_eval_ctx_set_input(int ctxAddr, int valueAddr);

    void opa_free(int addr);

    int opa_heap_ptr_get();

    void opa_heap_ptr_set(int addr);

    int opa_heap_top_get();

    void opa_heap_top_set(int addr);

    int opa_json_dump(int valueAddr);

    int opa_json_parse(int strAddr, int size);

    int opa_malloc(int size);
}
