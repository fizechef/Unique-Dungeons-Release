package com.becks.uniquedungeons.common.entities;

import com.becks.uniquedungeons.core.init.BlockInit;
import com.becks.uniquedungeons.core.init.itemInit.GeneralItemInit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ClimbingHookProjectile extends Arrow {

	public ClimbingHookProjectile(Level p_i46758_1_, LivingEntity p_i46758_2_) {
		super(p_i46758_1_, p_i46758_2_);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(GeneralItemInit.CLIMBING_HOOK_ITEM.get());
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.inGround) {
			this.placeHook();
		}
	}
	
	@Override
	protected void onHitEntity(EntityHitResult p_213868_1_) {
		this.placeHook();
	}
	
	private void placeHook() {
		if (this.level.getBlockState(this.blockPosition()).isAir()) {
			this.level.setBlockAndUpdate(this.blockPosition(), BlockInit.CLIMBING_HOOK.get().defaultBlockState());
			this.kill();
		}
		else {
			this.level.addFreshEntity(new ItemEntity(level, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ(), GeneralItemInit.CLIMBING_HOOK_ITEM.get().getDefaultInstance()));
			this.kill();
		}
	}
}
