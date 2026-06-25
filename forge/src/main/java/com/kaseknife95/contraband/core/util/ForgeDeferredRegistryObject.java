package com.kaseknife95.contraband.core.util;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import net.minecraftforge.registries.RegistryObject;

public class ForgeDeferredRegistryObject<T> implements DeferredRegistryObject<T> {

    private final RegistryObject<T> objHolder;

    public ForgeDeferredRegistryObject(RegistryObject<T> objHolder) {
        this.objHolder = objHolder;
    }

    public T get() {
        return this.objHolder.get();
    }
}