package com.becks.uniquedungeons.network;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.stackCooldowns.ClientboundStackCooldownPacket;
import com.becks.uniquedungeons.network.packets.PaketSyncUnlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class Network {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(UniqueDungeons.MOD_ID, "network"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(),
                PaketSyncUnlock.class,
                PaketSyncUnlock::toBytes,
                PaketSyncUnlock::new,
                PaketSyncUnlock::handle);

        INSTANCE.registerMessage(nextID(),
                ClientboundStackCooldownPacket.class,
                ClientboundStackCooldownPacket::toBytes,
                ClientboundStackCooldownPacket::new,
                ClientboundStackCooldownPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

    }

    public static void sendToNearby(Level world, BlockPos pos, Object toSend) {
        if (world instanceof ServerLevel ws) {
            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).stream()
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(Level world, Entity e, Object toSend) {
        sendToNearby(world, e.blockPosition(), toSend);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
