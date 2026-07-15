package com.kaseknife95.contraband.client.cropsticks;

import com.kaseknife95.contraband.core.base.cropsticks.CropStickBE;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.RandomSource;
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

        if (blockEntity.getLevel() == null) {
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
         * Slight offset prevents z-fighting with the crop sticks.
         */
        poseStack.translate(
                0.0D,
                0.001D,
                0.0D
        );

        VertexConsumer vertexConsumer =
                bufferSource.getBuffer(RenderType.cutout());

        this.blockRenderer.renderBatched(
                plantState,
                blockEntity.getBlockPos(),
                blockEntity.getLevel(),
                poseStack,
                vertexConsumer,
                false,
                RandomSource.create(
                        plantState.getSeed(
                                blockEntity.getBlockPos()
                        )
                )
        );

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(
            CropStickBE blockEntity
    ) {
        return false;
    }
}