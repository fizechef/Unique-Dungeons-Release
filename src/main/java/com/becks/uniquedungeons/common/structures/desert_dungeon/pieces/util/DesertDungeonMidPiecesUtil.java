package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonCorridorRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonJunctionRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonRoomRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.ADesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonRoom;
import net.minecraft.core.Direction;

import java.util.List;
import java.util.Random;

public class DesertDungeonMidPiecesUtil {

    public static ADesertDungeonPiece getRandom(List<DesertDungeonRoom> usedRooms, ADesertDungeonPiece last, double corridors, double rooms, double junktions, int bedrockDist, int xBound, int zBound, Direction direc){
        Random rand = new Random();
        double randRoll = rand.nextDouble();
        if (randRoll <= corridors){
            return DesertDungeonCorridorRegistry.getRandom(bedrockDist, xBound, zBound, direc);
        }
        else if (randRoll <= corridors + rooms) {
            return DesertDungeonRoomRegistry.getRandom(usedRooms);
        }
        else if (randRoll <= corridors + rooms + junktions){
            return DesertDungeonJunctionRegistry.getRandom();
        }
        throw new IndexOutOfBoundsException("Provided values do not add up to 1, random out of bounds");
    }

    public static ADesertDungeonPiece getRandom(List<DesertDungeonRoom> usedRooms, ADesertDungeonPiece last, double corridors, double rooms, double junktions, boolean genRooms, boolean genJunktions, int bedrockDist, int xBound, int zBound, Direction direc){
        return getRandom(usedRooms, last, corridors + (genRooms?0:rooms) + (genJunktions?0:junktions), (genRooms?rooms:0), (genJunktions?junktions:0), bedrockDist, xBound, zBound, direc);
    }
}
