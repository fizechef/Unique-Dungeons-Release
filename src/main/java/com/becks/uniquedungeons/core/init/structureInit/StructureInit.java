package com.becks.uniquedungeons.core.init.structureInit;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.structures.desert_dungeon.DesertDungeon;
import com.becks.uniquedungeons.common.structures.test_structure.TestStructure;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.*;
;

public class StructureInit extends Structures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, UniqueDungeons.MOD_ID);

    public static RegistryObject<StructureType<TestStructure>> TEST_STRUCTURE = STRUCTURE_TYPE.register("test_structure", () -> () -> TestStructure.CODEC);

    public static RegistryObject<StructureType<DesertDungeon>> DESERT_DUNGEON_1 = STRUCTURE_TYPE.register("desert_dungeon", () -> () -> DesertDungeon.CODEC);
}
