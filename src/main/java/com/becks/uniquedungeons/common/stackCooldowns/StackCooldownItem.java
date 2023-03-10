package com.becks.uniquedungeons.common.stackCooldowns;

import com.becks.uniquedungeons.UniqueDungeons;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public interface StackCooldownItem {

    static final String COOLDOWN_KEY = UniqueDungeons.MOD_ID + ".stackCooldownID";
    public String getCooldownID(ItemStack stack);

    public void onCooldownEnd(Player player, ItemStack stack);
}
