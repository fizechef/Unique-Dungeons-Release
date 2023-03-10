package com.becks.uniquedungeons.mixin;

import com.becks.uniquedungeons.common.stackCooldowns.StackCooldownMap;
import com.becks.uniquedungeons.common.stackCooldowns.StackCooldowns;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;tick()V"))
    private void injected(CallbackInfo ci) {
        Player thisPlayer = (Player)(Object)this;
        StackCooldowns cooldowns = StackCooldownMap.getCooldowns((Player)(Object)this);
        if (cooldowns != null){
            cooldowns.tick();
        }
        if (thisPlayer instanceof ServerPlayer){
            cooldowns = StackCooldownMap.getCooldowns((Player)(Object)this);
            if (cooldowns != null){
                cooldowns.tick();
            }
        }
    }
}
