package com.becks.uniquedungeons.core.init;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.entities.AmethystProjectile;
import com.becks.uniquedungeons.common.entities.CrystalArrow;
import com.becks.uniquedungeons.common.entities.SonicBoomProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UniqueDungeons.MOD_ID);

    public static final RegistryObject<EntityType<AmethystProjectile>> AMETHYST_PROJECTILE = ENTITIES.register("amethyst_projectile",
            () -> (EntityType.Builder.<AmethystProjectile>of(AmethystProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)).build("amethyst_projectile"));

    public static final RegistryObject<EntityType<SonicBoomProjectile>> SONIC_BOOM_PROJECTILE = ENTITIES.register("sonic_boom_projectile",
            () -> (EntityType.Builder.<SonicBoomProjectile>of(SonicBoomProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)).build("sonic_boom_projectile"));

    public static final RegistryObject<EntityType<CrystalArrow>> CRYSTAL_ARROW = ENTITIES.register("crystal_arrow",
            () -> (EntityType.Builder.<CrystalArrow>of(CrystalArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)).build("crystal_arrow"));

}
