package com.becks.uniquedungeons.core.itemgroup;

import com.becks.uniquedungeons.core.init.itemInit.GeneralItemInit;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * siehe UniqueDungeonsBlocks.class
 * @author erike
 *
 */
public class UniqueDungeonsItems extends CreativeModeTab {
	public static final UniqueDungeonsItems ITEM_GROUP = new UniqueDungeonsItems(CreativeModeTab.TABS.length, "Unique Dungeons Items");

	public UniqueDungeonsItems(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(GeneralItemInit.CERAMIC_SHARD.get());
	}
}
