package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;

import java.util.ArrayList;
import java.util.List;
/**
 * Holds instances of DesertDungeonDeco to call when generating a dungeon
 */
public class DesertDungeonDecoRegistry {
    private static final List<DesertDungeonDeco> decos = new ArrayList<>();

    private static DesertDungeonDecoRegistry INSTANCE;

    private DesertDungeonDecoRegistry() {
    }

    public static DesertDungeonDecoRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonDecoRegistry();
        }
        return INSTANCE;
    }

    /**
     * registers a new DesertDungeonDeco and returns it
     * @param name name of the deco group, filename prefix and folder name
     * @param variantNames Array of filename suffixes for the deco group
     * @return the deco
     */
    public static DesertDungeonDeco register(String name, String[] variantNames){
        DesertDungeonDeco deco = new DesertDungeonDeco(name, variantNames);
        decos.add(deco);
        return deco;
    }
}
