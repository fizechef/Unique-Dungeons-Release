package com.becks.uniquedungeons.common.structures.test_structure;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.core.init.structureInit.StructurePieceInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

public class TestStructurePieces {

    static TestStructurePieces.TestStructurePiece addPiece(StructureTemplateManager pStructureTemplateManager, TestStructurePieces.TestStructurePiece pPiece, BlockPos pStartPos, String pName, Rotation pRotation, boolean pOverwrite) {
        TestStructurePieces.TestStructurePiece pieces = new TestStructurePieces.TestStructurePiece(pStructureTemplateManager, pName, pPiece.templatePosition(), pRotation, pOverwrite);
        BlockPos blockpos = pPiece.template().calculateConnectedPosition(pPiece.placeSettings(), pStartPos, pieces.placeSettings(), BlockPos.ZERO);
        pieces.move(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return pieces;
    }

    public static void startGeneration(StructureTemplateManager pStructureTemplateManager, BlockPos pStartPos, Rotation pRotation, List<StructurePiece> pPieces, RandomSource pRandom) {
        TestStructurePieces.TestStructurePiece pieces = addHelper(pPieces, new TestStructurePieces.TestStructurePiece(pStructureTemplateManager, "test_structure", pStartPos, pRotation, true));
    }

    static TestStructurePieces.TestStructurePiece addHelper(List<StructurePiece> pPieces, TestStructurePieces.TestStructurePiece pPiece) {
        pPieces.add(pPiece);
        return pPiece;
    }

    static boolean recursiveChildren(StructureTemplateManager pStructureTemplateManager, TestStructurePieces.SectionGenerator pSectionGenerator, int pCounter, TestStructurePieces.TestStructurePiece pPiece, BlockPos pStartPos, List<StructurePiece> pPieces, RandomSource pRandom) {
        return false;
    }

    public static class TestStructurePiece extends TemplateStructurePiece {
        public TestStructurePiece(StructureTemplateManager pStructureTemplateManager, String pName, BlockPos pStartPos, Rotation pRotation, boolean pOverwrite) {
            super(StructurePieceInit.TEST_STRUCTURE_PIECE_TYPE.get(), 0, pStructureTemplateManager, makeResourceLocation(pName), pName, makeSettings(pOverwrite, pRotation), pStartPos);
        }

        public TestStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(StructurePieceInit.TEST_STRUCTURE_PIECE_TYPE.get(), pTag, pStructureTemplateManager, (p_227512_) -> {
                return makeSettings(pTag.getBoolean("OW"), Rotation.valueOf(pTag.getString("Rot")));
            });
        }

        public TestStructurePiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
            super(StructurePieceInit.TEST_STRUCTURE_PIECE_TYPE.get(), compoundTag, structurePieceSerializationContext.structureTemplateManager(), (p_227512_) -> {
                return makeSettings(compoundTag.getBoolean("OW"), Rotation.valueOf(compoundTag.getString("Rot")));
            });
        }

        private static StructurePlaceSettings makeSettings(boolean pOverwriter, Rotation pRotation) {
            BlockIgnoreProcessor blockignoreprocessor = pOverwriter ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
            return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor(blockignoreprocessor).setRotation(pRotation);
        }

        protected ResourceLocation makeTemplateLocation() {
            return makeResourceLocation(this.templateName);
        }

        private static ResourceLocation makeResourceLocation(String pName) {
            //System.out.println("Generating test_structure Location: " + new ResourceLocation(UniqueDungeons.MOD_ID + ':' /*+ "structures/"*/ + pName));
            return new ResourceLocation(UniqueDungeons.MOD_ID + ':' /*+ "structures/"*/ + pName);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putString("Rot", this.placeSettings.getRotation().name());
            pTag.putBoolean("OW", this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        //Handle Data Markers for static Entity Spawns and Loot Chests
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
        }
    }

    // Extends this to create section generators for the structure
    interface SectionGenerator {
        void init();
        boolean generate(StructureTemplateManager pStructureTemplateManager, int pCounter, TestStructurePieces.TestStructurePiece pPiece, BlockPos pStartPos, List<StructurePiece> pPieces, RandomSource pRandom);
    }
}
