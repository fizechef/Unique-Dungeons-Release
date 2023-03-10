package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.LockingItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBox;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxItemRender;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxPuttable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LockItemBox extends ItemBox {
    private final ItemBox lockBox;

    public LockItemBox(ItemBox lockBox, VoxelShape shape, AABB aabb, int itemSlotNumber, ItemBoxItemRender render, ItemBoxPuttable puttable) {
        super(shape, aabb, itemSlotNumber, render, puttable);
        this.lockBox = lockBox;
    }

    public LockItemBox(ItemBox lockBox, VoxelShape shape, int itemSlotNumber, ItemBoxItemRender render, ItemBoxPuttable puttable) {
        super(shape, itemSlotNumber, render, puttable);
        this.lockBox = lockBox;
    }

    public ItemBox getLockBox(){
        return this.lockBox;
    }
}
