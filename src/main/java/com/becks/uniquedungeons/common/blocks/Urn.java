package com.becks.uniquedungeons.common.blocks;

import java.util.Random;
import java.util.stream.Stream;

import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.level.block.LiquidBlock.LEVEL;
import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class Urn extends AbstractWaterloggableBlock {
	private final VoxelShape SHAPE  = Stream.of(
			Block.box(4, 14, 4, 12, 16, 12),
			Block.box(5, 11, 5, 11, 14, 11),
			Block.box(3, 1, 3, 13, 11, 13),
			Block.box(4, 0, 4, 12, 1, 12)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();

	public static final BooleanProperty CONTAINS_WATER = BooleanProperty.create("contains_water");
	public static final BooleanProperty CONTAINS_SPIDER = BooleanProperty.create("contains_spider");
	
	public Urn(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(CONTAINS_WATER, Boolean.FALSE).setValue(CONTAINS_SPIDER, Boolean.FALSE));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
							   CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2,
						 boolean bool) {
		super.onRemove(state, level, pos, state2, bool);
		if(state.getValue(CONTAINS_SPIDER)){
			CaveSpider spider = new CaveSpider(EntityType.CAVE_SPIDER, level);
			spider.setPos(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5);
			level.addFreshEntity(spider);
		}
		if(state.getValue(CONTAINS_WATER)){
			level.setBlockAndUpdate(pos, Fluids.WATER.defaultFluidState().createLegacyBlock().setValue(LEVEL, 7));
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CONTAINS_WATER);
		builder.add(CONTAINS_SPIDER);
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
