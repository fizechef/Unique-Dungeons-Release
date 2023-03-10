package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import com.becks.uniquedungeons.util.DirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class ADesertDungeonPiece{
    private final PieceConnection entrance;
    private final PieceConnection[] exits;
    private final String name;
    private final Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco;

    protected ADesertDungeonPiece(PieceConnection entrance, PieceConnection[] exits, String name, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco) {
        this.entrance = entrance;
        this.exits = exits;
        this.name = name;
        this.deco = deco;
    }

    public PieceConnection getEntrance(){
        return entrance;
    }

    public String getName(){
        return name;
    }

    public DesertDungeonPiece get(StructureTemplateManager pStructureTemplateManager, BlockPos pStartPos, Rotation pRotation) {
        return new DesertDungeonPiece(pStructureTemplateManager, this.name, pStartPos, pRotation, true);
    }

    public PieceConnection[] getExits() {
        return exits;
    }

    public Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] getDeco() {
        return deco;
    }
}
