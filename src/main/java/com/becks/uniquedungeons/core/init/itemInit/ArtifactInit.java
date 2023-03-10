package com.becks.uniquedungeons.core.init.itemInit;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.*;
import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.BoundSoulBoots;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.BoundSoulChestplate;
import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.BoundSoulHelmet;
import com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor.BoundSoulLeggins;
import com.becks.uniquedungeons.core.itemgroup.UniqueDungeonsItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Random;

public class ArtifactInit {
    public static final DeferredRegister<Item> ARTIFACTS = DeferredRegister.create(ForgeRegistries.ITEMS, UniqueDungeons.MOD_ID);
    public static HashMap<String, ArtifactItem> nameLookupTable = new HashMap<>();

    public static final RegistryObject<BloodSword> BLOOD_SWORD = ARTIFACTS.register("blood_sword", () -> new BloodSword(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC).durability(2031)));

    public static final RegistryObject<RageBlade> RAGE_BLADE = ARTIFACTS.register("rage_blade", () -> new RageBlade(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC).durability(2031)));

    public static final RegistryObject<ReflectingShield> REFLECTING_SHIELD = ARTIFACTS.register("reflecting_shield", () -> new ReflectingShield(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC).durability(2000)));

    public static final RegistryObject<AmethystShield> AMETHYST_SHIELD = ARTIFACTS.register("amethyst_shield", () -> new AmethystShield(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC).durability(2000)));

    public static final RegistryObject<BoundSoulBoots> BOUND_SOUL_BOOTS = ARTIFACTS.register("bound_soul_boots", () -> new BoundSoulBoots(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC)));
    public static final RegistryObject<BoundSoulLeggins> BOUND_SOUL_LEGGINS = ARTIFACTS.register("bound_soul_leggins", () -> new BoundSoulLeggins(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC)));
    public static final RegistryObject<BoundSoulChestplate> BOUND_SOUL_CHESTPLATE = ARTIFACTS.register("bound_soul_chestplate", () -> new BoundSoulChestplate(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC)));
    public static final RegistryObject<BoundSoulHelmet> BOUND_SOUL_HELMET = ARTIFACTS.register("bound_soul_helmet", () -> new BoundSoulHelmet(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC)));

    public static final RegistryObject<SonicBow> SONIC_BOW = ARTIFACTS.register("sonic_bow", () -> new SonicBow(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.EPIC).durability(384)));

    public static void buildNameLookup() {
        for (RegistryObject o : ARTIFACTS.getEntries()) {
            nameLookupTable.put(o.get().toString(), (ArtifactItem) o.get());
        }
    }

    public static ArtifactItem lookUp(String key){
        return nameLookupTable.get(key);
    }

    public static ArtifactItem getRandom(){
        Random random = new Random();
        int artifactIndex = random.nextInt(nameLookupTable.values().size());
        return (ArtifactItem)nameLookupTable.values().toArray()[artifactIndex];
    }

}
