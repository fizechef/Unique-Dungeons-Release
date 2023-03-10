package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonStart;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * holds instances of DesertDungeonStart to call later when generating dungeon
 */
public class DesertDungeonStartRegistry {
    private static final List<DesertDungeonStart> starts = new ArrayList<>();

    private static DesertDungeonStartRegistry INSTANCE;

    private DesertDungeonStartRegistry() {
    }

    public static DesertDungeonStartRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonStartRegistry();
        }
        return INSTANCE;
    }

    /**
     * registers a new DesertDungeonStart type
     * @param name file name
     * @param exits Array of PieceConnection representing all exits of the start piece
     * @param deco Array of nested Tuples representing deco elements of the start, each element represents a group of similar deco pieces out of wich a random one will be chosen at generation.
     *                Vec3i relative position of deco piece to 1,1,1 corner of parent piece cube.
     *                Rotation relative rotation to parent piece.
     * @param generationYOffset how much the structure is lowered at generation to match surface level
     * @return the start piece
     */
    public static DesertDungeonStart register(String name, PieceConnection[] exits, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco, int generationYOffset){
        DesertDungeonStart start = new DesertDungeonStart(exits, name, deco, generationYOffset);
        starts.add(start);
        return start;
    }

    public static DesertDungeonStart getRandom(){
        Random rand = new Random();
        return starts.get(rand.nextInt(starts.size()));
    }
}
