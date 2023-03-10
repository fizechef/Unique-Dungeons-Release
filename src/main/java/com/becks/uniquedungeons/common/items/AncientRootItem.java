package com.becks.uniquedungeons.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class AncientRootItem extends Item {
    public AncientRootItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> type) {
        return 200;
    }
}
