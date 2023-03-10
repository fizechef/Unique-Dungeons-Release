package com.becks.uniquedungeons.common.blocks;


import java.util.List;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class FullBlockPPlate extends BasePressurePlateBlock {

	private final SoundEvent activationSound;
	private final SoundEvent deactivationSound;
	
	protected static final VoxelShape PRESSED_AABB = Stream.of(
			Block.box(0, 0, 0, 16, 15, 16)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	protected static final VoxelShape AABB = Stream.of(
			Block.box(0, 0, 0, 16, 16, 16)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();
	protected static final AABB TOUCH_AABB = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.2D, 1.0D);
	private final PressurePlateBlock.Sensitivity sensitivity = PressurePlateBlock.Sensitivity.EVERYTHING;

	public static final BooleanProperty PRESSED = BlockStateProperties.POWERED;
	public FullBlockPPlate(Properties p_49290_, SoundEvent activationSound, SoundEvent deactivationSound) {
		super(p_49290_);
		this.activationSound = activationSound;
		this.deactivationSound = deactivationSound;
		this.registerDefaultState(this.stateDefinition.any().setValue(PRESSED, Boolean.valueOf(false)));
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
	      return this.getSignalForState(p_220053_1_) > 0 ? PRESSED_AABB : AABB;
	}

	@Override
	protected void playOnSound(LevelAccessor p_185507_1_, BlockPos p_185507_2_) {
		p_185507_1_.playSound((Player)null, p_185507_2_, activationSound, SoundSource.BLOCKS, 0.3F, 0.8F);
	}

	@Override
	protected void playOffSound(LevelAccessor p_185508_1_, BlockPos p_185508_2_) {
		p_185508_1_.playSound((Player)null, p_185508_2_, deactivationSound, SoundSource.BLOCKS, 0.3F, 0.8F);
	}

	@Override
	protected int getSignalStrength(Level level, BlockPos pos) {
		AABB aabb = TOUCH_AABB.move(pos);
		List<? extends Entity> list;
		list = level.getEntities((Entity)null, aabb);
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
	protected int getSignalForState(BlockState p_49354_) {
		return p_49354_.getValue(PRESSED) ? 15 : 0;
	}

	@Override
	protected BlockState setSignalForState(BlockState p_55259_, int p_55260_) {
		return p_55259_.setValue(PRESSED, Boolean.valueOf(p_55260_ > 0));
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (!level.isClientSide) {
			int i = this.getSignalForState(state);
			if (i == 0) {
				this.checkPressed(entity, level, pos, state, i);
			}

		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55262_) {
		p_55262_.add(PRESSED);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return true;
	}
}
