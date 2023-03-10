package com.becks.uniquedungeons.network.proxy;

import com.becks.uniquedungeons.UniqueDungeons;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;

import java.io.File;

public class ServerProxy extends Proxy {

    public ServerProxy(){
        //System.out.println("Server");
    }

    @Override
    public void init() {
    }

    @Override
    public Level getClientWorld() {
        throw new IllegalStateException("Accessing client world on server proxy");
    }

    @Override
    public Minecraft getMinecraft() {
        throw new IllegalStateException("Accessing client Minecraft on server proxy");
    }

    @Override
    public Player getPlayer() {
        throw new IllegalStateException("Accessing client player on server proxy");
    }

}