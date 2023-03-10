package com.becks.uniquedungeons.common.items.artifacts;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItemSword;
import com.becks.uniquedungeons.common.stackCooldowns.StackCooldownItem;
import com.becks.uniquedungeons.common.stackCooldowns.StackCooldownMap;
import com.becks.uniquedungeons.common.stackCooldowns.StackCooldowns;
import com.becks.uniquedungeons.common.stackCooldowns.StackWrapper;
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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class RageBlade extends ArtifactItemSword implements StackCooldownItem {

    private static final String STACK_KEY = UniqueDungeons.MOD_ID + ".stacks";


    public RageBlade(Properties pProperties) {
        super(Tiers.DIAMOND, 1, -3.5f, pProperties);
    }

    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        Collection<Component> components = new ArrayList<Component>();
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound == null || !nbtTagCompound.contains(STACK_KEY)) {
            removeStacks(stack);
        }
        assert nbtTagCompound != null;
        components.add(Component.translatable("lore.item.uniquedungeons.rage_blade.stacks").withStyle(ChatFormatting.GOLD)
                .append(Component.literal("" + nbtTagCompound.getInt(STACK_KEY)).withStyle(ChatFormatting.GOLD)));
        components.add(Component.translatable("lore.item.uniquedungeons.rage_blade.damage_modifier").withStyle(ChatFormatting.GOLD)
                .append(Component.literal("" + getStackDamageModifier(stack)).withStyle(ChatFormatting.GOLD)));
        components.add(Component.translatable("lore.item.uniquedungeons.rage_blade.speed_modifier").withStyle(ChatFormatting.GOLD)
                .append(Component.literal("" + getStackSpeedModifier(stack)).withStyle(ChatFormatting.GOLD)));
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.rage_blade.lore").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE).append(getRepairItem().getName(getRepairItem().getDefaultInstance())));
        }
        return components;
    }

    @Override
    public Item getRepairItem() {
        return Items.ANCIENT_DEBRIS;
    }

    private double getStackSpeedModifier(ItemStack stack){
        double scale = Math.pow(10, 3);
        double value = Math.sqrt(0.5*(stack.getTag().getInt(STACK_KEY)));
        return Math.round(value * scale) / scale;
    }

    private double getStackDamageModifier(ItemStack stack){
        double scale = Math.pow(10, 3);
        double value = (1 + (Math.sqrt(stack.getTag().getInt(STACK_KEY)) / 10.0));
        return Math.round(value * scale) / scale;
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
        if (a.equals(Attributes.ATTACK_SPEED)){
            assert stack.getTag() != null;
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", am.getAmount() + getStackSpeedModifier(stack), AttributeModifier.Operation.ADDITION));
        }
        else if (a.equals(Attributes.ATTACK_DAMAGE)){
            assert stack.getTag() != null;
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", am.getAmount() * getStackDamageModifier(stack), AttributeModifier.Operation.ADDITION));
        }
        else {
            builder.put(a,am);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        boolean hurtSuper = super.hurtEnemy(pStack, pTarget, pAttacker);
        if (hurtSuper && !pTarget.getLevel().isClientSide){
           //System.out.println("Adding Cooldown Rageblade");
            StackCooldowns cooldowns = StackCooldownMap.getCooldowns(((Player)pAttacker));
            if (cooldowns != null) {
                cooldowns.addCooldown(pStack, 100, false);
                addStack(pStack);
            }
        }
        return hurtSuper;
    }

    @Override
    public String getCooldownID(ItemStack stack) {
        String id = "";
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(COOLDOWN_KEY)) {
            id = nbtTagCompound.getString(COOLDOWN_KEY);
            return id;
        }
        else if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
        }
        id = UUID.randomUUID().toString();
        nbtTagCompound.putString(COOLDOWN_KEY, id);
        stack.setTag(nbtTagCompound);
        return id;
    }

    public void addStack(ItemStack stack)
    {
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(STACK_KEY)) {
            int stacks = nbtTagCompound.getInt(STACK_KEY);
            stacks++;
            nbtTagCompound.remove(STACK_KEY);
            nbtTagCompound.putInt(STACK_KEY, stacks);
            stack.setTag(nbtTagCompound);
            return;
        }
        else if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
        }
        nbtTagCompound.putInt(STACK_KEY, 1);
        stack.setTag(nbtTagCompound);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        removeStacks(item);
        return super.onDroppedByPlayer(item, player);
    }

    public void removeStacks(ItemStack stack)
    {
       //System.out.println("Removing Stacks from Rageblade");
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(STACK_KEY)) {
            nbtTagCompound.remove(STACK_KEY);
            nbtTagCompound.putInt(STACK_KEY, 0);
            stack.setTag(nbtTagCompound);
            return;
        }
        else if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
        }
        nbtTagCompound.putInt(STACK_KEY, 0);
        stack.setTag(nbtTagCompound);
    }

    @Override
    public void onCooldownEnd(Player player, ItemStack stack) {
        for (ItemStack s : player.getInventory().items){
            if (s.getItem() instanceof StackCooldownItem){
                if ((new StackWrapper(stack).equals(new StackWrapper(s)))){
                    removeStacks(s);
                }
            }
        }
    }
}
