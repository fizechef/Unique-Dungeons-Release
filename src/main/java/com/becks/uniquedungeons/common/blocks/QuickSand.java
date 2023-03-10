package com.becks.uniquedungeons.common.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class QuickSand extends Block {

	public QuickSand(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	public void entityInside(BlockState state, Level p_196262_2_, BlockPos pos, Entity entity) {
		if (entity.getY() + entity.getEyeHeight(entity.getPose()) < pos.getY() + 1 && !(entity instanceof ItemEntity)) {
			entity.hurt(DamageSource.DROWN, 1);
		}
	    entity.makeStuckInBlock(state, new Vec3(0.25D, (double)0.05F, 0.25D));
	}
	
	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		entity.makeStuckInBlock(world.getBlockState(pos), new Vec3(0.25D, (double)0.05F, 0.25D));
		
	}	
}
