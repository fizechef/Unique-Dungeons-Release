package com.becks.uniquedungeons.core.init.structureInit;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.structures.desert_dungeon.pieces.DesertDungeonPiece;
import com.becks.uniquedungeons.common.structures.test_structure.TestStructurePieces;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public final class StructurePieceInit {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, UniqueDungeons.MOD_ID);
    public static final RegistryObject<StructurePieceType> TEST_STRUCTURE_PIECE_TYPE = STRUCTURE_PIECES.register("test_structure", () -> TestStructurePieces.TestStructurePiece::new);

    public static final RegistryObject<StructurePieceType> DESERT_DUNGEON_1_TYPE = STRUCTURE_PIECES.register("desert_dungeon", () -> DesertDungeonPiece::new);

}
