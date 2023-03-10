package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import com.becks.uniquedungeons.util.DirectionHelper;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

public abstract class ADesertDungeonMidPiece extends ADesertDungeonPiece{

    private final int downwardsAngle;
    private final Rotation angle;

    protected ADesertDungeonMidPiece(PieceConnection entrance, PieceConnection[] exits, String name, Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>>[] deco) {
        super(entrance, exits, name, deco);
        downwardsAngle = generateDownwardsAngle(this);
        angle = generateAngle(this);
    }

    private static int generateDownwardsAngle(ADesertDungeonPiece piece){
        int enHeight = piece.getEntrance().getVertikalOffset();
        int exHeight = 0;
        for (PieceConnection c : piece.getExits()){
            if (c.getVertikalOffset() > exHeight){
                exHeight = c.getVertikalOffset();
            }
        }
        System.out.println(enHeight + " " + exHeight + " " + piece.getName());
        return enHeight - exHeight;
    }

    private static Rotation generateAngle(ADesertDungeonPiece piece){
        System.out.println(piece.getName());
        if (piece.getExits().length > 1){
            return null;
        }
        return DirectionHelper.getRotationNoOpp(piece.getEntrance().getDirection().getOpposite(), piece.getExits()[0].getDirection());
    }

    public int getDownwardsAngle(){
        return downwardsAngle;
    }

    public Rotation getAngle(){
        return angle;
    }
}
