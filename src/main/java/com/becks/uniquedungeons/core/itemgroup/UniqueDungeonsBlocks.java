package com.becks.uniquedungeons.core.itemgroup;

import com.becks.uniquedungeons.core.init.itemInit.GeneralItemInit;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Klasse f�r Creativtab Bl�cke
 * @author erike
 *
 */
public class UniqueDungeonsBlocks extends CreativeModeTab {
	
	public static final UniqueDungeonsBlocks BLOCK_GROUP = new UniqueDungeonsBlocks(CreativeModeTab.TABS.length, "Unique Dungeons Blocks");

	/**
	 * Konstruktor
	 * @param index an welcher Stelle im Inventar der Tab angezeigt wird, w�hle dynamisch um Konflikte zu vermeiden
	 * @param label Name des Tabs
	 */
	public UniqueDungeonsBlocks(int index, String label) {
		super(index, label);
	}

	/**
	 * Legt fest welches Item bzw. BlockItem als Icon f�r den Tab verwendet wird
	 */
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(GeneralItemInit.URN.get());
	}

}
