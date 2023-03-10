package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util;

import net.minecraft.core.Direction;

/**
 * represents a connection point between two ADesertDungeonPiece, either the entrance to a piece or the exit
 */
public class PieceConnection {
    private final Direction direction;
    private final int horizontalOffset;
    private final int vertikalOffset;

    /**
     * creates a new PieceConnection
     * @param direction the direction of the face of the piece cube the connection is on when seen from the inside of the cube
     * @param vertikalOffset vertical offset of the connection on the face of the cube, counting from left to right when looking from the outside of the cube
     * @param horizontalOffset horizontal offset of the connection on the face of the cube, counting from bottom to top when looking from the outside of the cube
     */
    public PieceConnection(Direction direction, int vertikalOffset, int horizontalOffset) {
        this.direction = direction;
        this.horizontalOffset = horizontalOffset;
        this.vertikalOffset = vertikalOffset;
    }

    public int getHorizontalOffset(){
        return horizontalOffset;
    }

    public int getVertikalOffset(){
        return vertikalOffset;
    }

    public Direction getDirection(){
        return direction;
    }
}
