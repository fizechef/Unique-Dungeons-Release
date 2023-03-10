package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;

import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class EmptyUrn extends AbstractWaterloggableBlock {
	private final VoxelShape SHAPE  = Stream.of(
			Block.box(4, 14, 4, 12, 16, 12),
			Block.box(5, 11, 5, 11, 14, 11),
			Block.box(3, 1, 3, 13, 11, 13),
			Block.box(4, 0, 4, 12, 1, 12)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	
	public EmptyUrn(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE;
	}
	
	/**
	 * Updatet das Modelk des Blocks je nach Blockstate
	 * Override und neu implementieren wenn mehr als nur waterloggble am modl ge�ndert werden k�nnen soll 
	 */
	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		return (direction == Direction.DOWN && !this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return canSupportCenter(pLevel, pPos.below(), Direction.UP);
	}
}
