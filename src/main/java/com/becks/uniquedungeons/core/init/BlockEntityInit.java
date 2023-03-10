package com.becks.uniquedungeons.core.init;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.blocks.flamethrower.FlameThrowerBlockEntity;
import com.becks.uniquedungeons.common.blocks.lockPedestal.LockPedestalBlockEntity;
import com.becks.uniquedungeons.common.blocks.pedestal.PedestalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UniqueDungeons.MOD_ID);

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL =
            BLOCK_ENTITIES.register("pedestal", () ->
                    BlockEntityType.Builder.of(PedestalBlockEntity::new,
                            BlockInit.PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<LockPedestalBlockEntity>> LOCK_PEDESTAL =
            BLOCK_ENTITIES.register("lock_pedestal", () ->
                    BlockEntityType.Builder.of(LockPedestalBlockEntity::new,
                            BlockInit.LOCK_PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<FlameThrowerBlockEntity>> FLAME_THROWER =
            BLOCK_ENTITIES.register("flame_thrower", () ->
                    BlockEntityType.Builder.of(FlameThrowerBlockEntity::new,
                            BlockInit.FLAME_THROWER.get()).build(null));


}
