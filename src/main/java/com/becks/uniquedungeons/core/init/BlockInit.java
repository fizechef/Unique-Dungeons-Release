package com.becks.uniquedungeons.core.init;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.blocks.*;
import com.becks.uniquedungeons.common.blocks.cracked_blocks.CrackedStone;
import com.becks.uniquedungeons.common.blocks.flamethrower.FlameThrower;

import com.becks.uniquedungeons.common.blocks.lockPedestal.LockPedestal;
import com.becks.uniquedungeons.common.blocks.pedestal.Pedestal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Klasse zum Registrieren aller Blöcke
 * @author erike
 *
 */
public class BlockInit {
	//"Liste" aller Bl�cke
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UniqueDungeons.MOD_ID);
	
	//Hinzufügen eines Blocks
	public static final RegistryObject<Block> TEST_BLOCK =
			BLOCKS.register("test_block", 
					() -> new TestBlock(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
							.strength(5)));
	public static final RegistryObject<Block> DARK_ANTIQUE_STONE = 
			BLOCKS.register("dark_antique_stone", 
					() -> new DarkAntiqueStone(
							Block.Properties.of(Material.STONE)
									.requiresCorrectToolForDrops()
									.strength(3f,  5f)));
	public static final RegistryObject<Block> DARK_ANTIQUE_STONE_SLAB =
			BLOCKS.register("dark_antique_stone_slab",
					() -> new DarkAntiqueStoneSlab(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(3f,  5f)));
	public static final RegistryObject<Block> DARK_ANTIQUE_STONE_STAIRS =
			BLOCKS.register("dark_antique_stone_stairs",
					() -> new DarkAntiqueStoneStairs(()-> DARK_ANTIQUE_STONE.get().defaultBlockState(),
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(3f,  5f)));
	public static final RegistryObject<Block> PEDESTAL = 
			BLOCKS.register("pedestal", 
					() -> new Pedestal(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops().noOcclusion()
									.strength(3f,  5f)));

	public static final RegistryObject<Block> LOCK_PEDESTAL =
			BLOCKS.register("lock_pedestal",
					() -> new LockPedestal(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops().noOcclusion()
									.strength(3f,  5f)));
	public static final RegistryObject<Block> CHISELED_DARK_ANTIQUE_STONE = 
			BLOCKS.register("chiseled_dark_antique_stone", 
					() -> new ChiseledDarkAntiqueStone(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(3f,  5f)));
	public static final RegistryObject<Block> URN = 
			BLOCKS.register("urn", 
					() -> new Urn(
							Block.Properties.of(Material.GLASS, MaterialColor.STONE)
									.strength(0f,  0f)
									.dynamicShape()
									.noOcclusion()
									.sound(SoundType.GLASS)));
	public static final RegistryObject<Block> EMPTY_URN = 
			BLOCKS.register("empty_urn", 
					() -> new EmptyUrn(
							Block.Properties.of(Material.GLASS, MaterialColor.STONE)
									.strength(0f,  0f)
									.dynamicShape()
									.noOcclusion()
									.sound(SoundType.GLASS)));
	public static final RegistryObject<Block> CRYSTAL = 
			BLOCKS.register("crystal", 
					() -> new Crystal(
							Block.Properties.of(Material.ICE, MaterialColor.STONE)
									.strength(1f,  1f)
									.dynamicShape()
									.noOcclusion()
									.sound(SoundType.GLASS)));
	public static final RegistryObject<Block> IRON_SPIKES =
			BLOCKS.register("iron_spikes",
					() -> new SpikesBlock(
							Block.Properties.of(Material.HEAVY_METAL, MaterialColor.STONE)
									.strength(4f,  6f)
									.dynamicShape()
									.noOcclusion()
									.noCollission()
									.requiresCorrectToolForDrops()
									.sound(SoundType.METAL),10f));
	public static final RegistryObject<Block> CRACKED_DARK_ANTIQUE_STONE = 
			BLOCKS.register("cracked_dark_antique_stone", 
					() -> new CrackedStone(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(3f,  5f),
							SoundEvents.BASALT_BREAK));
	public static final RegistryObject<Block> DARK_ANTIQUE_GRAVEL = 
			BLOCKS.register("dark_antique_gravel", 
					() -> new DarkAntiqueGravel(
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.sound(SoundType.GRAVEL)
									.strength(2f,  5f)));
	public static final RegistryObject<Block> DARK_ANTIQUE_PRESSURE_PLATE = 
			BLOCKS.register("dark_antique_stone_pressure_plate", 
					() -> new FullBlockPPlate(
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  5f)
									.noOcclusion(),
						SoundEvents.BASALT_BREAK,
						SoundEvents.BASALT_BREAK));
	public static final RegistryObject<Block> ANTIQUE_PEBBLE = 
			BLOCKS.register("antique_pebble", 
					() -> new AntiquePebble(
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  5f)
									.sound(SoundType.BASALT)
									.noOcclusion()));
	public static final RegistryObject<Block> ANTIQUE_PEBBLE_PRESSURE_PLATE = 
			BLOCKS.register("antique_pebble_pressure_plate", 
					() -> new AntiquePebblePPlate(
							PressurePlateBlock.Sensitivity.EVERYTHING,
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  5f)
									.sound(SoundType.BASALT)
									.noOcclusion()));
	public static final RegistryObject<Block> FLAME_THROWER = 
			BLOCKS.register("flame_thrower", 
					() -> new FlameThrower(
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  5f)));

	public static final RegistryObject<Block> DISGUISED_DISPENSER =
			BLOCKS.register("disguised_dispenser",
					() -> new DispenserBlock(
							Block.Properties.of(Material.SAND, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  5f)));
	public static final RegistryObject<Block> CLIMBING_HOOK = 
			BLOCKS.register("climbing_hook", 
					() -> new ClimbingHook(
							Block.Properties.of(Material.METAL, MaterialColor.STONE)
									.sound(SoundType.ROOTS)
									.strength(1f,  1f)
									.noOcclusion()
									.noCollission()));
	public static final RegistryObject<Block> CLIMBING_ROPE = 
			BLOCKS.register("climbing_rope", 
					() -> new ClimbingRope(
							Block.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN)
									.strength(0f,  0f)
									.noOcclusion()
									.sound(SoundType.ROOTS)
									.noCollission()));
	public static final RegistryObject<Block> QUICK_SAND = 
			BLOCKS.register("quick_sand", 
					() -> new QuickSand(
							Block.Properties.of(Material.SAND, MaterialColor.SAND)
									.requiresCorrectToolForDrops()
									.strength(2f,  2f)
									.sound(SoundType.SAND)
									.noCollission()));

	public static final RegistryObject<Block> DRIED_QUICK_SAND =
			BLOCKS.register("dried_quick_sand",
					() -> new DriedQuickSand(
							Block.Properties.of(Material.SAND, MaterialColor.SAND)
									.requiresCorrectToolForDrops()
									.strength(2f,  2f)
									.sound(SoundType.TUFF)));

	public static final RegistryObject<Block> POTTERY_BLOCK =
			BLOCKS.register("pottery_block",
					() -> new Block(
							Block.Properties.of(Material.SAND, MaterialColor.SAND)
									.requiresCorrectToolForDrops()
									.strength(1f,  2f)
									.sound(SoundType.MUD)));
	public static final RegistryObject<Block> ANTIQUE_STONE_CARPET =
			BLOCKS.register("antique_stone_carpet",
					() -> new Block(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.strength(2f,  2f)));
	public static final RegistryObject<Block> DARK_ANTIQUE_STONE_PILLAR =
			BLOCKS.register("dark_antique_stone_pillar",
					() -> new DarkAntiqueStonePillar(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.requiresCorrectToolForDrops()
									.noOcclusion()
									.strength(2f,  2f)));
	public static final RegistryObject<Block> ANCIENT_ROOTS =
			BLOCKS.register("ancient_roots",
					() -> new AncientRoots(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.strength(0f,  0f)
									.noOcclusion()
									.noCollission()
									.sound(SoundType.ROOTS)));
	public static final RegistryObject<Block> ROOTED_GROUND =
			BLOCKS.register("rooted_ground",
					() -> new RootedGround(
							Block.Properties.of(Material.STONE, MaterialColor.STONE)
									.strength(0f,  0f)
									.noOcclusion()
									.noCollission()
									.sound(SoundType.ROOTS)));

}
