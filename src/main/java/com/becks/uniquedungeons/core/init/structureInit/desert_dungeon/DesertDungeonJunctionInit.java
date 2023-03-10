package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonJunctionRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonJunction;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

/**
 * Place to register all DesertDungeon junctions,
 * call DesertDungeonJunctionRegistry.register to register new junction type
 */
public class DesertDungeonJunctionInit {

    private static final DesertDungeonJunction JUNCTION_CRYSTAL = DesertDungeonJunctionRegistry.register(
            "desert_dungeon_junktion_crystal",
            new PieceConnection(Direction.NORTH, 11, 13),
            new PieceConnection[]{
                    new PieceConnection(Direction.WEST,5, 13),
                    new PieceConnection(Direction.EAST,5, 13)
            },
            new Tuple[0]
    );

    private static final DesertDungeonJunction MAYBE_JUNCTION_YES = DesertDungeonJunctionRegistry.register(
            "desert_dungeon_maybe_junction_yes",
            new PieceConnection(Direction.WEST, 4, 5),
            new PieceConnection[]{
                    new PieceConnection(Direction.EAST,4, 21),
                    new PieceConnection(Direction.SOUTH,12, 15)
            },
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(1, 1, 2), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(7, 1, 2), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(13, 1, 2), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(19, 1, 2), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(2, 2, 0), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(8, 2, 0), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(20, 2, 0), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(4, 2, 8), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(22, 2, 8), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(12, 1, 1), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(0, 1, 1), Rotation.NONE))
            }
    );

    public static void init(){
    }
}
