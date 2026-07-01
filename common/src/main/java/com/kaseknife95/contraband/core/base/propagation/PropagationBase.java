package com.kaseknife95.contraband.core.base.propagation;

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

import java.util.List;

public class PropagationBase extends ItemNameBlockItem {

    private final GeneticsData defaultGenetics;

    public PropagationBase(Block block, Properties properties, GeneticsData defaultGenetics) {
        super(block, properties);
        this.defaultGenetics = defaultGenetics;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);

        if (result.consumesAction() && !context.getLevel().isClientSide()) {
            Level level = context.getLevel();

            BlockPos cropPos = findPlacedCropPos(context);

            if (cropPos != null) {
                BlockEntity blockEntity = level.getBlockEntity(cropPos);

                if (blockEntity instanceof GrowableBE growableBE) {
                    GeneticsData genetics = getGeneticsFromSeed(context.getItemInHand());
                    growableBE.setGeneticsData(genetics);
                }
            }
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
        stack.set(ModDataComponents.GENETICS.get(), genetics);
    }

    public GeneticsData getGeneticsFromSeed(ItemStack stack) {
        GeneticsData genetics = stack.get(ModDataComponents.GENETICS.get());

        if (genetics != null) {
            return genetics;
        }

        return this.defaultGenetics;
    }

    public GeneticsData mutateGeneticsForTesting(GeneticsData input, RandomSource random) {
        float yieldChange = random.nextFloat() * 0.10F - 0.05F;
        float stabilityChange = random.nextFloat() * 0.10F - 0.05F;
        float qualityChange = random.nextFloat() * 0.10F - 0.05F;

        return new GeneticsData(
                input.speciesId(),
                clamp(input.yieldModifier() + yieldChange, 0.1F, 5.0F),
                clamp(input.stability() + stabilityChange, 0.1F, 5.0F),
                clamp(input.geneticQuality() + qualityChange, 0.1F, 5.0F)
        );
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        super.appendHoverText(stack, context, tooltip, flag);

        GeneticsData genetics = getGeneticsFromSeed(stack);

        tooltip.add(Component.empty());

        tooltip.add(Component.literal("Genetics")
                .withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal("Species: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(genetics.speciesId())
                        .withStyle(ChatFormatting.WHITE)));

        tooltip.add(Component.literal("Yield: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.format("%.2fx", genetics.yieldModifier()))
                        .withStyle(ChatFormatting.GOLD)));

        tooltip.add(Component.literal("Stability: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.format("%.2f", genetics.stability()))
                        .withStyle(ChatFormatting.AQUA)));

        tooltip.add(Component.literal("Genetic Quality: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.format("%.2f", genetics.geneticQuality()))
                        .withStyle(ChatFormatting.LIGHT_PURPLE)));
    }
}