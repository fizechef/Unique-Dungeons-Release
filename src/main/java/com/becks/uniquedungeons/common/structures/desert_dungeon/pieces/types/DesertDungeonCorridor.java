package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

public class DesertDungeonCorridor extends ADesertDungeonMidPiece {

    public DesertDungeonCorridor(String name, PieceConnection entrance, PieceConnection exit, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco) {
        super(entrance, new PieceConnection[]{exit}, name, deco);
    }
}
