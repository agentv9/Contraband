package com.kaseknife95.contraband.client;

import net.minecraft.core.BlockPos;

public final class ClientRenderRefresh {
    private static Handler handler;

    private ClientRenderRefresh() {}

    public static void init(Handler handler) {
        ClientRenderRefresh.handler = handler;
    }

    public static void refresh(BlockPos pos) {
        if (handler != null) {
            handler.refresh(pos);
        }
    }

    public interface Handler {
        void refresh(BlockPos pos);
    }
}