package com.becks.uniquedungeons.mixin;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.LogicalSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean is(Item pItem);

    @Invoker("getHideFlags")
    public abstract int invoke$getHideFlags();

    @Invoker("shouldShowInTooltip")
    public abstract boolean invoke$shouldShowInTooltip(int j, ItemStack.TooltipPart additional);

    @Inject(method = "getTooltipLines", at = @At("HEAD"), cancellable = true)
    public void inject$getTooltipLines(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack thisItemStack = (ItemStack) (Object) this;
        if (thisItemStack.getItem() instanceof ArtifactItem) {
            if (!UnlockUtils.getUnlockData(UniqueDungeons.proxy.getPlayer(), LogicalSide.CLIENT).knows((ArtifactItem)thisItemStack.getItem())){
                List<Component> list = Lists.newArrayList();
                MutableComponent mutablecomponent = Component.empty().append(thisItemStack.getHoverName()).withStyle(thisItemStack.getRarity().getStyleModifier());
                if (thisItemStack.hasCustomHoverName()) {
                    mutablecomponent.withStyle(ChatFormatting.ITALIC);
                }

                list.add(mutablecomponent);
                if (!pIsAdvanced.isAdvanced() && !thisItemStack.hasCustomHoverName() && thisItemStack.is(Items.FILLED_MAP)) {
                    Integer integer = MapItem.getMapId(thisItemStack);
                    if (integer != null) {
                        list.add(Component.literal("#" + integer).withStyle(ChatFormatting.GRAY));
                    }
                }

                int j = this.invoke$getHideFlags();
                if (this.invoke$shouldShowInTooltip(j, ItemStack.TooltipPart.ADDITIONAL)) {
                    thisItemStack.getItem().appendHoverText(thisItemStack, pPlayer == null ? null : pPlayer.level, list, pIsAdvanced);
                }
                list.add(Component.translatable("lore.item.uniquedungeons.unknown").withStyle(ChatFormatting.DARK_GRAY));
                cir.setReturnValue(list);
            }
        }
    }
}
