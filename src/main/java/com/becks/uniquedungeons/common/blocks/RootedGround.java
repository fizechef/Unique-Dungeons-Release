package com.becks.uniquedungeons.common.blocks;

import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RootedGround extends AbstractWaterloggableBlock {

    private final VoxelShape SHAPE  = Block.box(0, 0, 0, 16, 2.5, 16);


    public RootedGround(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
        return 1;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
        return 20000;
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
        return (direction == Direction.DOWN && !this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below());
    }
}
