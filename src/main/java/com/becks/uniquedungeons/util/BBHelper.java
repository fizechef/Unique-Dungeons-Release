 package com.becks.uniquedungeons.util;

import net.minecraft.util.Tuple;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BBHelper {

    public static boolean intersectAxisAA(int x1low, int x1high, int x2low, int x2high){
        if (x1low <= x2low){
            return x2low <= x1high;
        }
        else {
            return x1low <= x2high;
        }
    }

    public static boolean intersectAABB(BoundingBox bb, BoundingBox bb2){
        return intersectAxisAA(bb.minX(), bb.maxX(), bb2.minX(), bb2.maxX()) &&
                intersectAxisAA(bb.minY(), bb.maxY(), bb2.minY(), bb2.maxY()) &&
                intersectAxisAA(bb.minZ(), bb.maxZ(), bb2.minZ(), bb2.maxZ());
    }

    public static boolean intersectOneOfOrGround(ChunkGenerator chunkGenerator,
                                                 LevelHeightAccessor levelHeightAccessor,
                                                 RandomState randomState,
                                                 Collection<BoundingBox> of,
                                                 BoundingBox one){

        AtomicBoolean groundFlag = new AtomicBoolean(false);
        one.forAllCorners(p -> {
            if (p.getY() >= chunkGenerator.getFirstFreeHeight(p.getX(), p.getZ(), Heightmap.Types.WORLD_SURFACE, levelHeightAccessor, randomState)){
                System.out.println(p + " " + chunkGenerator.getFirstFreeHeight(p.getX(), p.getZ(), Heightmap.Types.WORLD_SURFACE, levelHeightAccessor, randomState));
                groundFlag.set(true);
            }
        });
        if (groundFlag.get()){
            System.out.println("Ground abortion");
            return true;
        }
        for(BoundingBox b : of) {
            if (b.minY() < -54){
                return true;
            }
            /*AtomicBoolean groundFlag = new AtomicBoolean(false);
            b.forAllCorners(p -> {
                if (p.getY() >= chunkGenerator.getFirstFreeHeight(p.getX(), p.getZ(), Heightmap.Types.WORLD_SURFACE, levelHeightAccessor, randomState)){
                    System.out.println(p + " " + chunkGenerator.getFirstFreeHeight(p.getX(), p.getZ(), Heightmap.Types.WORLD_SURFACE, levelHeightAccessor, randomState));
                    groundFlag.set(true);
                }
            });
            if (groundFlag.get()){
                System.out.println("Ground abortion");
                return true;
            }*/
            if (BBHelper.intersectAABB(b, one)){
                return true;
            }
        }
        return false;
    }

    public static Collection<BoundingBox> getToCheck(Collection<Tuple<StructurePiece, Boolean>> pieces){
        List<BoundingBox> l = new ArrayList<>();
        for (Tuple<StructurePiece, Boolean> t : pieces){
            if (t.getB()){
                l.add(t.getA().getBoundingBox());
            }
        }
        return l;
    }
}
