package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public enum ItemBoxPuttable {
    NO_BLOCK_ITEM(){
        @Override
        public boolean puttable(Item item) {
            return !(item instanceof BlockItem);
        }
    };

    public abstract boolean puttable(Item item);
}
