package com.becks.uniquedungeons.core.init.itemInit;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.*;
import com.becks.uniquedungeons.common.items.artifacts.SonicBow;
import com.becks.uniquedungeons.core.init.BlockInit;
import com.becks.uniquedungeons.core.itemgroup.UniqueDungeonsBlocks;
import com.becks.uniquedungeons.core.itemgroup.UniqueDungeonsItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Initialiesierungsklasse f�r Items und BlockItems
 * @author erike
 *
 */
public class GeneralItemInit {
	//"Liste" aller registrierten Items
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UniqueDungeons.MOD_ID);
	
	//Items
	//Jedes hier sollte eigene Klasse haben um eventuelle Funktionalit�t zu implementieren
	public static final RegistryObject<TestItem> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties()));
	public static final RegistryObject<CrystalShard> CRYSTAL_SHARD = ITEMS.register("crystal_shard", () -> new CrystalShard(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP)));
	public static final RegistryObject<ClimbingHookItem> CLIMBING_HOOK_ITEM = ITEMS.register("climbing_hook_item", () -> new ClimbingHookItem(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.RARE)));
	public static final RegistryObject<CrystalArrowItem> CRYSTAL_ARROW = ITEMS.register("crystal_arrow_item", () -> new CrystalArrowItem(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.RARE)));
	public static final RegistryObject<Note> NOTE = ITEMS.register("note", () -> new Note(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<AncientNote> ANCIENT_NOTE = ITEMS.register("ancient_note", () -> new AncientNote(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP).rarity(Rarity.UNCOMMON)));

	
	//Items
	//Items ohne Funktion
	public static final RegistryObject<Item> CERAMIC_SHARD = ITEMS.register("ceramic_shard", () -> new Item(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP)));
	public static final RegistryObject<Item> CLAY_PASTE = ITEMS.register("clay_paste", () -> new Item(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP)));
	public static final RegistryObject<AncientRootItem> ANCIENT_ROOT_ITEM = ITEMS.register("ancient_root_item", () -> new AncientRootItem(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP)));
	public static final RegistryObject<Item> ROPE = ITEMS.register("rope", () -> new Item(new Item.Properties().tab(UniqueDungeonsItems.ITEM_GROUP)));


	//BlockItems
	//Brauchen nicht unbedingt eigenen Klassen da sie ausschlie�lich Bl�cke repr�sentieren.
	public static final RegistryObject<BlockItem> TEST_BLOCK = ITEMS.register("test_block", () -> new BlockItem(BlockInit.TEST_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_STONE = ITEMS.register("dark_antique_stone", () -> new BlockItem(BlockInit.DARK_ANTIQUE_STONE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> CHISELED_DARK_ANTIQUE_STONE = ITEMS.register("chiseled_dark_antique_stone", () -> new BlockItem(BlockInit.CHISELED_DARK_ANTIQUE_STONE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> URN = ITEMS.register("urn", () -> new BlockItem(BlockInit.URN.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> EMPTY_URN = ITEMS.register("empty_urn", () -> new BlockItem(BlockInit.EMPTY_URN.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> CRYSTAL = ITEMS.register("crystal", () -> new BlockItem(BlockInit.CRYSTAL.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> PEDESTAL = ITEMS.register("pedestal", () -> new BlockItem(BlockInit.PEDESTAL.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> LOCK_PEDESTAL = ITEMS.register("lock_pedestal", () -> new BlockItem(BlockInit.LOCK_PEDESTAL.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));

	
	public static final RegistryObject<BlockItem> IRON_SPIKES = ITEMS.register("iron_spikes", () -> new BlockItem(BlockInit.IRON_SPIKES.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> CRACKED_DARK_ANTIQUE_STONE = ITEMS.register("cracked_dark_antique_stone", () -> new BlockItem(BlockInit.CRACKED_DARK_ANTIQUE_STONE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_GRAVEL = ITEMS.register("dark_antique_gravel", () -> new BlockItem(BlockInit.DARK_ANTIQUE_GRAVEL.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_PRESSURE_PLATE = ITEMS.register("dark_antique_stone_pressure_plate", () -> new BlockItem(BlockInit.DARK_ANTIQUE_PRESSURE_PLATE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> ANTIQUE_PEBBLE = ITEMS.register("antique_pebble", () -> new BlockItem(BlockInit.ANTIQUE_PEBBLE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> ANTIQUE_PEBBLE_PRESSURE_PLATE = ITEMS.register("antique_pebble_pressure_plate", () -> new BlockItem(BlockInit.ANTIQUE_PEBBLE_PRESSURE_PLATE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> FLAME_TRHOWER = ITEMS.register("flame_thrower", () -> new BlockItem(BlockInit.FLAME_THROWER.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DISGUISED_DISPENSER = ITEMS.register("disguised_dispenser", () -> new BlockItem(BlockInit.DISGUISED_DISPENSER.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> QUICK_SAND = ITEMS.register("quick_sand", () -> new BlockItem(BlockInit.QUICK_SAND.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DRIED_QUICK_SAND = ITEMS.register("dried_quick_sand", () -> new BlockItem(BlockInit.DRIED_QUICK_SAND.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> POTTERY_BLOCK = ITEMS.register("pottery_block", () -> new BlockItem(BlockInit.POTTERY_BLOCK.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> ANTIQUE_STONE_CARPET = ITEMS.register("antique_stone_carpet", () -> new BlockItem(BlockInit.ANTIQUE_STONE_CARPET.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_STONE_PILLAR = ITEMS.register("dark_antique_stone_pillar", () -> new BlockItem(BlockInit.DARK_ANTIQUE_STONE_PILLAR.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> ANCIENT_ROOTS = ITEMS.register("ancient_roots", () -> new BlockItem(BlockInit.ANCIENT_ROOTS.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> ROOTED_GROUND = ITEMS.register("rooted_ground", () -> new BlockItem(BlockInit.ROOTED_GROUND.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_STONE_SLAB = ITEMS.register("dark_antique_stone_slab", () -> new BlockItem(BlockInit.DARK_ANTIQUE_STONE_SLAB.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> DARK_ANTIQUE_STONE_STAIRS = ITEMS.register("dark_antique_stone_stairs", () -> new BlockItem(BlockInit.DARK_ANTIQUE_STONE_STAIRS.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));


	public static final RegistryObject<BlockItem> CLIMBING_HOOK = ITEMS.register("climbing_hook", () -> new BlockItem(BlockInit.CLIMBING_HOOK.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));
	public static final RegistryObject<BlockItem> CLIMBING_ROPE = ITEMS.register("climbing_rope", () -> new BlockItem(BlockInit.CLIMBING_ROPE.get(), new Item.Properties().tab(UniqueDungeonsBlocks.BLOCK_GROUP)));

}
