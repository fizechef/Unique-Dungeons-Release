package com.becks.uniquedungeons.common.items.artifacts.abstracts;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Collection;

public interface ArtifactItem{

    abstract Collection<Component> generateInfoLoreText(ItemStack stack, Level level);

    abstract Item getRepairItem();

}
