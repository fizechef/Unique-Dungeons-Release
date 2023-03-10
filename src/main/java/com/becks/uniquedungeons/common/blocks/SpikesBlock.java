package com.becks.uniquedungeons.common.blocks;

import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpikesBlock extends AbstractWaterloggableBlock {

	private final VoxelShape SHAPE  = Block.box(1, 0, 1, 15, 12, 15);

	private float damageModifier;
	public SpikesBlock(Properties p_49795_, float damageModifier) {
		super(p_49795_);
		this.damageModifier = damageModifier;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(world, pos, state, entity);
		entity.hurt(DamageSource.CRAMMING, 10f);
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		if (!entity.getType().equals(EntityType.ITEM) && !level.isClientSide) {
			float movementTotal = (float) entity.getDeltaMovement().length();
			//System.out.println(movementTotal);
			if (movementTotal >= 0.07f) {
				//System.out.println("damage " + entity);
				entity.hurt(DamageSource.CACTUS, (movementTotal*damageModifier + 1));
			}
		}
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
