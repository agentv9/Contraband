package com.kaseknife95.contraband.core.util;


import java.util.function.Supplier;

public interface DeferredRegistryObject<T> extends Supplier<T> {
    T get();
}
