package com.kaseknife95.contraband.core.util;

import java.util.function.Supplier;

public class NeoForgeDeferredRegistryObject<T> implements DeferredRegistryObject<T> {

    private final Supplier<T> object;

    public NeoForgeDeferredRegistryObject(Supplier<T> object) {
        this.object = object;
    }

    @Override
    public T get() {
        return object.get();
    }
}