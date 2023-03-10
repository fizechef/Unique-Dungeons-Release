package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.DesertDungeonGenerationConfig;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonCorridor;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonJunction;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleFunction;

/**
 * holds instances of DesertDungeonJunction to call later when generating dungeon
 */
public class DesertDungeonJunctionRegistry {
    private static final List<DesertDungeonJunction> junctions = new ArrayList<>();

    private static DesertDungeonJunctionRegistry INSTANCE;

    private DesertDungeonJunctionRegistry() {
    }

    public static DesertDungeonJunctionRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonJunctionRegistry();
        }
        return INSTANCE;
    }

    /**
     *
     * @param name file name
     * @param entrance PieceConnection representing the single entrance of the junction
     * @param exits Array of PieceConnections representing all exits of the junction
     * @param deco Array of nested Tuples representing deco elements of the end, each elements represents a group of similar deco pieces out of wich a random one will be chosen at generation.
     *                 Vec3i relative position of deco piece to 1,1,1 corner of parent piece cube.
     *                 Rotation relative rotation to parent piece.
     * @return the junction
     */
    public static DesertDungeonJunction register(String name, PieceConnection entrance, PieceConnection[] exits, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco){
        DesertDungeonJunction junction = new DesertDungeonJunction(entrance, exits, name, deco);
        junctions.add(junction);
        return junction;
    }

    public static DesertDungeonJunction getRandom() {
        Random rand = new Random();
        return junctions.get(rand.nextInt(junctions.size()));
    }
}
