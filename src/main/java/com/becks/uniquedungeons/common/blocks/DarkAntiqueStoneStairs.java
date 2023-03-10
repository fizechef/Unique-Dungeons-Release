package com.becks.uniquedungeons.common.blocks;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Supplier;

public class DarkAntiqueStoneStairs extends StairBlock {

    public DarkAntiqueStoneStairs(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }
}
