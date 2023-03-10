package com.becks.uniquedungeons.common.blocks.flamethrower;

import java.util.stream.Stream;

import com.becks.uniquedungeons.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class FlameThrower extends BaseEntityBlock {

	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public FlameThrower(Properties p_i48440_1_) {
		super(p_i48440_1_);	
		this.defaultBlockState().setValue(FACING, Direction.NORTH);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)));
	}

	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean bool) {
		if (!level.isClientSide) {
			boolean powered = state.getValue(POWERED);
			if (powered != level.hasNeighborSignal(pos)) {
				level.setBlock(pos, state.cycle(POWERED), 2);
			}

		}
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
	}

	 protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		 super.createBlockStateDefinition(builder);
	      builder.add(POWERED);
	      builder.add(FACING);
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

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FlameThrowerBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityInit.FLAME_THROWER.get(), FlameThrowerBlockEntity::tick);
	}
}
