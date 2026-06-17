package com.kaseknife95.psychedelicraft.core.util;


import java.util.function.Supplier;

public interface DeferredRegistryObject<T> extends Supplier<T> {
    T get();
}
