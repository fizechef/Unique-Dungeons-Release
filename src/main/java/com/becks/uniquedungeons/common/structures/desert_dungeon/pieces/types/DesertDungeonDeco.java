package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Random;

public class DesertDungeonDeco extends ADesertDungeonPiece {
    private final String name;
    private final String[] variantNames;
    private static final Random rand = new Random();

    public DesertDungeonDeco(String name, String[] variantNames) {
        super(null, null, name, null);
        this.name = name;
        this.variantNames = variantNames;
    }

    @Override
    public DesertDungeonPiece get(StructureTemplateManager pStructureTemplateManager, BlockPos pStartPos, Rotation pRotation) {
        return new DesertDungeonPiece(pStructureTemplateManager, "deco/" + this.name + "/" + this.name + "_" + this.getRandomVariant(), pStartPos, pRotation, true);
    }

    private String getRandomVariant(){
        return variantNames[rand.nextInt(variantNames.length)];
    }
}
