package com.becks.uniquedungeons.core.init;

import com.becks.uniquedungeons.UniqueDungeons;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionsInit {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, UniqueDungeons.MOD_ID);

    public static RegistryObject<Potion> GLOW_POTION = POTIONS.register("glowing_potion", () -> new Potion(new MobEffectInstance(MobEffects.GLOWING, 1200, 0)));

}
