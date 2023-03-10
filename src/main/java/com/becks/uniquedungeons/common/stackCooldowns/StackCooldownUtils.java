package com.becks.uniquedungeons.common.stackCooldowns;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.PlayerUnlockData;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import com.becks.uniquedungeons.network.packets.PaketSyncUnlock;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class StackCooldownUtils {
    @OnlyIn(Dist.CLIENT)
    public static void updateClientCooldowns(@Nullable ClientboundStackCooldownPacket pkt) {
        if (UniqueDungeons.proxy.getPlayer() instanceof ServerPlayer){
            return;
        }
       //System.out.println("Updating Client Cooldowns with Duration" + pkt.getDuration());
        if (pkt.getDuration() <= 0) {
            StackCooldowns cooldowns = StackCooldownMap.getCooldowns(UniqueDungeons.proxy.getPlayer());
           //System.out.println(cooldowns instanceof ServerStackCooldowns);
            if (cooldowns != null){
                cooldowns.removeCooldown(pkt.getItem(), true);
            }
        } else {
            StackCooldowns cooldowns = StackCooldownMap.getCooldowns(UniqueDungeons.proxy.getPlayer());
           //System.out.println(cooldowns instanceof ServerStackCooldowns);
            if (cooldowns != null){
                cooldowns.addCooldown(pkt.getItem(), pkt.getDuration(), true);
            }
        }
    }
}
