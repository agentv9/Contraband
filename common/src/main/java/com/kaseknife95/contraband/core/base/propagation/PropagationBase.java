package com.kaseknife95.contraband.core.base.propagation;

import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.growables.GrowableBE;
import com.kaseknife95.contraband.core.data.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PropagationBase extends ItemNameBlockItem {

    private final GeneticsData defaultGenetics;

    public PropagationBase(Block block, Properties properties, GeneticsData defaultGenetics) {
        super(block, properties);

        if (defaultGenetics == null) {
            throw new IllegalArgumentException("defaultGenetics cannot be null");
        }

        this.defaultGenetics = defaultGenetics;
    }

    public GeneticsData defaultGenetics() {
        return this.defaultGenetics;
    }

    public GeneticsData geneticsData(ItemStack stack) {
        GeneticsData stackGenetics = stack.get(ModDataComponents.GENETICS.get());
        return stackGenetics != null ? stackGenetics : this.defaultGenetics;
    }

    public void setGenetics(ItemStack stack, GeneticsData genetics) {
        if (genetics == null) {
            stack.remove(ModDataComponents.GENETICS.get());
            return;
        }

        stack.set(ModDataComponents.GENETICS.get(), genetics);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);

        if (!result.consumesAction() || context.getLevel().isClientSide()) {
            return result;
        }

        Level level = context.getLevel();
        BlockPos cropPos = findPlacedCropPos(context);

        if (cropPos == null) {
            return result;
        }

        BlockEntity blockEntity = level.getBlockEntity(cropPos);

        if (blockEntity instanceof GrowableBE growableBE) {
            growableBE.setGeneticsData(geneticsData(context.getItemInHand()));

            BlockState state = level.getBlockState(cropPos);

            level.sendBlockUpdated(cropPos, state, state, Block.UPDATE_ALL);
            level.updateNeighborsAt(cropPos, state.getBlock());
        }

        return result;
    }

    private BlockPos findPlacedCropPos(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();

        BlockPos above = clickedPos.above();

        if (level.getBlockEntity(above) instanceof GrowableBE) {
            return above;
        }

        if (level.getBlockEntity(clickedPos) instanceof GrowableBE) {
            return clickedPos;
        }

        return null;
    }

    public void setGeneticsOnSeed(ItemStack stack, GeneticsData genetics) {
        setGenetics(stack, genetics);
    }

    public GeneticsData getGeneticsFromSeed(ItemStack stack) {
        return geneticsData(stack);
    }

    public GeneticsData mutateGeneticsForTesting(GeneticsData input, RandomSource random) {
        float yieldChange = random.nextFloat() * 0.10F - 0.05F;
        float stabilityChange = random.nextFloat() * 0.10F - 0.05F;
        float qualityChange = random.nextFloat() * 0.10F - 0.05F;

        return new GeneticsData(
                input.speciesId(),
                input.strainId(),
                input.strainName(),
                clamp(input.yieldModifier() + yieldChange, 0.1F, 5.0F),
                clamp(input.stability() + stabilityChange, 0.1F, 5.0F),
                clamp(input.geneticQuality() + qualityChange, 0.1F, 5.0F),
                input.primaryColor(),
                input.secondaryColor()
        );
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        super.appendHoverText(stack, context, tooltip, flag);

        GeneticsData genetics = geneticsData(stack);

        tooltip.add(Component.empty());

        tooltip.add(Component.literal("Genetics")
                .withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal("Species: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(genetics.speciesId())
                        .withStyle(ChatFormatting.WHITE)));

        tooltip.add(Component.literal("Strain: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(genetics.strainName())
                        .withStyle(ChatFormatting.GOLD)));

        tooltip.add(Component.literal("Strain ID: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(genetics.strainId())
                        .withStyle(ChatFormatting.DARK_GRAY)));

        tooltip.add(Component.literal("Yield: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(formatMultiplier(genetics.yieldModifier()))
                        .withStyle(ChatFormatting.GOLD)));

        tooltip.add(Component.literal("Stability: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(formatMultiplier(genetics.stability()))
                        .withStyle(ChatFormatting.AQUA)));

        tooltip.add(Component.literal("Genetic Quality: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(formatMultiplier(genetics.geneticQuality()))
                        .withStyle(ChatFormatting.LIGHT_PURPLE)));

        tooltip.add(Component.literal("Primary Color: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("#" + formatColor(genetics.primaryColor()))
                        .withStyle(ChatFormatting.WHITE)));

        tooltip.add(Component.literal("Secondary Color: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("#" + formatColor(genetics.secondaryColor()))
                        .withStyle(ChatFormatting.WHITE)));
    }

    public int getTintColor(ItemStack stack, int tintIndex) {

        GeneticsData genetics = geneticsData(stack);

        if (genetics == null) {
            return tintIndex == 0 ? 0xFFFFFFFF : 0xFFFFFFFF;
        }

        return switch (tintIndex) {
            case 1 -> 0xFF000000 | genetics.primaryColor();
            case 2 -> 0xFF000000 | genetics.secondaryColor();
            default -> 0xFFFFFFFF;
        };
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private static String formatMultiplier(float value) {
        return String.format("%.2fx", value);
    }

    private static String formatColor(int color) {
        return String.format("%06X", color & 0xFFFFFF);
    }
}