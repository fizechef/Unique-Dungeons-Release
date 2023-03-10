package com.becks.uniquedungeons.common.items;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Note extends Item{

	private static String KEY = UniqueDungeons.MOD_ID + ".artifact";
	public Note(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
		String artifactString = this.getArtifact(itemstack);
		if (artifactString != null && !artifactString.equals("")){
			ArtifactItem artifact = ArtifactInit.lookUp(artifactString);
			if (artifact != null){
				UnlockUtils.unlockArtifact(pPlayer, artifact);
				pLevel.playSound(null, new BlockPos(pPlayer.getEyePosition()), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 5.0F, 1.0F);
			}
		}
		return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
	}

	private void setArtifact(ItemStack stack, ArtifactItem artifact)
	{
		String artifactString = artifact.toString();
		CompoundTag nbtTagCompound = stack.getTag();
		if (nbtTagCompound != null && nbtTagCompound.contains(KEY)) {
			return;
		}
		if (nbtTagCompound == null){
			nbtTagCompound = new CompoundTag();
		}
		nbtTagCompound.putString(KEY, artifactString);
		stack.setTag(nbtTagCompound);
	}

	private String getArtifact(ItemStack stack){
		CompoundTag nbtTagCompound = stack.getTag();
		if (nbtTagCompound != null && nbtTagCompound.contains(KEY)) {
			return nbtTagCompound.getString(KEY);
		}
		/*ArtifactItem randomArtifact = ArtifactInit.getRandom();
		setArtifact(stack, randomArtifact);
		return randomArtifact.toString();*/
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
		if (this.getArtifact(stack) != null){
			components.add(Component.translatable("item.uniquedungeons." + this.getArtifact(stack)).withStyle(
					UnlockUtils.getUnlockData(UniqueDungeons.proxy.getPlayer(), LogicalSide.CLIENT).knows(ArtifactInit.lookUp(this.getArtifact(stack)))
							?
							ChatFormatting.GREEN
							:
							ChatFormatting.RED
			));
		}
		if (!Screen.hasShiftDown()){
			components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
		}
		else {
			components.add(Component.translatable("lore.item.uniquedungeons.note").withStyle(ChatFormatting.WHITE));
		}
		super.appendHoverText(stack, level, components, flag);
	}
}
