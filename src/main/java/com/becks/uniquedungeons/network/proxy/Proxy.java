package com.becks.uniquedungeons.network.proxy;

import com.becks.uniquedungeons.UniqueDungeons;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;

import java.io.File;

public abstract class Proxy {

    abstract void init();

    public abstract Level getClientWorld();

    public abstract Minecraft getMinecraft();

    public abstract Player getPlayer();

    /**
     * Credit to Astral Sorcery for this.
     * @return Server directory to save stuff into
     */
    public File getServerDataDirectory() {
        MinecraftServer server = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        if (server == null) {
            return null;
        }

        File dataDir = server.getWorldPath(new LevelResource(UniqueDungeons.MOD_ID)).toFile();
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        return dataDir;
    }
}
