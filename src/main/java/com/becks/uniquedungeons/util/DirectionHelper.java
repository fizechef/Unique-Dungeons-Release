package com.becks.uniquedungeons.util;

import com.mojang.math.Vector3d;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public abstract class DirectionHelper {
    public static Rotation getRotation(Direction from, Direction toMatch){
        if (from.equals(Direction.UP) || from.equals(Direction.DOWN) || toMatch.equals(Direction.UP) || toMatch.equals(Direction.DOWN)){
            return Rotation.NONE;
        }
        Rotation r = Rotation.NONE;
        while (!r.rotate(toMatch).equals(from.getOpposite())){
            //System.out.println(r);
            r = r.getRotated(Rotation.CLOCKWISE_90);
        }
        return r;
    }

    public static Vec3i getOffsets(Rotation or, Rotation r, Direction d){
        Vec3i newX = or.rotate(Direction.EAST).getNormal();
        Vec3i newZ = or.rotate(Direction.SOUTH).getNormal();
       //System.out.println(or);
       //System.out.println(newX + " " + newZ);
       //System.out.println(r);
       //System.out.println(d);
       //System.out.println(d.getNormal());
        switch(r){
            case NONE:
                return d.getNormal().multiply(25);
            case CLOCKWISE_90:
                return newX.multiply(24).offset(d.getNormal().multiply(25));
            case CLOCKWISE_180:
                return newX.multiply(24).offset(newZ.multiply(24)).offset(d.getNormal().multiply(25));
            case COUNTERCLOCKWISE_90:
                return newZ.multiply(24).offset(d.getNormal().multiply(25));
        }
        return null;
    }

    public static Vec3i getRotatedVec(Vec3i toRotate, Rotation rotation){
        Vec3i newX = rotation.rotate(Direction.EAST).getNormal();
        Vec3i newZ = rotation.rotate(Direction.SOUTH).getNormal();
        Vec3i newY = Direction.UP.getNormal();
        newY = newY.multiply(toRotate.getY());
        newX = newX.multiply(toRotate.getX());
        newZ = newZ.multiply(toRotate.getZ());
        return newX.offset(newY).offset(newZ);
    }

    public static Vector3d getRotatedVec(Vector3d toRotate, Rotation rotation){
        Vec3i newX = rotation.rotate(Direction.EAST).getNormal();
        Vec3i newZ = rotation.rotate(Direction.SOUTH).getNormal();
        Vec3i newY = Direction.UP.getNormal();
        Vector3d newXD = new Vector3d(newX.getX(), newX.getY(), newX.getZ());
        Vector3d newYD = new Vector3d(newY.getX(), newY.getY(), newY.getZ());
        Vector3d newZD = new Vector3d(newZ.getX(), newZ.getY(), newZ.getZ());
        newYD.scale(toRotate.y);
        newXD.scale(toRotate.x);
        newZD.scale(toRotate.z);
        Vector3d result = new Vector3d(0, 0, 0);
        result.add(newXD);
        result.add(newYD);
        result.add(newZD);
        return result;
    }

    public static AABB rotateAABBblockCenterRelated(AABB toRotate, Rotation r){
        //System.out.println(toRotate);
        Vector3d firstCorner = new Vector3d(((double)toRotate.minX), ((double)toRotate.minY), ((double)toRotate.minZ));
        Vector3d secondCorner = new Vector3d(((double)toRotate.maxX), ((double)toRotate.maxY), ((double)toRotate.maxZ));
        firstCorner.scale(16);
        secondCorner.scale(16);
        //System.out.println("original: " + firstCorner.x + " " + firstCorner.y + " " + firstCorner.z + " " + " to " + secondCorner.x + " " + secondCorner.y + " " + secondCorner.z);
        Vector3d inversMiddleShift = new Vector3d(-8,-8,-8);
        firstCorner.add(inversMiddleShift);
        secondCorner.add(inversMiddleShift);
        //System.out.println("offset: " + firstCorner.x + " " + firstCorner.y + " " + firstCorner.z + " " + " to " + secondCorner.x + " " + secondCorner.y + " " + secondCorner.z);
        firstCorner = getRotatedVec(firstCorner, r);
        Vector3d middleShift = new Vector3d(8,8,8);
        firstCorner.add(middleShift);
        secondCorner = getRotatedVec(secondCorner, r);
        secondCorner.add(middleShift);
        firstCorner.scale(1.0/16.0);
        secondCorner.scale(1.0/16.0);
        //System.out.println("rotated: " + firstCorner.x + " " + firstCorner.y + " " + firstCorner.z + " " + " to " + secondCorner.x + " " + secondCorner.y + " " + secondCorner.z);
        AABB newAABB = new AABB(
                Math.min(firstCorner.x, secondCorner.x),
                Math.min(firstCorner.y, secondCorner.y),
                Math.min(firstCorner.z, secondCorner.z),
                Math.max(firstCorner.x, secondCorner.x),
                Math.max(firstCorner.y, secondCorner.y),
                Math.max(firstCorner.z, secondCorner.z));
        //System.out.println(newAABB);
        return newAABB;
    }

    public static int getRotationDegrees(Rotation r){
        switch(r){
            case NONE -> {
                return 0;
            }
            case CLOCKWISE_90 -> {
                return 90;
            }
            case CLOCKWISE_180 -> {
                return 180;
            }
            case COUNTERCLOCKWISE_90 -> {
                return 270;
            }
        }
        return 0;
    }

    public static Rotation getRotationNoOpp(Direction from, Direction toMatch) {
        if (from.equals(Direction.UP) || from.equals(Direction.DOWN) || toMatch.equals(Direction.UP) || toMatch.equals(Direction.DOWN)){
            return Rotation.NONE;
        }
        Rotation r = Rotation.NONE;
        while (!r.rotate(from).equals(toMatch)){
            //System.out.println(r);
            r = r.getRotated(Rotation.CLOCKWISE_90);
        }
        return r;
    }
}
