package com.kaseknife95.contraband.core.base.drugs;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;
import com.kaseknife95.contraband.core.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public abstract class DrugBase extends Item {

    private final DrugData baseDrugData;

    protected DrugBase(Properties properties, DrugData baseDrugData) {
        super(properties);

        if (baseDrugData == null) {
            throw new IllegalArgumentException("baseDrugData cannot be null");
        }

        this.baseDrugData = baseDrugData;
    }

    public DrugData baseDrugData() {
        return this.baseDrugData;
    }

    public DrugData drugData(ItemStack stack) {
        DrugData stackData = stack.get(ModDataComponents.DRUG_DATA.get());
        return stackData != null ? stackData : this.baseDrugData;
    }

    public Optional<SubstanceData> substanceData(ItemStack stack) {
        return Optional.ofNullable(drugData(stack).substanceData());
    }

    public Optional<GeneticsData> geneticsData(ItemStack stack) {
        return Optional.ofNullable(drugData(stack).geneticsData());
    }

    public boolean hasSubstanceData(ItemStack stack) {
        return drugData(stack).substanceData() != null;
    }

    public boolean hasGenetics(ItemStack stack) {
        return drugData(stack).geneticsData() != null;
    }

    public float getEffectivePotency(ItemStack stack) {
        DrugData data = drugData(stack);

        float modifier = data.substanceData() != null
                ? data.substanceData().potencyModifier()
                : 1.0F;

        return data.basePotency() * modifier;
    }

    public float getEffectiveQuality(ItemStack stack) {
        DrugData data = drugData(stack);

        float modifier = data.substanceData() != null
                ? data.substanceData().qualityModifier()
                : 1.0F;

        return data.baseQuality() * modifier;
    }

    public int getTintColor(ItemStack stack, int tintIndex) {
        DrugData data = drugData(stack);
        GeneticsData genetics = data.geneticsData();

        if (genetics == null) {
            return tintIndex == 0 ? 0xFFFFFFFF : 0xFFFFFFFF;
        }

        return switch (tintIndex) {
            case 1 -> 0xFF000000 | genetics.primaryColor();
            case 2 -> 0xFF000000 | genetics.secondaryColor();
            default -> 0xFFFFFFFF;
        };
    }

    @Override
    public Component getName(ItemStack stack) {
        DrugData data = drugData(stack);

        if (data.substanceData() != null && data.substanceData().strainName() != null && !data.substanceData().strainName().isBlank()) {
            return Component.literal(data.substanceData().strainName());
        }

        if (data.displayName() != null && !data.displayName().isBlank()) {
            return Component.literal(data.displayName());
        }

        return super.getName(stack);
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
        DrugData data = drugData(stack);

        tooltip.add(Component.literal("Base Drug: " + data.displayName())
                .withStyle(ChatFormatting.AQUA));

        tooltip.add(Component.literal("Type: " + data.drugType().displayName())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Potency: " + formatMultiplier(getEffectivePotency(stack)))
                .withStyle(ChatFormatting.LIGHT_PURPLE));

        tooltip.add(Component.literal("Quality: " + formatMultiplier(getEffectiveQuality(stack)))
                .withStyle(ChatFormatting.GREEN));
    }

    protected void addExpandedTooltip(ItemStack stack, List<Component> tooltip) {
        DrugData data = drugData(stack);

        tooltip.add(Component.empty());

        tooltip.add(Component.literal("Drug Info")
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal(data.drugType().description())
                .withStyle(ChatFormatting.DARK_GRAY));

        tooltip.add(Component.literal("Drug ID: " + data.drugId())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Display Name: " + data.displayName())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Base Potency: " + formatMultiplier(data.basePotency()))
                .withStyle(ChatFormatting.LIGHT_PURPLE));

        tooltip.add(Component.literal("Base Quality: " + formatMultiplier(data.baseQuality()))
                .withStyle(ChatFormatting.GREEN));

        if (data.substanceData() != null) {
            addSubstanceTooltip(data.substanceData(), tooltip);
        }

        if (data.geneticsData() != null) {
            addGeneticsTooltip(data.geneticsData(), tooltip);
        }
    }

    protected void addSubstanceTooltip(SubstanceData data, List<Component> tooltip) {
        tooltip.add(Component.empty());

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

    protected void addGeneticsTooltip(GeneticsData data, List<Component> tooltip) {
        tooltip.add(Component.empty());

        tooltip.add(Component.literal("Genetics")
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Species: " + data.speciesId())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Strain ID: " + data.strainId())
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Strain Name: " + data.strainName())
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.literal("Genetic Quality: " + formatMultiplier(data.geneticQuality()))
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Stability: " + formatMultiplier(data.stability()))
                .withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("Yield Modifier: " + formatMultiplier(data.yieldModifier()))
                .withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal("Primary Color: #" + formatColor(data.primaryColor()))
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("Secondary Color: #" + formatColor(data.secondaryColor()))
                .withStyle(ChatFormatting.GRAY));
    }

    protected String formatMultiplier(float value) {
        return String.format("%.2fx", value);
    }

    protected String formatPercent(float value) {
        return String.format("%.0f%%", value * 100.0F);
    }

    protected String formatColor(int color) {
        return String.format("%06X", color & 0xFFFFFF);
    }
}