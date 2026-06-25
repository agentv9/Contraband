package com.kaseknife95.contraband.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PsychedelicEffect extends MobEffect {

    public PsychedelicEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA33FF);
    }

    @Override
    public boolean applyEffectTick(
            LivingEntity entity,
            int amplifier
    ) {
        entity.hurt(
                entity.damageSources().magic(),
                1.0F
        );

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(
            int duration,
            int amplifier
    ) {
        return duration % 20 == 0;
    }
}
