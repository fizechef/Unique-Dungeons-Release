package com.becks.uniquedungeons.mixin;

import com.becks.uniquedungeons.common.items.artifacts.AmethystShield;
import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "blockUsingShield", at = @At(value = "HEAD"), cancellable = true)
    protected void inject$blockUsingShield(LivingEntity attacker, CallbackInfo ci) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        //System.out.println("Hello from LivingEntityMixin");
        if (thisEntity.getUseItem().is(ArtifactInit.REFLECTING_SHIELD.get())){
            //System.out.println("Blocked with refelcting shield");
            attacker.knockback(2.5D, -(attacker.getX() - thisEntity.getX()), -(attacker.getZ() - thisEntity.getZ()));
            ci.cancel();
        }
        else if (thisEntity.getUseItem().is(ArtifactInit.AMETHYST_SHIELD.get())){
            ((AmethystShield)thisEntity.getUseItem().getItem()).addDamegeStacks(thisEntity.getUseItem(), 1);
        }
        //System.out.println("Buisness as ususal");
    }
}
