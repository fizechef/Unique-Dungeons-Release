package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DesertDungeonStart extends ADesertDungeonPiece {

    private final int generationYOffset;
    public DesertDungeonStart(PieceConnection[] exits, String name, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco, int generationYOffset) {
        super(null, exits, name, deco);
        this.generationYOffset = generationYOffset;
    }
    public int getGenerationYOffset(){
        return generationYOffset;
    }
}
