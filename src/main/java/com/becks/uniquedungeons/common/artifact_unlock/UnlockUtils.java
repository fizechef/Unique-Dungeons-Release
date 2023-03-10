package com.becks.uniquedungeons.common.artifact_unlock;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.network.Network;
import com.becks.uniquedungeons.network.packets.PaketSyncUnlock;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UnlockUtils {
    private static final Map<UUID, PlayerUnlockData> serverSideUnlockData = new HashMap<>();

    private static PlayerUnlockData clientSideUnlockData = new ClientsidePLayerUnlockData();


    @Nonnull
    public static PlayerUnlockData getUnlockData(@Nullable Player player, LogicalSide side) {
       //System.out.println("Getting Data");
        if (side.isClient()) {
           //System.out.println("Client Data");
            return getClientUnlockData();
        } else if (player instanceof ServerPlayer) {
           //System.out.println("Server Data");
            return getServerUnlockData((ServerPlayer) player);
        } else {
           //System.out.println("New Client Data");
            return new ClientsidePLayerUnlockData();
        }
    }

    @Nonnull
    public static PlayerUnlockData getClientUnlockData() {
        return clientSideUnlockData;
    }

    @Nonnull
    private static PlayerUnlockData getServerUnlockData(ServerPlayer player) {
        return getUnlockData(player.getUUID());
    }

    public static void saveKnownArtifacts(Player p) {
       //System.out.println("Saving Server Data");
        if (p instanceof ServerPlayer) {
            UUID pUUID = p.getUUID();
            if (serverSideUnlockData.get(pUUID) != null){
                PlayerUnlockData pData = serverSideUnlockData.get(pUUID);
                CompoundTag nbt = new CompoundTag();
                pData.saveToNBT(nbt);
                PaketSyncUnlock pkt = new PaketSyncUnlock(nbt);
                Network.sendToPlayer(pkt, (ServerPlayer)p);
            }
            savePlayerKnowledge(pUUID, true);
        }
    }

    @Nonnull
    private static PlayerUnlockData getUnlockData(UUID uuid) {
       //System.out.println("Data for UUID " + uuid);
        PlayerUnlockData progress = serverSideUnlockData.get(uuid);
        if (progress == null) {
            loadPlayerKnowledge(uuid);
            progress = serverSideUnlockData.get(uuid);
        }
        return progress;
    }

    public static void unlockArtifact(Player player, ArtifactItem artifact){
       //System.out.println("Unlocking " + artifact);
        getUnlockData(player, player instanceof ServerPlayer ? LogicalSide.SERVER: LogicalSide.CLIENT).unlock(artifact);
        saveKnownArtifacts(player);
    }

    @OnlyIn(Dist.CLIENT)
    public static void updateClientResearch(@Nullable PaketSyncUnlock pkt) {
       //System.out.println("Updating Client Data");
        UnlockUtils.clientSideUnlockData = new PlayerUnlockData();
        if (pkt != null) {
            UnlockUtils.clientSideUnlockData.sync(pkt);
        }
    }

    public static void loadPlayerKnowledge(ServerPlayer p) {
       //System.out.println("Loading Data");
        PaketSyncUnlock pkt = new PaketSyncUnlock(loadPlayerKnowledge(p.getUUID()));
        Network.sendToPlayer(pkt, p);
    }

    private static CompoundTag loadPlayerKnowledge(UUID pUUID) {
       //System.out.println("Loading Data with UUID");
        File playerFile = getPlayerFile(pUUID);
        CompoundTag nbt = null;
        try {
            nbt = NbtIo.read(playerFile);
            PlayerUnlockData progress = new PlayerUnlockData();
            if (nbt != null && !nbt.isEmpty()) {
                progress.loadFromNBT(nbt);
            }
            serverSideUnlockData.put(pUUID, progress);
            return nbt;
        }
        catch (Exception e) {
           //System.out.println("SHHEEEEESH");
            return new CompoundTag();
        }
    }

    private static void savePlayerKnowledge(UUID pUUID, boolean force) {
        if (serverSideUnlockData.get(pUUID) == null) return;
        PlayerUnlockData pData = serverSideUnlockData.get(pUUID);
        if (force) {
            ArtifactUnlockServer.saveNow(pUUID, pData);
        } else {
            //ArtifactUnlockServer.saveProgress(pUUID, pData.copy());
        }
    }

    public static File getPlayerFile(UUID pUUID) {
        File f = new File(getPlayerDirectory(), pUUID.toString() + "." + UniqueDungeons.MOD_ID);
        if (!f.exists()) {
            try {
                NbtIo.write(new CompoundTag(), f);
            } catch (IOException ignored) {}
        }
        return f;
    }

    private static File getPlayerDirectory() {
        File pDir = new File(UniqueDungeons.proxy.getServerDataDirectory(), "playerdata");
        if (!pDir.exists()) {
            pDir.mkdirs();
        }
       //System.out.println(pDir);
        return pDir;
    }
}
