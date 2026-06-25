package com.kaseknife95.contraband.core.base.drugs;

import com.kaseknife95.contraband.core.base.substances.SubstanceData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public abstract class DrugBase extends Item {

    private final DrugData drugData;

    protected DrugBase(Properties properties, DrugData drugData) {
        super(properties);

        if (drugData == null) {
            throw new IllegalArgumentException("drugData cannot be null");
        }

        this.drugData = drugData;
    }

    public DrugData drugData() {
        return drugData;
    }

    public Optional<SubstanceData> substanceData() {
        return Optional.ofNullable(drugData.substanceData());
    }

    public boolean hasSubstanceData() {
        return drugData.substanceData() != null;
    }

    public boolean hasGenetics() {
        return drugData.geneticsData() != null;
    }

    public float getEffectivePotency() {
        float modifier = substanceData()
                .map(SubstanceData::potencyModifier)
                .orElse(1.0F);

        return drugData.basePotency() * modifier;
    }

    public float getEffectiveQuality() {
        float modifier = substanceData()
                .map(SubstanceData::qualityModifier)
                .orElse(1.0F);

        return drugData.baseQuality() * modifier;
    }

    public int getTintColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            return 0xFF000000 | drugData.drugColor();
        }

        return 0xFFFFFFFF;
    }

    @Override
    public Component getName(ItemStack stack) {
        return substanceData()
                .map(data -> Component.literal(data.strainName()))
                .orElse((MutableComponent) super.getName(stack));
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

        tooltip.add(Component.literal("Base Drug: " + drugData.displayName())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Base Quality: " + formatMultiplier(drugData.baseQuality()))
                .withStyle(ChatFormatting.GREEN));

        substanceData().ifPresent(data -> addSubstanceTooltip(data, tooltip));

        if (hasGenetics()) {
            addGeneticsTooltip(tooltip);
        }
    }

    protected void addSubstanceTooltip(SubstanceData data, List<Component> tooltip) {
        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Substance")
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Variant: " + data.strainName())
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.literal("Potency Modifier: " + formatMultiplier(data.potencyModifier()))
                .withStyle(ChatFormatting.LIGHT_PURPLE));

        tooltip.add(Component.literal("Quality Modifier: " + formatMultiplier(data.qualityModifier()))
                .withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal("Purity: " + formatPercent(data.purity()))
                .withStyle(ChatFormatting.AQUA));
    }

    protected void addGeneticsTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Genetics")
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Species: " + drugData.geneticsData().speciesId())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Genetic Quality: " + formatMultiplier(drugData.geneticsData().geneticQuality()))
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Stability: " + formatMultiplier(drugData.geneticsData().stability()))
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Yield Modifier: " + formatMultiplier(drugData.geneticsData().yieldModifier()))
                .withStyle(ChatFormatting.GREEN));
    }

    protected String formatMultiplier(float value) {
        return String.format("%.2fx", value);
    }

    protected String formatPercent(float value) {
        return String.format("%.0f%%", value * 100.0F);
    }
}