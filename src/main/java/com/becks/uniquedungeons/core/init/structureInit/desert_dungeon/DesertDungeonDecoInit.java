package com.becks.uniquedungeons.core.init.structureInit.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonDecoRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.DesertDungeonDeco;

/**
 * Place to register all DesertDungeon deco pieces, used to register other pieces,
 * call DesertDungeonDecoRegistry.register to register new deco type
 */
public class DesertDungeonDecoInit {

    public static final DesertDungeonDeco SMALL_STEP_TRAP = DesertDungeonDecoRegistry.register(
            "steptrap",
            new String[]{
                    "arrow",
                    "splash_potion",
                    "empty",
                    "flame_thrower",
                    "tnt"
            }
    );

    public static final DesertDungeonDeco SMALL_CAVERN = DesertDungeonDecoRegistry.register(
            "smallcavern",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4"
            }
    );

    public static final DesertDungeonDeco SINGLE_LIQUID = DesertDungeonDecoRegistry.register(
            "singleliquid",
            new String[]{
                    "1",
                    "2",
                    "3"
            }
    );

    public static final DesertDungeonDeco LONG_ORTHOGONAL_TRAP = DesertDungeonDecoRegistry.register(
            "long_orthogonal_trap",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4"
            }
    );

    public static final DesertDungeonDeco PILLARCAVE_INNER = DesertDungeonDecoRegistry.register(
            "pillarcave_inner",
            new String[]{
                    "jump",
                    "quicksand"
            }
    );

    public static final DesertDungeonDeco RANDOM_FLOOR_5X5 = DesertDungeonDecoRegistry.register(
            "random_floor_5x5",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6"
            }
    );

    public static final DesertDungeonDeco RANDOM_WALL_3X3 = DesertDungeonDecoRegistry.register(
            "random_wall_3x3",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6"
            }
    );

    public static final DesertDungeonDeco RANDOM_ROOM_SMALL = DesertDungeonDecoRegistry.register(
            "random_room_small",
            new String[]{
                    "base",
                    "bed",
                    "collapsed",
                    "paste",
                    "potionwire",
                    "shelf",
                    "spawner",
                    "lock_loaded",
                    "hole"
            }
    );

    public static final DesertDungeonDeco RANDOM_2X1 = DesertDungeonDecoRegistry.register(
            "random_2x1",
            new String[]{
                    "spikes",
                    "urn_1",
                    "urn_2",
                    "urn_1_spider",
                    "urn_2_spider"
            }
    );
    public static final DesertDungeonDeco RANDOM_GROUND_2X1 = DesertDungeonDecoRegistry.register(
            "random_ground_2x1",
            new String[]{
                    "broken",
                    "solid"
            }
    );

    public static final DesertDungeonDeco RANDOM_PILLAR_3X3X4 = DesertDungeonDecoRegistry.register(
            "random_pillar_3x3x4",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4",
                    "5"
            }
    );

    public static final DesertDungeonDeco RANDOM_ARCH_3X3 = DesertDungeonDecoRegistry.register(
            "random_arch_3x3",
            new String[]{
                    "1",
                    "2",
                    "3"
            }
    );

    public static final DesertDungeonDeco RANDOM_ARCH_5X4 = DesertDungeonDecoRegistry.register(
            "random_arch_5x4",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7"
            }
    );

    public static final DesertDungeonDeco CORRIDOR_BOOM_REDSTONE = DesertDungeonDecoRegistry.register(
            "corridor_boom_redstone",
            new String[]{
                    "1",
                    "2",
                    "3"
            }
    );

    public static final DesertDungeonDeco REDCANDLE_REDSTONE = DesertDungeonDecoRegistry.register(
            "redcandle_redstone",
            new String[]{
                    "1",
                    "2",
                    "3",
                    "4"
            }
    );

    public static final DesertDungeonDeco ENCHANT_WALL = DesertDungeonDecoRegistry.register(
            "enchant_wall",
            new String[]{
                    "1",
                    "2"
            }
    );


    public static void init(){
    }
}
