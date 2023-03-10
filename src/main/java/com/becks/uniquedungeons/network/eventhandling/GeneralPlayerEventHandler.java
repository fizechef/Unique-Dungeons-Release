package com.becks.uniquedungeons.network.eventhandling;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UniqueDungeons.MOD_ID)
public class GeneralPlayerEventHandler {


    @SubscribeEvent
    public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            UnlockUtils.loadPlayerKnowledge((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            UnlockUtils.loadPlayerKnowledge((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerDimChangedEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            UnlockUtils.loadPlayerKnowledge((ServerPlayer) event.getEntity());
        }
    }
}
