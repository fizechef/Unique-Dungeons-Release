package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.*;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.*;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

/**
 * Init split
 */
public class DesertDungeonInit {
    public static void init(){
        DesertDungeonDecoInit.init();
        DesertDungeonCorridorInit.init();
        DesertDungeonRoomInit.init();
        DesertDungeonJunctionInit.init();
        DesertDungeonEndInit.init();
        DesertDungeonStartInit.init();
    }
}
