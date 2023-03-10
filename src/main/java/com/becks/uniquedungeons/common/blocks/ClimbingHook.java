package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;

import com.becks.uniquedungeons.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;


public class ClimbingHook extends Block {
	
	protected static final VoxelShape SHAPE = Stream.of(
			Block.box(7, 0, 7, 9, 14, 9)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();

	public ClimbingHook(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	
	@Override
	public void onPlace(BlockState state1, Level world, BlockPos pos, BlockState p_220082_4_,
						boolean p_220082_5_) {
		if (world.getBlockState(pos.below()).isAir()) {
			world.setBlockAndUpdate(pos.below(), BlockInit.CLIMBING_ROPE.get().defaultBlockState());
		}
		super.onPlace(state1, world, pos, p_220082_4_, p_220082_5_);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		return (!this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return canSupportCenter(pLevel, pPos.above(), Direction.DOWN)
				|| pLevel.getBlockState(pPos.relative(Direction.NORTH)).getMaterial().isSolid()
				|| pLevel.getBlockState(pPos.relative(Direction.EAST)).getMaterial().isSolid()
				|| pLevel.getBlockState(pPos.relative(Direction.SOUTH)).getMaterial().isSolid()
				|| pLevel.getBlockState(pPos.relative(Direction.WEST)).getMaterial().isSolid();
	}
}
