package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonJunction;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonRoom;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * holds instances of DesertDungeonRoom to call when generating dungeon
 */
public class DesertDungeonRoomRegistry {
    private static final List<DesertDungeonRoom> rooms = new ArrayList<>();

    private static DesertDungeonRoomRegistry INSTANCE;

    private DesertDungeonRoomRegistry() {
    }

    public static DesertDungeonRoomRegistry get(){
        if (INSTANCE == null){
            INSTANCE = new DesertDungeonRoomRegistry();
        }
        return INSTANCE;
    }

    /**
     * registers a new DesertDungeonRoom type and return it
     * @param name file name
     * @param entrance PieceConnection representing the single entrance of the room
     * @param exits Array of PieceConnection representing all exits of the room
     * @param deco Array of nested Tuples representing deco elements of the room, each element represents a group of similar deco pieces out of wich a random one will be chosen at generation.
     *                Vec3i relative position of deco piece to 1,1,1 corner of parent piece cube.
     *                Rotation relative rotation to parent piece.
     * @return the room
     */
    public static DesertDungeonRoom register(String name, PieceConnection entrance, PieceConnection[] exits, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco){
        DesertDungeonRoom room = new DesertDungeonRoom(entrance, exits, name, deco);
        rooms.add(room);
        return room;
    }

    public static DesertDungeonRoom getRandom() {
        Random rand = new Random();
        return rooms.get(rand.nextInt(rooms.size()));
    }

    public static DesertDungeonRoom getRandom(List<DesertDungeonRoom> used){
        List<DesertDungeonRoom> useable = new ArrayList<>();
        for (DesertDungeonRoom r : rooms){
            if (!used.contains(r)){
                useable.add(r);
            }
        }
        if (useable.size() < 1){
            return getRandom();
        }
        Random rand = new Random();
        return useable.get(rand.nextInt(useable.size()));
    }
}
