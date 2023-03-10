package com.becks.uniquedungeons.common.items;

import com.becks.uniquedungeons.common.entities.ClimbingHookProjectile;
import com.becks.uniquedungeons.common.entities.CrystalArrow;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrystalArrowItem extends ArrowItem {
    public CrystalArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public CrystalArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
        return new CrystalArrow(p_200887_1_, p_200887_3_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.crystal_shard.lore").withStyle(ChatFormatting.WHITE));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}
