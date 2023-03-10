package com.becks.uniquedungeons.common.items.artifacts;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItemSword;
import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;

public class BloodSword extends ArtifactItemSword {

    public BloodSword(Properties pProperties) {
        super(Tiers.DIAMOND, 1, 1, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        boolean hurtSuper = super.hurtEnemy(pStack, pTarget, pAttacker);
        if (hurtSuper){
            if (pTarget instanceof TamableAnimal && ((TamableAnimal)pTarget).getOwner().is(pAttacker)){
                //System.out.println("Collecting Blood");
                addBlood(pStack, (int)getAttributeModifiers(EquipmentSlot.MAINHAND, pStack).get(Attributes.ATTACK_DAMAGE).iterator().next().getAmount());
            }
        }
        return hurtSuper;
    }

    public void addBlood(ItemStack stack, int amount)
    {
        if(!stack.is(ArtifactInit.BLOOD_SWORD.get())){
            return;
        }
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(UniqueDungeons.MOD_ID + ".blood")) {
             int blood = nbtTagCompound.getInt(UniqueDungeons.MOD_ID + ".blood");
             blood+=amount;
             nbtTagCompound.remove(UniqueDungeons.MOD_ID + ".blood");
             nbtTagCompound.putInt(UniqueDungeons.MOD_ID + ".blood", blood);
             stack.setTag(nbtTagCompound);
             return;
        }
        else if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
        }
        nbtTagCompound.putInt(UniqueDungeons.MOD_ID + ".blood", 0);
        stack.setTag(nbtTagCompound);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> defaultMap = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            defaultMap.forEach((a, am) -> putHelperMethod(a, am, builder, stack));
            return builder.build();
        }
        return defaultMap;
    }

    private void putHelperMethod(Attribute a, AttributeModifier am, ImmutableMultimap.Builder<Attribute, AttributeModifier> builder, ItemStack stack){
        if (a.equals(Attributes.ATTACK_DAMAGE)){
            assert stack.getTag() != null;
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", am.getAmount() * getBloodModifier(stack), AttributeModifier.Operation.ADDITION));
        }
        else {
            builder.put(a,am);
        }
    }

    private double getBloodModifier(ItemStack stack){
        double scale = Math.pow(10, 3);
        double value = (1 + (Math.sqrt(stack.getTag().getInt(UniqueDungeons.MOD_ID + ".blood")) / 10.0));
        return Math.round(value * scale) / scale;
    }

    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        Collection<Component> components = new ArrayList<Component>();
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound == null || !nbtTagCompound.contains(UniqueDungeons.MOD_ID + ".blood")) {
            addBlood(stack, 0);
        }
        assert nbtTagCompound != null;
        components.add(Component.translatable("lore.item.uniquedungeons.blood_sword.blood").withStyle(ChatFormatting.DARK_RED)
                .append(Component.literal("" + nbtTagCompound.getInt(UniqueDungeons.MOD_ID + ".blood")).withStyle(ChatFormatting.DARK_RED)));
        components.add(Component.translatable("lore.item.uniquedungeons.blood_sword.damage_modifier").withStyle(ChatFormatting.DARK_RED)
                .append(Component.literal("" + getBloodModifier(stack)).withStyle(ChatFormatting.DARK_RED)));
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.blood_sword.lore").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE).append(getRepairItem().getName(getRepairItem().getDefaultInstance())));
        }
        return components;
    }

    @Override
    public Item getRepairItem() {
        return Items.ECHO_SHARD;
    }
}
