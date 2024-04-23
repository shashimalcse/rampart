package org.rampart.opa.core;

public interface OPAWASM {

    public int eval(int ctxAddr);

    public int opa_eval_ctx_get_result(int ctxAddr);
    public int opa_json_parse(int addr, int jsonLength);
    public int opa_json_dump(int valueAddr);
    public int opa_heap_ptr_get();
    public int opa_heap_ptr_set(int addr);

    public int opa_malloc(int bytes);
}
