package com.becks.uniquedungeons.common.structures.test_structure;

import com.becks.uniquedungeons.core.init.structureInit.StructureInit;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;


import java.util.*;


public class TestStructure extends Structure {

    public static final Codec<TestStructure> CODEC = simpleCodec(TestStructure::new);

    public TestStructure(Structure.StructureSettings p_227526_) {
        super(p_227526_);
    }

    public TestStructure() {
        super(new Structure.StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(BiomeTags.HAS_DESERT_PYRAMID), new HashMap(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE));
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext p_227528_) {
        Rotation rotation = Rotation.getRandom(p_227528_.random());
        BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(p_227528_, rotation);
        return blockpos.getY() < 60 ? Optional.empty() : Optional.of(new Structure.GenerationStub(blockpos, (p_227538_) -> {
            this.generatePieces(p_227538_, blockpos, rotation, p_227528_);
        }));
    }

    private void generatePieces(StructurePiecesBuilder p_227530_, BlockPos p_227531_, Rotation p_227532_, Structure.GenerationContext p_227533_) {
        List<StructurePiece> list = Lists.newArrayList();
        TestStructurePieces.startGeneration(p_227533_.structureTemplateManager(), p_227531_, p_227532_, list, p_227533_.random());
        list.forEach(p_227530_::addPiece);
    }

    public StructureType<?> type() {
        return StructureInit.TEST_STRUCTURE.get();
    }
}
