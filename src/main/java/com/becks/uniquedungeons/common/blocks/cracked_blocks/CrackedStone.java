package com.becks.uniquedungeons.common.blocks.cracked_blocks;

import java.util.Random;

import com.becks.uniquedungeons.core.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedStone extends Block {
	
	private Random randomGenerator = new Random();

	private SoundEvent crackSound;
	
	public CrackedStone(Properties properties, SoundEvent crackSound) {
		super(properties);
		this.crackSound = crackSound;
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float f) {
		super.fallOn(world, state, pos, entity, f);
		if (!(entity instanceof Bat)) {
			this.breaking(world, pos, 1, new CrackingPattern(), null);
		}
	}
	
	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(world, pos, state, entity);
		if (!entity.isCrouching() && !(entity instanceof Bat)) {
			this.breaking(world, pos, 0.05, new CrackingPattern(), null);
		}
	}
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block pos2,
								BlockPos pos3, boolean b) {
		super.neighborChanged(state, level, pos, pos2, pos3, b);
		Boolean breaking = true;
		for (Direction d : Direction.values()){
			breaking = breaking && level.getBlockState(pos.relative(d)).isAir();
		}
		if (breaking){
			this.breaking(level, pos, 1.0, new CrackingPattern(), null);
		}
	}

	private void breaking(Level level, BlockPos pos, double chance, CrackingPattern pattern, Direction crackDirection) {
		this.breaking(level, pos, chance, pattern, crackDirection, randomGenerator.nextDouble());
	}
	private void breaking(Level level, BlockPos pos, double chance, CrackingPattern pattern, Direction crackDirection, double randomDouble) {
		if (level.isClientSide){
			return;
		}
		if (!(randomDouble < chance)){
			return;
		}
		if (!level.getBlockState(pos).getBlock().equals(BlockInit.CRACKED_DARK_ANTIQUE_STONE.get())){
			return;
		}
		BlockPos posLower = new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
		if (!level.getBlockState(posLower).isAir()) {
			return;
		}
		level.setBlockAndUpdate(pos, BlockInit.DARK_ANTIQUE_GRAVEL.get().defaultBlockState());
		level.playSound(null, pos, crackSound, SoundSource.BLOCKS, 10, 1);
		for (Direction d : Direction.values()){
			int test = pattern.test(d);
			if (test > 0){
				if (test == 1){
					this.breaking(level, pos.relative(d), crackChance(crackDirection, d), pattern.copy().add(d), d);
				}
				else if (test == 2){
					this.breaking(level, pos.relative(d), crackChance(crackDirection, d), pattern, d);
				}

			}
		}
	}

	private double crackChance(Direction d1, Direction d2){
		return (d1==null||d2==null)?0.9:(d1.equals(d2)?0.5:0.25);
	}
}
