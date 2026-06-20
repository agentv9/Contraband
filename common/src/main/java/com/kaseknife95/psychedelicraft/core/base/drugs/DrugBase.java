package com.kaseknife95.psychedelicraft.core.base.drugs;

import com.kaseknife95.psychedelicraft.core.base.genetics.GeneticsData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public abstract class DrugBase extends Item {

    private final DrugData drugData;
    private final GeneticsData defaultGenetics;

    protected DrugBase(Properties properties, DrugData drugData) {
        this(properties, drugData, null);
    }

    protected DrugBase(Properties properties, DrugData drugData, GeneticsData defaultGenetics) {
        super(properties);

        if (drugData == null) {
            throw new IllegalArgumentException("drugData cannot be null");
        }

        this.drugData = drugData;
        this.defaultGenetics = defaultGenetics;
    }

    public DrugData drugData() {
        return drugData;
    }

    public Optional<GeneticsData> defaultGenetics() {
        return Optional.ofNullable(defaultGenetics);
    }

    public boolean hasGenetics() {
        return defaultGenetics != null;
    }

    public float getEffectivePotency() {
        if (defaultGenetics == null) {
            return drugData.basePotency();
        }

        return drugData.basePotency() * defaultGenetics.potencyModifier();
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        addBasicTooltip(stack, tooltip);

        if (Screen.hasShiftDown()) {
            addExpandedTooltip(stack, tooltip);
        } else {
            tooltip.add(Component.literal("Hold SHIFT for more information")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    protected void addBasicTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal(drugData.displayName())
                .withStyle(ChatFormatting.AQUA));

        tooltip.add(Component.literal("Type: " + drugData.drugType().displayName())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Potency: " + formatMultiplier(getEffectivePotency()))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    protected void addExpandedTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Drug Info")
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal(drugData.drugType().description())
                .withStyle(ChatFormatting.DARK_GRAY));

        tooltip.add(Component.literal("Base Quality: " + formatMultiplier(drugData.baseQuality()))
                .withStyle(ChatFormatting.GREEN));

        if (defaultGenetics != null) {
            tooltip.add(Component.literal(""));

            tooltip.add(Component.literal("Genetics")
                    .withStyle(ChatFormatting.YELLOW));

            tooltip.add(Component.literal("Strain: " + defaultGenetics.strainName())
                    .withStyle(ChatFormatting.GOLD));

            tooltip.add(Component.literal("Genetic Quality: " + formatMultiplier(defaultGenetics.quality()))
                    .withStyle(ChatFormatting.YELLOW));

            tooltip.add(Component.literal("Stability: " + formatMultiplier(defaultGenetics.stability()))
                    .withStyle(ChatFormatting.YELLOW));

            tooltip.add(Component.literal("Potency Modifier: " + formatMultiplier(defaultGenetics.potencyModifier()))
                    .withStyle(ChatFormatting.LIGHT_PURPLE));

            tooltip.add(Component.literal("Yield Modifier: " + formatMultiplier(defaultGenetics.yieldModifier()))
                    .withStyle(ChatFormatting.GREEN));
        }
    }

    protected String formatMultiplier(float value) {
        return String.format("%.2fx", value);
    }
}