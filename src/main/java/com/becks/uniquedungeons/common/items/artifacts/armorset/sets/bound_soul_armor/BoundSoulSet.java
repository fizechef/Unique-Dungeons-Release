package com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor;

import com.becks.uniquedungeons.common.items.artifacts.armorset.ArmorSet;

public class BoundSoulSet implements ArmorSet {
    private static BoundSoulSet INSTANCE = null;

    public static BoundSoulSet getInstance(){
        if (INSTANCE == null){
            INSTANCE = new BoundSoulSet();
        }
        return INSTANCE;
    }

    private BoundSoulSet(){

    }
}
