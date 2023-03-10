package com.becks.uniquedungeons.common.blocks;

import com.becks.uniquedungeons.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DriedQuickSand extends SandBlock {
    public DriedQuickSand(Properties p_55968_) {
        super(12345, p_55968_);
    }

    private static boolean touchesLiquid(BlockGetter p_52065_, BlockPos p_52066_, BlockState state) {
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = p_52066_.mutable();
        for(Direction direction : Direction.values()) {
            if (p_52065_.getBlockState(p_52066_.relative(direction)).getBlock().equals(BlockInit.QUICK_SAND.get())){
                flag = true;
                break;
            }
            BlockState blockstate = p_52065_.getBlockState(blockpos$mutableblockpos);
            if (direction != Direction.DOWN || state.canBeHydrated(p_52065_, p_52066_, blockstate.getFluidState(), blockpos$mutableblockpos)) {
                blockpos$mutableblockpos.setWithOffset(p_52066_, direction);
                blockstate = p_52065_.getBlockState(blockpos$mutableblockpos);
                if (state.canBeHydrated(p_52065_, p_52066_, blockstate.getFluidState(), blockpos$mutableblockpos) && !blockstate.isFaceSturdy(p_52065_, p_52066_, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_52063_) {
        BlockGetter blockgetter = p_52063_.getLevel();
        BlockPos blockpos = p_52063_.getClickedPos();
        BlockState blockstate = blockgetter.getBlockState(blockpos);
        return shouldTurnWet(blockgetter, blockpos, blockstate) ? BlockInit.QUICK_SAND.get().defaultBlockState() : super.getStateForPlacement(p_52063_);
    }

    private static boolean shouldTurnWet(BlockGetter p_52081_, BlockPos p_52082_, BlockState p_52083_) {
        return p_52083_.canBeHydrated(p_52081_, p_52082_, p_52081_.getFluidState(p_52082_), p_52082_) || touchesLiquid(p_52081_, p_52082_, p_52083_);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos2, Block block, BlockPos pos, boolean flag) {
        super.neighborChanged(state, level, pos2, block, pos, flag);
        level.scheduleTick(pos, this, 2);
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        super.tick(state, serverLevel, pos, random);
        serverLevel.setBlock(pos,(touchesLiquid(serverLevel, pos, state) ? BlockInit.QUICK_SAND.get().defaultBlockState() : (isFree(serverLevel.getBlockState(pos.below()))? Blocks.AIR.defaultBlockState():state)), 3);
    }
}
