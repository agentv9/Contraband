package com.kaseknife95.contraband.effect;

import com.kaseknife95.contraband.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class CannabisEffect extends MobEffect {

    private static final int TICK_INTERVAL = 40;

    public CannabisEffect() {
        super(MobEffectCategory.NEUTRAL, 0x5E9E45);

        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        "cannabis_movement_speed"
                ),
                -0.025D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        "cannabis_attack_speed"
                ),
                -0.04D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        "cannabis_attack_damage"
                ),
                -0.02D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public boolean applyEffectTick(
            LivingEntity entity,
            int amplifier
    ) {
        if (entity instanceof Player player) {
            float intensity = Math.min(
                    (amplifier + 1.0F) / 4.0F,
                    1.0F
            );

            player.causeFoodExhaustion(
                    0.08F + intensity * 0.20F
            );
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(
            int duration,
            int amplifier
    ) {
        return duration % TICK_INTERVAL == 0;
    }
}