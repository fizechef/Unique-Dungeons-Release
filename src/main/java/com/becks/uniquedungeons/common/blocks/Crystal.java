package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;

import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class Crystal extends AbstractWaterloggableBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	private final VoxelShape SHAPE_N  = Stream.of(
			Block.box(5, 0, 5, 11, 2, 11),
			Block.box(6, 2, 6, 10, 6, 10),
			Block.box(7, 6, 7, 9, 11, 9)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	
	public Crystal(Properties properties) {
		super(properties);
		this.defaultBlockState().setValue(FACING, Direction.NORTH);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
		
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE_N;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
	      return (direction == Direction.DOWN && !this.canSurvive(state1, level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return canSupportCenter(pLevel, pPos.below(), Direction.UP);
	}


	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 4;
	}
}
