package com.becks.uniquedungeons.common.blocks;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class AntiquePebblePPlate extends PressurePlateBlock {

	protected static final VoxelShape PRESSED_AABB = Stream.of(
			Block.box(6, 0, 5, 9, 2, 9),
			Block.box(9, 0, 6, 12, 1, 9),
			Block.box(5, 0, 6, 6, 1, 11),
			Block.box(6, 0, 9, 11, 2, 12),
			Block.box(7, 1, 7, 10, 3, 11)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	protected static final VoxelShape AABB = Stream.of(
			Block.box(6, 0, 5, 9, 2, 9),
			Block.box(9, 0, 6, 12, 1, 9),
			Block.box(5, 0, 6, 6, 1, 11),
			Block.box(6, 0, 9, 11, 2, 12),
			Block.box(7, 1, 7, 10, 4, 11)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	protected static final net.minecraft.world.phys.AABB TOUCH_AABB = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	private final PressurePlateBlock.Sensitivity sensitivity = Sensitivity.EVERYTHING;
	private boolean pressed = false;
	
	
	public AntiquePebblePPlate(Sensitivity p_i48348_1_, Properties p_i48348_2_) {
		super(p_i48348_1_, p_i48348_2_);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
	      return this.getSignalForState(p_220053_1_) > 0 ? PRESSED_AABB : AABB;
	}

	@Override
	protected void playOnSound(LevelAccessor p_185507_1_, BlockPos p_185507_2_) {
		p_185507_1_.playSound((Player)null, p_185507_2_, SoundEvents.BASALT_BREAK, SoundSource.BLOCKS, 0.3F, 0.8F);
	}

	@Override
	protected void playOffSound(LevelAccessor p_185508_1_, BlockPos p_185508_2_) {
		p_185508_1_.playSound((Player)null, p_185508_2_, SoundEvents.BASALT_BREAK, SoundSource.BLOCKS, 0.3F, 0.8F);
	}
	
	@Override
	protected int getSignalStrength(Level world, BlockPos pos) {
	      AABB axisalignedbb = TOUCH_AABB.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
	      List<? extends Entity> list;
	      switch(this.sensitivity) {
	      case EVERYTHING:
	         list = world.getEntities((Entity)null, axisalignedbb);
	         break;
	      case MOBS:
	         list = world.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
	         break;
	      default:
	         return 0;
	      }

	      if (!list.isEmpty()) {
	         for(Entity entity : list) {
	            if (!entity.isIgnoringBlockTriggers()) {
	               return 15;
	            }
	         }
	      }

	      return 0;
	   }
	
	
	
	@Override
	protected int getPressedTime() {
	      return 20;
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
