package com.becks.uniquedungeons.common.structures.desert_dungeon.pieces;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.core.init.structureInit.StructurePieceInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class DesertDungeonPiece extends TemplateStructurePiece {

    private Collection<DesertDungeonPiece> children = new ArrayList<>();
    public DesertDungeonPiece(StructureTemplateManager pStructureTemplateManager, String pName, BlockPos pStartPos, Rotation pRotation, boolean pOverwrite) {
        super(StructurePieceInit.DESERT_DUNGEON_1_TYPE.get(), 0, pStructureTemplateManager, makeResourceLocation(pName), pName, makeSettings(pOverwrite, pRotation), pStartPos);
        placeSettings.setKeepLiquids(false);
        placeSettings.setIgnoreEntities(false);
    }

    public DesertDungeonPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
        super(StructurePieceInit.DESERT_DUNGEON_1_TYPE.get(), pTag, pStructureTemplateManager, (p_227512_) -> {
            return makeSettings(pTag.getBoolean("OW"), Rotation.valueOf(pTag.getString("Rot")));
        });
        placeSettings.setKeepLiquids(false);
        placeSettings.setIgnoreEntities(false);
    }

    public DesertDungeonPiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
        super(StructurePieceInit.DESERT_DUNGEON_1_TYPE.get(), compoundTag, structurePieceSerializationContext.structureTemplateManager(), (p_227512_) -> {
            return makeSettings(compoundTag.getBoolean("OW"), Rotation.valueOf(compoundTag.getString("Rot")));
        });
        placeSettings.setKeepLiquids(false);
        placeSettings.setIgnoreEntities(false);
    }

    private static StructurePlaceSettings makeSettings(boolean pOverwriter, Rotation pRotation) {
        BlockIgnoreProcessor blockignoreprocessor = pOverwriter ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
        return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor(blockignoreprocessor).setRotation(pRotation);
    }

    protected ResourceLocation makeTemplateLocation() {
        return makeResourceLocation(this.templateName);
    }

    private static ResourceLocation makeResourceLocation(String pName) {
       //System.out.println("Generating desert_dungeon Location: " + new ResourceLocation(UniqueDungeons.MOD_ID + ':' + "desert_dungeon/" + pName));
        return new ResourceLocation(UniqueDungeons.MOD_ID + ':' + "desert_dungeon/" + pName);
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        super.addAdditionalSaveData(pContext, pTag);
        pTag.putString("Rot", this.placeSettings.getRotation().name());
        pTag.putBoolean("OW", this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK);
    }

    //Handle Data Markers for static Entity Spawns and Loot Chests
    protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
    }

    public void addChild(DesertDungeonPiece child){
        children.add(child);
    }

    public void callForAllRecursiveChildren(Consumer<DesertDungeonPiece> f){
        for (DesertDungeonPiece p : children){
            f.accept(p);
            p.callForAllRecursiveChildren(f);
        }
    }
}
