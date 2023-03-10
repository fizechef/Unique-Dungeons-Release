package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonCorridorRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonRoomRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonCorridor;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonRoom;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

/**
 * Place to register all DesertDungeon rooms,
 * call DesertDungeonRoomRegistry.register to register new room type
 */
public class DesertDungeonRoomInit {

    private static final DesertDungeonRoom PILLARCAVE = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_pillarcave",
            new PieceConnection(Direction.WEST, 7, 12),
            new PieceConnection[]{
                    new PieceConnection(Direction.EAST,7, 14)
            },
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.PILLARCAVE_INNER, new Tuple(new Vec3i(2, 1, 3), Rotation.NONE))
            }
    );

    private static final DesertDungeonRoom CITY = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_city",
            new PieceConnection(Direction.NORTH, 9, 14),
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH,11, 19)
            },
            new Tuple[0]
    );

    private static final DesertDungeonRoom BRIDGE = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_bridge",
            new PieceConnection(Direction.NORTH, 16, 14),
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH,16, 12)
            },
            new Tuple[0]
    );

    private static final DesertDungeonRoom MAZE = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_maze",
            new PieceConnection(Direction.NORTH, 19, 6),
            new PieceConnection[]{
                    new PieceConnection(Direction.WEST,6, 20)
            },
            new Tuple[0]
    );
    private static final DesertDungeonRoom BIB = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_bib",
            new PieceConnection(Direction.NORTH, 15, 14),
            new PieceConnection[]{
                    new PieceConnection(Direction.WEST,7, 11)
            },
            new Tuple[0]
    );

    private static final DesertDungeonRoom RED_CANDLE = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_redcandle",
            new PieceConnection(Direction.NORTH, 20, 7),
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH,9, 12)
            },
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.REDCANDLE_REDSTONE, new Tuple(new Vec3i(0, 0, 0), Rotation.NONE))
            }
    );

    private static final DesertDungeonRoom DESERT_DUNGEON_CORRIDOR_OPENBRIDGE = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_openbridge",
            new PieceConnection(Direction.NORTH, 13, 13),
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH, 13, 13)
            },
            new Tuple[0]
    );

    private static final DesertDungeonRoom DESERT_DUNGEON_CORRIDOR_FIGHT = DesertDungeonRoomRegistry.register(
            "desert_dungeon_room_fight",
            new PieceConnection(Direction.NORTH, 12, 14),
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH, 11, 19)
            },
            new Tuple[0]
    );

    public static void init(){
    }
}
