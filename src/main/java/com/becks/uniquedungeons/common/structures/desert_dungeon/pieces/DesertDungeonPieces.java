package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces;

import com.becks.uniquedungeons.common.structures.desert_dungeon.DesertDungeonGenerationConfig;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonEndRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.registries.DesertDungeonStartRegistry;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.types.*;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.DesertDungeonMidPiecesUtil;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.util.PieceConnection;
import com.becks.uniquedungeons.util.BBHelper;
import com.becks.uniquedungeons.util.DirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;

public class DesertDungeonPieces {
    static DesertDungeonPiece addPiece(StructureTemplateManager pStructureTemplateManager, DesertDungeonPiece pPiece, BlockPos pStartPos, String pName, Rotation pRotation, boolean pOverwrite) {
        DesertDungeonPiece pieces = new DesertDungeonPiece(pStructureTemplateManager, pName, pPiece.templatePosition(), pRotation, pOverwrite);
        BlockPos blockpos = pPiece.template().calculateConnectedPosition(pPiece.placeSettings(), pStartPos, pieces.placeSettings(), BlockPos.ZERO);
        pieces.move(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return pieces;
    }

    public static void startGeneration(ChunkGenerator chunkGenerator, LevelHeightAccessor levelHeightAccessor, RandomState randomState, StructureTemplateManager pStructureTemplateManager, BlockPos pStartPos, Rotation pRotation, List<Tuple<StructurePiece, Boolean>> pPieces, RandomSource pRandom) {
        GENERATOR_1.init();
        DesertDungeonStart startType = DesertDungeonStartRegistry.getRandom();
        BlockPos pStartPosYCompensated = pStartPos.above(startType.getGenerationYOffset());
        DesertDungeonPiece piece = addHelper(pPieces, startType.get(pStructureTemplateManager, pStartPosYCompensated, pRotation), true);
        List<DesertDungeonRoom> usedRooms = new ArrayList<>();
        if (recursiveChildren(
                chunkGenerator,
                levelHeightAccessor,
                randomState,
                pStructureTemplateManager,
                GENERATOR_1,
                DesertDungeonGenerationConfig.NUM_PIECES_PER_REC,
                DesertDungeonGenerationConfig.NUM_ROOMS_PER_REC,
                DesertDungeonGenerationConfig.NUM_JUNC_PER_REC ,
                piece,
                startType,
                pStartPosYCompensated,
                pStartPosYCompensated,
                pPieces,
                usedRooms,
                pRandom,
                0) == -2){
            pPieces.clear();
        }
    }

    static DesertDungeonPiece addHelper(List<Tuple<StructurePiece, Boolean>> pPieces, DesertDungeonPiece pPiece, boolean checkIntersect) {
        pPieces.add(new Tuple<>(pPiece, checkIntersect));
        return pPiece;
    }

    static void removeHelper(List<Tuple<StructurePiece, Boolean>> pPieces, DesertDungeonPiece pPiece) {
        pPiece.callForAllRecursiveChildren(p -> {
            for (Tuple<StructurePiece, Boolean> t : pPieces){
                if (t.getA().equals(p)){
                    pPieces.remove(t);
                    break;
                }
            }
        });
        for (Tuple<StructurePiece, Boolean> t : pPieces){
            if (t.getA().equals(pPiece)){
                pPieces.remove(t);
                break;
            }
        }
    }

    static int recursiveChildren(ChunkGenerator chunkGenerator,
                                 LevelHeightAccessor levelHeightAccessor,
                                 RandomState randomState,
                                 StructureTemplateManager pStructureTemplateManager,
                                 SectionGenerator pSectionGenerator,
                                 int pieceLimit,
                                 int roomLimit,
                                 int junktionLimit,
                                 DesertDungeonPiece pPiece,
                                 ADesertDungeonPiece lastPieceType,
                                 BlockPos pStartPos,
                                 BlockPos absoluteStart,
                                 List<Tuple<StructurePiece, Boolean>> pPieces,
                                 List<DesertDungeonRoom> usedRooms, RandomSource pRandom,
                                 int collisionCheckLimiter) {
        if (collisionCheckLimiter > DesertDungeonGenerationConfig.COLLISION_CHECK_LIMITER){
            return -2;
        }
        int generated = pSectionGenerator.generate(chunkGenerator, levelHeightAccessor, randomState, pStructureTemplateManager, pieceLimit, roomLimit, junktionLimit, pPiece, lastPieceType, pStartPos, absoluteStart, pPieces, usedRooms, pRandom);
        if (generated == -2){
            return -1;
        }
        return generated;
    }

    static final DesertDungeonPieces.SectionGenerator GENERATOR_1 = new DesertDungeonPieces.SectionGenerator() {
        public void init() {
        }

        public int generate(ChunkGenerator chunkGenerator, LevelHeightAccessor levelHeightAccessor, RandomState randomState, StructureTemplateManager manager, int pieceLimit, int roomLimit, int junktionLimit, DesertDungeonPiece lastPiece, ADesertDungeonPiece lastPieceType, BlockPos startPos, BlockPos absoluteStart, List<Tuple<StructurePiece, Boolean>> pieces, List<DesertDungeonRoom> usedRooms, RandomSource random) {
            int recRoomLimit = roomLimit;
            int recJunktionLimit = junktionLimit;
           //System.out.println(reccount + " pieces left to generate");
            if(pieceLimit <= 0){
                return 0;
            }
            Rotation rotation = lastPiece.placeSettings().getRotation();
            boolean allDirectionsSuccess = true;
            for (PieceConnection c : lastPieceType.getExits()){
                //eSystem.out.println(c.getDirection() + " " + c.getHorizontalOffset() + " " + c.getVertikalOffset());
                if (!allDirectionsSuccess){
                    return -1;
                }
                Direction rotatedD = rotation.rotate(c.getDirection());
                int tries = 0;
                int recSuccess = -1;
                DesertDungeonPiece piece = null;
                while (piece == null || recSuccess == -1) {
                    if (piece != null){
                        removeHelper(pieces, piece);
                    }
                    if (tries > DesertDungeonGenerationConfig.COLLISION_CHECK_LIMITER){
                        allDirectionsSuccess = false;
                        break;
                    }
                    ADesertDungeonPiece newType =
                            pieceLimit<=1
                                    ?
                                    DesertDungeonEndRegistry.getRandom()
                                    :
                                    DesertDungeonMidPiecesUtil.getRandom(
                                            usedRooms,
                                            lastPieceType,
                                            DesertDungeonGenerationConfig.CORRIDOR_PERCENTAGE,
                                            DesertDungeonGenerationConfig.ROOM_PERCENTAGE,
                                            DesertDungeonGenerationConfig.JUNKTION_PERCENTAGE,
                                            (recRoomLimit > 0)
                                                    &&
                                                    (pieceLimit <= (DesertDungeonGenerationConfig.ROOM_GEN_DUNGEON_PERCENTAGE * (double) DesertDungeonGenerationConfig.NUM_PIECES_PER_REC))
                                                    &&
                                                    Math.abs(startPos.getX() - absoluteStart.getX()) < DesertDungeonGenerationConfig.OUTER_BOUNDS
                                                    &&
                                                    startPos.getY() >= -40,
                                            recJunktionLimit > 0
                                                    &&
                                                    Math.abs(startPos.getX() - absoluteStart.getX()) < DesertDungeonGenerationConfig.OUTER_BOUNDS
                                                    &&
                                                    startPos.getY() >= -40,
                                            startPos.getY(),
                                            startPos.getX() - absoluteStart.getX(),
                                            startPos.getZ() - absoluteStart.getZ(),
                                            rotatedD);

                    Vec3i offsetsVec = Rotation.CLOCKWISE_90.rotate(rotatedD).getNormal().multiply(-(c.getHorizontalOffset()-13)).offset(new Vec3i(0, 1, 0).multiply((c.getVertikalOffset()-13)));
                    PieceConnection newTypeEntrance = newType.getEntrance();
                    Rotation matchRotation = DirectionHelper.getRotation(c.getDirection(), newTypeEntrance.getDirection());
                    Rotation actualRotation = rotation.getRotated(matchRotation);
                    Vec3i cubeOffsets = DirectionHelper.getOffsets(rotation, matchRotation, rotatedD);
                    Vec3i secondaryOffsetsVec = Rotation.CLOCKWISE_90.rotate(rotatedD).getNormal().multiply(-(newTypeEntrance.getHorizontalOffset()-13)).offset(new Vec3i(0, 1, 0).multiply(-(newTypeEntrance.getVertikalOffset()-13)));
                    BlockPos newPos = startPos.offset(cubeOffsets).offset(offsetsVec).offset(secondaryOffsetsVec);

                    piece = newType.get(manager, newPos, actualRotation);

                    if (BBHelper.intersectOneOfOrGround(chunkGenerator, levelHeightAccessor, randomState, BBHelper.getToCheck(pieces), piece.getBoundingBox())){
                    }
                    else {
                        pieces.add(new Tuple<>(piece, true));
                        if (newType instanceof DesertDungeonRoom){
                            usedRooms.add((DesertDungeonRoom) newType);
                            //System.out.println("Using room " + newType.getName());
                            recRoomLimit--;
                        }
                        else if(newType instanceof DesertDungeonJunction){
                            recJunktionLimit--;
                        }
                        recSuccess = recursiveChildren(chunkGenerator, levelHeightAccessor, randomState, manager, this, --pieceLimit, recRoomLimit, recJunktionLimit, piece, newType, newPos, absoluteStart, pieces, usedRooms, random, tries);
                        if (recSuccess >= 0){
                            lastPiece.addChild(piece);
                            for (Tuple<DesertDungeonDeco, Tuple<Vec3i, Rotation>> t : newType.getDeco()){
                                Rotation decoRotation = t.getB().getB().getRotated(actualRotation);
                                Vec3i relativeOffset = DirectionHelper.getRotatedVec(t.getB().getA(), actualRotation);
                                DesertDungeonPiece decoPiece = addHelper(pieces, t.getA().get(manager, newPos.offset(relativeOffset), decoRotation), false);
                                piece.addChild(decoPiece);
                            }
                        }
                        else {
                            if (newType instanceof DesertDungeonRoom){
                                usedRooms.remove(newType);
                            }
                            removeHelper(pieces, piece);
                        }
                        if (recSuccess == -2){
                            allDirectionsSuccess = false;
                        }
                    }
                    tries++;
                }
            }
            if (!allDirectionsSuccess){
                return -1;
            }
            return 1;
        }
    };

    // Extends this to create section generators for the structure
    interface SectionGenerator {
        void init();
        int generate(ChunkGenerator chunkGenerator, LevelHeightAccessor levelHeightAccessor, RandomState randomState, StructureTemplateManager pStructureTemplateManager, int pieceLimit, int roomLimit, int junktionLimit, DesertDungeonPiece pPiece, ADesertDungeonPiece lastPieceType, BlockPos pStartPos, BlockPos absoluteStart, List<Tuple<StructurePiece, Boolean>> pPieces, List<DesertDungeonRoom> usedRooms, RandomSource pRandom);
    }
}