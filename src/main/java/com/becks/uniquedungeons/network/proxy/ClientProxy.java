package com.becks.uniquedungeons.network.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.io.File;

public class ClientProxy extends Proxy {

    public ClientProxy(){
        //System.out.println("Client");
    }

    @Override
    public void init() {
    }

    @Override
    public Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Player getPlayer() {
        return Minecraft.getInstance().player;
    }


    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

}