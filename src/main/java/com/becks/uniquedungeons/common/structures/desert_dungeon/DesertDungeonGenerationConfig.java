package com.becks.uniquedungeons.common.structures.desert_dungeon;

public class DesertDungeonGenerationConfig {

    //How many pieces a dungeon can have max per recursion-tree branch
    public static int NUM_PIECES_PER_REC = 30;

    //How many rooms a dungeon can have max per recursion-tree branch
    public static int NUM_ROOMS_PER_REC = 8;

    //How many junktions a dungeon can have max per recursion-tree branch
    public static int NUM_JUNC_PER_REC = 2;

    //The chance for a piece to be a corridor
    public static double CORRIDOR_PERCENTAGE = 0.2;

    //The chance for a piece to be a room
    public static double ROOM_PERCENTAGE = 0.6;

    //the chance for a piece to be a junktion
    public static double JUNKTION_PERCENTAGE = 0.2;

    //The percentage of dungeon from the start without rooms (to avoid surface clipping and make deep exploration more rewarding)
    public static final double ROOM_GEN_DUNGEON_PERCENTAGE = 0.7;

    //Radius around the generation center in wich the dungeon can freely generate. Gen outside this radius will be limited to get back in the radius to avoid cutoff
    public static final int OUTER_BOUNDS = 40;

    //how many times a section of dungeon can be regenerated before aborting if collision is detected. High numbers severally impact generation performance
    public static final int COLLISION_CHECK_LIMITER = 10;

    //How sloped the Terrain at a possible generation point can be
    public static final int TERRAIN_SLOPE = 10;

    //Lowest y a dungeon start can spawn at
    public static final int MIN_GEN_HEIGHT = 60;
}
