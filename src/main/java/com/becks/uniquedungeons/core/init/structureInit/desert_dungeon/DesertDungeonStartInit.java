package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonStartRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonStart;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;

/**
 * PLace to register all DesertDungeon starts,
 * call DesertDungeonStartRegistry.register to register new start type
 */
public class DesertDungeonStartInit {

    private static final DesertDungeonStart ROUND_TEMPLE = DesertDungeonStartRegistry.register(
            "desert_dungeon_start_roundtemple",
            new PieceConnection[]{
                    new PieceConnection(Direction.SOUTH, 3, 13)
            },
            new Tuple[0],
            -15
    );

    public static void init(){
    }
}
