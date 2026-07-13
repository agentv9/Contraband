package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.block.ModBlockEntities;
import com.kaseknife95.contraband.client.cropsticks.CropModels;
import com.kaseknife95.contraband.client.cropsticks.CropStickBER;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientRenderRefresh.init(pos -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.levelRenderer != null) {
                minecraft.levelRenderer.setBlocksDirty(
                        pos.getX(), pos.getY(), pos.getZ(),
                        pos.getX(), pos.getY(), pos.getZ()
                );
            }
        });

        CommonColorRegistry.tintableItems().forEach(item ->
                ColorProviderRegistry.ITEM.register(
                        (stack, tintIndex) -> {
                            if (stack.getItem() instanceof DrugBase drugItem) {
                                return drugItem.getTintColor(stack, tintIndex);
                            }

                            if (stack.getItem() instanceof PropagationBase seedItem) {
                                return seedItem.getTintColor(stack, tintIndex);
                            }
                            return 0xFFFFFFFF;
                        },
                        item.get()
                )
        );

        CommonColorRegistry.tintableBlocks().forEach(block ->
                ColorProviderRegistry.BLOCK.register(
                        GrowableBase::getTintColor,
                        block.get()
                ));

        CropModels.loadClass();

        BlockEntityRenderers.register(
                ModBlockEntities.CROP_STICK.get(),
                CropStickBER::new
        );
    }


}
