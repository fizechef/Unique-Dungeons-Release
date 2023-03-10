package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;

import com.becks.uniquedungeons.core.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class ClimbingRope extends Block {
	
	protected static final VoxelShape SHAPE = Stream.of(
			Block.box(7, 0, 7, 9, 16, 9)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	
	public ClimbingRope(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}
	
	@Override
	public void onPlace(BlockState state1, Level level, BlockPos pos, BlockState p_220082_4_,
						boolean p_220082_5_) {
		level.scheduleTick(pos, this, 2);
		super.onPlace(state1, level, pos, p_220082_4_, p_220082_5_);
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		return (!this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return pLevel.getBlockState(pPos.above()).getBlock().equals(BlockInit.CLIMBING_ROPE.get()) || pLevel.getBlockState(pPos.above()).getBlock().equals(BlockInit.CLIMBING_HOOK.get());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
		if (serverLevel.getBlockState(pos.below()).isAir()) {
			serverLevel.setBlockAndUpdate(pos.below(), BlockInit.CLIMBING_ROPE.get().defaultBlockState());
		}
		super.tick(state, serverLevel, pos, random);
	}
}
