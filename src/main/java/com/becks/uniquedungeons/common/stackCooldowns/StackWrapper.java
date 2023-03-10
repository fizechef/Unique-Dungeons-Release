package com.becks.uniquedungeons.common.stackCooldowns;

import net.minecraft.world.item.ItemStack;

public class StackWrapper {
    private final ItemStack stack;

    public StackWrapper(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack resolve(){
        return this.stack;
    }

    @Override
    public boolean equals(Object o){
       //System.out.println("Stack Warpper Equals Check");
        if (o instanceof StackWrapper stackWrapper){
            return ((StackCooldownItem)this.stack.getItem()).getCooldownID(this.stack).equals(((StackCooldownItem)stackWrapper.stack.getItem()).getCooldownID(stackWrapper.stack));
        }
        return false;
    }
}
