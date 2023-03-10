package com.becks.uniquedungeons.network.eventhandling;

import com.becks.uniquedungeons.UniqueDungeons;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DeathEventHandler {
    @SubscribeEvent
    public static void death(LivingDeathEvent event){
    }
}
