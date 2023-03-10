package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;

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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class AntiquePebble extends AbstractWaterloggableBlock {
	
	protected static final VoxelShape SHAPE = Stream.of(
			Block.box(6, 0, 5, 9, 2, 9),
			Block.box(9, 0, 6, 12, 1, 9),
			Block.box(5, 0, 6, 6, 1, 11),
			Block.box(6, 0, 9, 11, 2, 12),
			Block.box(7, 1, 7, 10, 3, 11)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();

	public AntiquePebble(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		return (direction == Direction.DOWN && !this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return canSupportCenter(pLevel, pPos.below(), Direction.UP);
	}

}
