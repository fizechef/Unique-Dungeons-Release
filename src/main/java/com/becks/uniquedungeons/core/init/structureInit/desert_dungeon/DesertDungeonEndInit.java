package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonEndRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonEnd;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;

/**
 * Place to register all DesertDungeon ends,
 * call DesertDungeonEndRegistry.register to register new end type
 */
public class DesertDungeonEndInit {

    private static final DesertDungeonEnd OPEN_ROOM_END = DesertDungeonEndRegistry.register(
            "desert_dungeon_end_simple",
            new PieceConnection(Direction.NORTH, 8, 15),
            new Tuple[0]
    );

    private static final DesertDungeonEnd LOCKED_TRAPPED_END = DesertDungeonEndRegistry.register(
            "desert_dungeon_end_locked_trapped",
            new PieceConnection(Direction.WEST, 11, 11),
            new Tuple[0]
    );

    public static void init(){
    }
}
