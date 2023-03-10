package com.becks.uniquedungeons.common.stackCooldowns;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class StackCooldownMap {

    private static final Map<Player, StackCooldowns> map = new HashMap<>();

    public static StackCooldowns getCooldowns(Player player){
        if (map.containsKey(player)){
            return map.get(player);
        }
       //System.out.println("Creating new Cooldowm instance for " + player);
        StackCooldowns newCooldowns;
        if (player instanceof ServerPlayer){
            newCooldowns = new ServerStackCooldowns((ServerPlayer)player);
        }
        else {
            newCooldowns = new StackCooldowns();
        }
        map.put(player, newCooldowns);
        return newCooldowns;
    }
}
