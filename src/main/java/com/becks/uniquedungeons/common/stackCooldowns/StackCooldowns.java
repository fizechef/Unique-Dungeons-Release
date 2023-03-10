package com.becks.uniquedungeons.common.stackCooldowns;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.network.proxy.ServerProxy;
import com.google.common.collect.Maps;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.Map;

public class StackCooldowns {
    private final Map<StackWrapper, StackCooldowns.CooldownInstance> cooldowns = Maps.newHashMap();
    private int tickCount;

    public boolean isOnCooldown(ItemStack stack) {
        return this.getCooldownPercent(stack, 0.0F) > 0.0F;
    }

    public float getCooldownPercent(ItemStack stack, float pPartialTicks) {
        StackCooldowns.CooldownInstance itemcooldowns$cooldowninstance = this.search(stack);
        if (itemcooldowns$cooldowninstance != null) {
            float f = (float)(itemcooldowns$cooldowninstance.endTime - itemcooldowns$cooldowninstance.startTime);
            float f1 = (float)itemcooldowns$cooldowninstance.endTime - ((float)this.tickCount + pPartialTicks);
            return Mth.clamp(f1 / f, 0.0F, 1.0F);
        } else {
            return 0.0F;
        }
    }

    public void tick() {
        //System.out.println(UniqueDungeons.proxy.getMinecraft().isSameThread());
        //System.out.println("ticking");
       //System.out.println(cooldowns.size());
        ++this.tickCount;
        if (UniqueDungeons.proxy instanceof ServerProxy || UniqueDungeons.proxy.getMinecraft().isSameThread()){
            if (!this.cooldowns.isEmpty()) {
                Iterator<Map.Entry<StackWrapper, StackCooldowns.CooldownInstance>> iterator = this.cooldowns.entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<StackWrapper, StackCooldowns.CooldownInstance> entry = iterator.next();
                    if ((entry.getValue()).endTime <= this.tickCount) {
                       //System.out.println("Removing");
                        iterator.remove();
                        this.onCooldownEnded(entry.getKey().resolve(), false);
                    }
                }
            }
        }
    }

    public void addCooldown(ItemStack stack, int pTicks, boolean syncadd) {
       //System.out.println("Search and remove: " + searchAndRemove(stack));
       //System.out.println("Adding " + stack);
        searchAndRemove(stack);
        this.cooldowns.put(new StackWrapper(stack), new StackCooldowns.CooldownInstance(this.tickCount, this.tickCount + pTicks));
        this.onCooldownStarted(stack, pTicks, syncadd);
    }

    public void addCooldown(Player p, int pTicks, EquipmentSlot slot, boolean syncadd) {
        this.addCooldown(p.getItemBySlot(slot), pTicks, syncadd);
    }

    public void removeCooldown(ItemStack stack, boolean syncremove) {
        this.searchAndRemove(stack);
        this.onCooldownEnded(stack, syncremove);
    }

    protected void onCooldownStarted(ItemStack stack, int pTicks, boolean syncadd) {
    }

    protected void onCooldownEnded(ItemStack stack, boolean syncremove) {
    }

    static class CooldownInstance {
        final int startTime;
        final int endTime;

        CooldownInstance(int pStartTime, int pEndTime) {
            this.startTime = pStartTime;
            this.endTime = pEndTime;
        }
    }

    private boolean searchAndRemove(ItemStack stack){
        if (!this.cooldowns.isEmpty()) {
            Iterator<Map.Entry<StackWrapper, StackCooldowns.CooldownInstance>> iterator = this.cooldowns.entrySet().iterator();
            StackWrapper wrappedStack = new StackWrapper(stack);
            while(iterator.hasNext()) {
                Map.Entry<StackWrapper, StackCooldowns.CooldownInstance> entry = iterator.next();
                if (entry.getKey().equals(wrappedStack)) {
                   //System.out.println("Removing");
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    private StackCooldowns.CooldownInstance search(ItemStack stack){
        if (!this.cooldowns.isEmpty()) {
            StackWrapper wrappedStack = new StackWrapper(stack);
            for (Map.Entry<StackWrapper, CooldownInstance> entry : this.cooldowns.entrySet()) {
                if (entry.getKey().equals(wrappedStack)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
