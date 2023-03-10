package com.becks.uniquedungeons.common.items.artifacts.abstracts;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ArtifactItemArmor extends ArmorItem implements ArtifactItem{
    public ArtifactItemArmor(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        if (UnlockUtils.getUnlockData(UniqueDungeons.proxy.getPlayer(), LogicalSide.CLIENT).knows((ArtifactItem)pStack.getItem())){
            return super.getName(pStack);
        }
        else {
            return Component.translatable("lore.item.uniquedungeons.unknown").withStyle(ChatFormatting.GRAY);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (UnlockUtils.getUnlockData(UniqueDungeons.proxy.getPlayer(), LogicalSide.CLIENT).knows((ArtifactItem)stack.getItem())){
            components.addAll(this.generateInfoLoreText(stack, level));
            super.appendHoverText(stack, level, components, flag);
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.unknown").withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(getRepairItem());
    }
}
