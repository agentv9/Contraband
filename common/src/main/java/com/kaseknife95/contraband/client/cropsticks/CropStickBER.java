package com.kaseknife95.contraband.client.cropsticks;

import com.kaseknife95.contraband.core.base.cropsticks.CropStickBE;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CropStickBER
        implements BlockEntityRenderer<CropStickBE> {

    private final BlockRenderDispatcher blockRenderer;

    public CropStickBER(
            BlockEntityRendererProvider.Context context
    ) {
        this.blockRenderer =
                Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(
            CropStickBE blockEntity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        GeneticsData genetics =
                blockEntity.getGeneticsData();

        if (genetics == null) {
            return;
        }

        BlockState plantState =
                CropModelRegistry.getPlantState(
                        genetics.speciesId(),
                        blockEntity.getAge()
                );

        if (plantState == null) {
            return;
        }

        poseStack.pushPose();

        /*
         * Tiny offset reduces visual overlap between the crop and sticks.
         */
        poseStack.translate(
                0.0D,
                0.001D,
                0.0D
        );

        this.blockRenderer.renderSingleBlock(
                plantState,
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay
        );

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(CropStickBE blockEntity) {
        return false;
    }
}