package com.kaseknife95.psychedelicraft.core.util;

public class FabricDeferredRegistryObject<T> implements DeferredRegistryObject<T> {

    private final T obj;

    public FabricDeferredRegistryObject(T obj) {
        this.obj = obj;
    }

    public T get() {
        return this.obj;
    }
}
