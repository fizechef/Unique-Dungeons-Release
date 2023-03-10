package com.becks.uniquedungeons.common.blocks.lockPedestal;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlockEntity;
import com.becks.uniquedungeons.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class LockPedestalBlockEntity extends ItemBoxBlockEntity<ItemStackHandler> {

    public LockPedestalBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.LOCK_PEDESTAL.get(), p_155229_, p_155230_, defaultInventory(2));
    }
}
