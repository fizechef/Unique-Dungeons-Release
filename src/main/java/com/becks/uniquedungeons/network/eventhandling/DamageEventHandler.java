package com.becks.uniquedungeons.network.eventhandling;

import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.DamageFaktorTupel;
import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.AbstractBoundSoulArmor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;

public class DamageEventHandler {
    @SubscribeEvent
    public static void damage(LivingHurtEvent event){
        Entity damageTarget = event.getEntity();
        //System.out.println("damage");
        if (damageTarget instanceof Player){
            //System.out.println("player");
            //ItemStack feet = ((Player)damageTarget).getInventory().getItem(EquipmentSlot.FEET.getIndex());
            Iterable<ItemStack> armorSlots = ((Player)damageTarget).getArmorSlots();
            for (ItemStack stack : armorSlots){
                if (stack.getItem() instanceof AbstractBoundSoulArmor){
                    //System.out.println("wearing armor");
                    Collection<DamageFaktorTupel> blocking = ((AbstractBoundSoulArmor)stack.getItem()).blocking((Player) damageTarget);
                    for (DamageFaktorTupel t : blocking){
                        if (t.getSource() == null || t.getSource().equals(event.getSource())){
                            if (stack.getTag().contains(AbstractBoundSoulArmor.UUID_KEY)){
                                //System.out.println("with UUID");
                                LivingEntity newTarget = (LivingEntity)((ServerLevel)damageTarget.level).getEntity(stack.getTag().getUUID(AbstractBoundSoulArmor.UUID_KEY));
                                if (newTarget != null){
                                    boolean damageLoopFlag = false;
                                    if (newTarget instanceof Player) {
                                        Iterable<ItemStack> targetArmorSlots = ((Player) newTarget).getArmorSlots();
                                        for (ItemStack compareStack : targetArmorSlots) {
                                            if (compareStack.getItem().equals(stack.getItem())){
                                                damageLoopFlag = true;
                                            }
                                        }
                                    }
                                    if (!damageLoopFlag){
                                        if (event.getSource().equals(DamageSource.FALL)){
                                            newTarget.causeFallDamage(((event.getAmount()/t.getFactor())*2)+3, 1, DamageSource.FALL);
                                        }
                                        else {
                                            newTarget.hurt(event.getSource(), event.getAmount() * t.getFactor());
                                        }
                                        event.setAmount(event.getAmount() * (1-t.getFactor()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
