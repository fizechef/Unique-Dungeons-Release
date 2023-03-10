package com.becks.uniquedungeons.mixin;

import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    
    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemainingFireTicks(I)V"))
    protected void inject$onHitEntity(EntityHitResult pResult, CallbackInfo ci) {
        AbstractArrow thisArrow = (AbstractArrow)(Object)this;
        if (pResult.getEntity() instanceof LivingEntity){
            if (((LivingEntity)pResult.getEntity()).isBlocking() && ((LivingEntity)pResult.getEntity()).getUseItem().is(ArtifactInit.REFLECTING_SHIELD.get())){
                thisArrow.setDeltaMovement(((LivingEntity)(pResult.getEntity())).getLookAngle());
                thisArrow.setDeltaMovement(thisArrow.getDeltaMovement().scale(-10D));
            }
        }
    }
}
