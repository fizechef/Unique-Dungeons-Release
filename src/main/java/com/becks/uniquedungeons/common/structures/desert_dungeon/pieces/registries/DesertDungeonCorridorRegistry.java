package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonCorridor;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;
import com.becks.uniquedungeons.common.structures.desert_dungeon.DesertDungeonGenerationConfig;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.DoubleFunction;
/**
 * Holds instances of DesertDungeonCorridors to call when generating a dungeon
 */
public class DesertDungeonCorridorRegistry {
    private static final List<DesertDungeonCorridor> corridors = new ArrayList<>();

    private static DesertDungeonCorridorRegistry INSTANCE;

    private DesertDungeonCorridorRegistry() {
    }

    public static DesertDungeonCorridorRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonCorridorRegistry();
        }
        return INSTANCE;
    }

     /**
     * registers a new DesertDungeonCorridor and returns it
     * @param name filename of the corridor
     * @param entrance PieceConnection representing the single entrance of the corridor
     * @param exit PieceConnection representing the single exit of the corridor
     * @param deco Array of nested Tuples representing deco elements of the corridor, each elements represents a group of similar deco pieces out of wich a random one will be chosen at generation.
     *            Vec3i relative position of deco piece to 1,1,1 corner of parent piece cube.
     *            Rotation relative rotation to parent piece.
     * @return the corridor
     */
    public static DesertDungeonCorridor register(String name, PieceConnection entrance, PieceConnection exit, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco){
        DesertDungeonCorridor corridor = new DesertDungeonCorridor(name, entrance, exit, deco);
        corridors.add(corridor);
        return corridor;
    }

    public static DesertDungeonCorridor getRandom(){
        Random rand = new Random();
        return corridors.get(rand.nextInt(corridors.size()));
    }

    public static DesertDungeonCorridor getRandom(int bedrockDistance, int xBound, int zBound, Direction direc){
        HashMap<DesertDungeonCorridor, Double> chanceMapBound = new HashMap<>();
        Vec3 boundVec = new Vec3(xBound, 0 , zBound);
        for (DesertDungeonCorridor piece : corridors){
            Vec3 boundVecMulAdd = boundVec.multiply(new Vec3(piece.getAngle().rotate(direc).getNormal().getX(), 0, piece.getAngle().rotate(direc).getNormal().getZ()))/*)*/;
            double d = Math.max(-1 * ((boundVecMulAdd.x + boundVecMulAdd.z) - (DesertDungeonGenerationConfig.OUTER_BOUNDS)), 0);
            chanceMapBound.put(piece, d);
        }

        DoubleFunction<Double> f = (x) -> Math.pow((x + 25)/25,(bedrockDistance/32.d));
        HashMap<DesertDungeonCorridor, Double> chanceMapHight = new HashMap<>();
        for (DesertDungeonCorridor piece : corridors){
            chanceMapHight.put(piece, f.apply(piece.getDownwardsAngle()));
        }
        double scale = 0;
        HashMap<DesertDungeonCorridor, Double> chanceMap = new HashMap();
        for (DesertDungeonCorridor piece : corridors){
            chanceMap.put(piece, chanceMapHight.get(piece) * chanceMapBound.get(piece));
            scale = scale + chanceMap.get(piece);
        }
        for (DesertDungeonCorridor piece : corridors){
            chanceMap.replace(piece, chanceMap.get(piece)/scale);
        }

        Random random = new Random();
        double randDouble = random.nextDouble();
        double passedChance = 0;
        for (DesertDungeonCorridor piece : corridors){
            passedChance = passedChance + chanceMap.get(piece);
            if (passedChance > randDouble){
                return piece;
            }
        }
        return getRandom();
    }
}
