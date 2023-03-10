package com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor;

import net.minecraft.world.damagesource.DamageSource;

public class DamageFaktorTupel {
    private final DamageSource source;
    private final float factor;

    DamageFaktorTupel(DamageSource source, float factor){
        this.source = source;

        this.factor = factor;
    }

    public float getFactor(){
        return factor;
    }

    public DamageSource getSource(){
        return source;
    }
}
