package com.becks.uniquedungeons.common.items.artifacts.armorset;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class ArmorSetUtil {
    public static boolean playerWearingFullSet(Player player, ArmorSet set){
        Iterable<ItemStack> armorSlots = ((Player)player).getArmorSlots();
        for (ItemStack stack : armorSlots){
            if (stack != null && stack.getItem() instanceof ArmorSetItem){
                if (!((ArmorSetItem) stack.getItem()).getSet().equals(set)){
                    return false;
                }
            }
        }
        return true;
    }
}
