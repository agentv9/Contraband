package com.kaseknife95.contraband.Effects;


import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class Effects {

    public static final DeferredRegistryObject<MobEffect> PSYCHEDELIC =
            Services.PLATFORM.register(
                    BuiltInRegistries.MOB_EFFECT,
                    "psychedelic",
                    PsychedelicEffect::new
            );

    public static void loadClass() {}
}
