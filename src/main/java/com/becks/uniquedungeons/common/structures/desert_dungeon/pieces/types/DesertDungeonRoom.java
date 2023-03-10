package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DesertDungeonRoom extends ADesertDungeonPiece {
    public DesertDungeonRoom(PieceConnection entrance, PieceConnection[] exits, String name, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco) {
        super(entrance, exits, name, deco);
    }
}
