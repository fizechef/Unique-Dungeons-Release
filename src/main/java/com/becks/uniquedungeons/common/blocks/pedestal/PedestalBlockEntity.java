package com.becks.uniquedungeons.common.blocks.pedestal;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlockEntity;
import com.becks.uniquedungeons.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class PedestalBlockEntity extends ItemBoxBlockEntity<ItemStackHandler> {

    public PedestalBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.PEDESTAL.get(), p_155229_, p_155230_, defaultInventory(1));
    }
}
