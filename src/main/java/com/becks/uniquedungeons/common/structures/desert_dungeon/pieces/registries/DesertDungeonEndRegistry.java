package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonEnd;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * holds instances of DesertDungeonEnd to call later when generating dungeon
 */
public class DesertDungeonEndRegistry {
    private static final List<DesertDungeonEnd> ends = new ArrayList<>();

    private static DesertDungeonEndRegistry INSTANCE;

    private DesertDungeonEndRegistry() {
    }

    public static DesertDungeonEndRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonEndRegistry();
        }
        return INSTANCE;
    }

    /**
     * registers a new DesertDungeonEnd and returns it
     * @param name file name
     * @param entrance PieceConnection representing the single entrance of the end piece
     * @param deco Array of nested Tuples representing deco elements of the end, each elements represents a group of similar deco pieces out of wich a random one will be chosen at generation.
     *           Vec3i relative position of deco piece to 1,1,1 corner of parent piece cube.
     *           Rotation relative rotation to parent piece.
     * @return the end piece
     */
    public static DesertDungeonEnd register(String name, PieceConnection entrance, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco){
        DesertDungeonEnd corridor = new DesertDungeonEnd(entrance, name, deco);
        ends.add(corridor);
        return corridor;
    }

    public static DesertDungeonEnd getRandom(){
        Random rand = new Random();
        return ends.get(rand.nextInt(ends.size()));
    }
}
