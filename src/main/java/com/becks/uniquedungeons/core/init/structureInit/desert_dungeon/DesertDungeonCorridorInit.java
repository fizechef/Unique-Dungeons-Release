package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonCorridorRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonCorridor;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Rotation;

/**
 * Place to register all DesertDungeon corridors,
 * call DesertDungeonCorridorRegistry.register to register new corridor type
 */
public class DesertDungeonCorridorInit {

    private static final DesertDungeonCorridor CORRIDOR_BOOM = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_boom",
            new PieceConnection(Direction.NORTH, 8, 12),
            new PieceConnection(Direction.EAST, 10, 13),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.CORRIDOR_BOOM_REDSTONE, new Tuple(new Vec3i(0, 0, 0), Rotation.NONE)),
            }
    );

    private static final DesertDungeonCorridor GRAVELWAY = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_gravelway",
            new PieceConnection(Direction.WEST, 13, 15),
            new PieceConnection(Direction.NORTH, 5, 15),
            new Tuple[0]
    );

    private static final DesertDungeonCorridor NARROW_CAVE = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_mid_3",
            new PieceConnection(Direction.WEST, 13, 13),
            new PieceConnection(Direction.EAST, 10, 14),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.SMALL_STEP_TRAP, new Tuple(new Vec3i(12, 6, 13), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.SMALL_CAVERN, new Tuple(new Vec3i(5, 7, 0), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.SINGLE_LIQUID, new Tuple(new Vec3i(18, 11, 17), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor STAIRS_UP = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_stairs_up",
            new PieceConnection(Direction.WEST, 5, 12),
            new PieceConnection(Direction.EAST, 13, 14),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.LONG_ORTHOGONAL_TRAP, new Tuple(new Vec3i(20, 8, 7), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_GUARDIAN = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_guardian",
            new PieceConnection(Direction.SOUTH, 21, 12),
            new PieceConnection(Direction.NORTH, 6, 13),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_PILLAR_3X3X4, new Tuple(new Vec3i(9, 18, 19), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ARCH_3X3, new Tuple(new Vec3i(10, 19, 23), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_GROUND_2X1, new Tuple(new Vec3i(9, 14, 11), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_GROUND_2X1, new Tuple(new Vec3i(12, 9, 15), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_2X1, new Tuple(new Vec3i(12, 10, 13), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ARCH_5X4, new Tuple(new Vec3i(10, 4, 0), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_TRAPS = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_traps",
            new PieceConnection(Direction.NORTH, 17, 20),
            new PieceConnection(Direction.SOUTH, 6, 19),
            new Tuple[0]
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_FIRE = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_fire",
            new PieceConnection(Direction.EAST, 8, 15),
            new PieceConnection(Direction.NORTH, 8, 17),
            new Tuple[0]
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_WATER = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_water",
            new PieceConnection(Direction.NORTH, 20, 6),
            new PieceConnection(Direction.WEST, 5, 20),
            new Tuple[0]
    );

    private static final DesertDungeonCorridor MAYBE_JUNCTION_NO = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_maybe_junction_no",
            new PieceConnection(Direction.WEST, 4, 5),
            new PieceConnection(Direction.EAST, 4, 21),
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
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(0, 1, 1), Rotation.NONE)),

                    new Tuple<>(DesertDungeonDecoInit.SMALL_CAVERN, new Tuple(new Vec3i(14, 0, 19), Rotation.CLOCKWISE_180))

            }
    );

    private static final DesertDungeonCorridor MAYBE_JUNCTION_NO_SAND = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_maybe_junction_no_sand",
            new PieceConnection(Direction.WEST, 12, 10),
            new PieceConnection(Direction.SOUTH, 12, 18),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(1, 9, 7), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(7, 9, 7), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(13, 9, 7), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(19, 9, 7), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(19, 9, 13), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(19, 9, 19), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(2, 10, 5), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(8, 10, 5), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(20, 10, 5), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(4, 10, 13), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(25, 10, 8), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(25, 10, 14), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(25, 10, 20), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(22, 10, 25), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(13, 10, 22), Rotation.COUNTERCLOCKWISE_90)),
                    //new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(12, 9, 6), Rotation.NONE)), //this breaks sand trap sometimes
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(0, 9, 6), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor MAYBE_JUNCTION_NO_BARREL = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_maybe_junction_no_barrel",
            new PieceConnection(Direction.WEST, 11, 15),
            new PieceConnection(Direction.NORTH, 11, 16),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(1, 8, 12), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(7, 8, 12), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(13, 8, 12), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(19, 8, 12), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(7, 8, 6), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_FLOOR_5X5, new Tuple(new Vec3i(7, 8, 0), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(2, 9, 10), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(20, 9, 10), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(25, 9, 13), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(22, 9, 18), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(4, 9, 18), Rotation.CLOCKWISE_180)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(13, 9, 7), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(13, 9, 1), Rotation.CLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(5, 9, 9), Rotation.COUNTERCLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_WALL_3X3, new Tuple(new Vec3i(5, 9, 3), Rotation.COUNTERCLOCKWISE_90)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(12, 8, 11), Rotation.NONE)),
                    new Tuple<>(DesertDungeonDecoInit.RANDOM_ROOM_SMALL, new Tuple(new Vec3i(0, 8, 11), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_SPIKES = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_spikes",
            new PieceConnection(Direction.NORTH, 20, 6),
            new PieceConnection(Direction.WEST, 10, 19),
            new Tuple[0]
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_ENCHANT = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_enchant",
            new PieceConnection(Direction.NORTH, 12, 13),
            new PieceConnection(Direction.SOUTH, 13, 13),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.ENCHANT_WALL, new Tuple(new Vec3i(11, 6, 11), Rotation.NONE))
            }
    );

    private static final DesertDungeonCorridor CORNER_CAVE_RIGHT = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corner_cave_right",
            new PieceConnection(Direction.NORTH, 5, 4),
            new PieceConnection(Direction.WEST, 6, 22),
            new Tuple[]{
                    new Tuple<>(DesertDungeonDecoInit.SMALL_CAVERN, new Tuple(new Vec3i(17, 1, 25), Rotation.CLOCKWISE_180))
            }
    );

    private static final DesertDungeonCorridor DESERT_DUNGEON_CORRIDOR_ARMORY = DesertDungeonCorridorRegistry.register(
            "desert_dungeon_corridor_armory",
            new PieceConnection(Direction.NORTH, 11, 17),
            new PieceConnection(Direction.EAST, 8, 11),
            new Tuple[0]
    );

    public static void init(){
    }
}
