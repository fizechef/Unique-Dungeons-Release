package com.becks.uniquedungeons.common.structures.desert_dungeon;

import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPieces;
import com.becks.uniquedungeons.core.init.structureInit.StructureInit;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;


import java.util.*;


public class DesertDungeon extends Structure {

    public static final Codec<DesertDungeon> CODEC = simpleCodec(DesertDungeon::new);

    public DesertDungeon(Structure.StructureSettings p_227526_) {
        super(p_227526_);
    }

    public DesertDungeon() {
        super(new Structure.StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(BiomeTags.IS_OVERWORLD), new HashMap(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE));
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        ChunkPos chunkpos = context.chunkPos();
        BlockPos genpos = chunkpos.getWorldPosition();
        Random rand = new Random();
        genpos.offset(rand.nextInt(16),0,rand.nextInt(16));
        Direction xVec = rotation.rotate(Direction.SOUTH);
        Direction zVec = rotation.rotate(Direction.EAST);

        BlockPos cornerPos1 = genpos.offset(xVec.getNormal().multiply(25));
        BlockPos cornerPos2 = genpos.offset(zVec.getNormal().multiply(25));
        BlockPos cornerPos3 = genpos.offset(xVec.getNormal().multiply(25)).offset(zVec.getNormal().multiply(25));
        BlockPos centerPos = genpos.offset(xVec.getNormal().multiply(13)).offset(zVec.getNormal().multiply(13));

        int y1 = context.chunkGenerator().getFirstOccupiedHeight(genpos.getX(), genpos.getZ(), Heightmap.Types.WORLD_SURFACE, context.heightAccessor(), context.randomState());
        int y2 = context.chunkGenerator().getFirstOccupiedHeight(cornerPos1.getX(), cornerPos1.getZ(), Heightmap.Types.WORLD_SURFACE, context.heightAccessor(), context.randomState());
        int y3 = context.chunkGenerator().getFirstOccupiedHeight(cornerPos2.getX(), cornerPos2.getZ(), Heightmap.Types.WORLD_SURFACE, context.heightAccessor(), context.randomState());
        int y4 = context.chunkGenerator().getFirstOccupiedHeight(cornerPos3.getX(), cornerPos3.getZ(), Heightmap.Types.WORLD_SURFACE, context.heightAccessor(), context.randomState());
        int y5 = context.chunkGenerator().getFirstOccupiedHeight(centerPos.getX(), centerPos.getZ(), Heightmap.Types.WORLD_SURFACE, context.heightAccessor(), context.randomState());

        int miny = Math.min(y1, Math.min(y2, Math.min(y3, Math.min(y4, y5))));

        if (Math.abs(Math.max(y1, Math.max(y2, Math.max(y3, Math.max(y4, y5)))) - miny) > DesertDungeonGenerationConfig.TERRAIN_SLOPE){
            return Optional.empty();
        }

        final BlockPos finalGenpos = new BlockPos(genpos.getX(), miny, genpos.getZ());

        return finalGenpos.getY() < DesertDungeonGenerationConfig.MIN_GEN_HEIGHT ? Optional.empty() : Optional.of(new Structure.GenerationStub(finalGenpos, (p_227538_) -> {
            this.generatePieces(p_227538_, finalGenpos, rotation, context);
        }));
    }

    private void generatePieces(StructurePiecesBuilder builder, BlockPos p_227531_, Rotation p_227532_, Structure.GenerationContext context) {
        List<Tuple<StructurePiece, Boolean>> list = Lists.newArrayList();
        DesertDungeonPieces.startGeneration(context.chunkGenerator(), context.heightAccessor(), context.randomState(), context.structureTemplateManager(), p_227531_, p_227532_, list, context.random());
        list.forEach(t -> builder.addPiece(t.getA()));
    }

    public StructureType<?> type() {
        return StructureInit.DESERT_DUNGEON_1.get();
    }

}
