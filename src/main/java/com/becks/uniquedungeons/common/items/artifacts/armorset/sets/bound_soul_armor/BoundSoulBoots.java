package com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor;

import com.becks.uniquedungeons.common.items.artifacts.armorset.ArmorSet;
import com.becks.uniquedungeons.common.items.artifacts.armorset.ArmorSetUtil;
import com.becks.uniquedungeons.core.init.ArmorMaterialInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.*;

public class BoundSoulBoots extends AbstractBoundSoulArmor {
    public BoundSoulBoots(Properties pProperties) {
        super(ArmorMaterialInit.BOUND_SOUL, EquipmentSlot.FEET, pProperties);
    }

    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        ArrayList<Component> components = (ArrayList<Component>) super.generateInfoLoreText(stack, level);

        if (Screen.hasShiftDown()){
            components.add(components.size()-1,Component.translatable("lore.item.uniquedungeons.bound_soul.boots").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    @Nonnull
    public InteractionResult useOn(UseOnContext context) {
        if (removeBoundSoul(context)){
            return InteractionResult.SUCCESS;
        }
        //System.out.println("Loading chunks? " + ForgeChunkManager.hasForcedChunks(((ServerLevel)player.level)));
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (setBoundSoul(stack, player, target, hand)){
            return InteractionResult.SUCCESS;
        }
        //System.out.println("Loading chunks? " + ForgeChunkManager.hasForcedChunks(((ServerLevel)player.level)));
        return InteractionResult.FAIL;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getTag().contains(UUID_KEY);
    }

    @Override
    public Collection<DamageFaktorTupel> blocking(Player player) {
        Collection<DamageFaktorTupel> sources = super.blocking(player);
        sources.add(new DamageFaktorTupel(DamageSource.FALL, 1.0f));
        sources.add(new DamageFaktorTupel(null, (ArmorSetUtil.playerWearingFullSet(player, this.getSet()))?0.0f:0.5f));
        return sources;
    }

    @Override
    public ArmorSet getSet() {
        return BoundSoulSet.getInstance();
    }
}
