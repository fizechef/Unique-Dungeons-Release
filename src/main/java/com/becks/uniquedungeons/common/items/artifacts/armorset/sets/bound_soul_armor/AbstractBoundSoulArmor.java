package com.becks.uniquedungeons.common.items.artifacts.armorset.sets.bound_soul_armor;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.armorset.ArmorSetItem;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItemArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractBoundSoulArmor extends ArtifactItemArmor implements ArmorSetItem {

    public static final String UUID_KEY = UniqueDungeons.MOD_ID + ".bound_soul";
    public static final String NAME_KEY = UniqueDungeons.MOD_ID + ".bound_soul_name";
    private static final Collection<DamageFaktorTupel> sources = new ArrayList<>();
    public AbstractBoundSoulArmor(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public Collection<DamageFaktorTupel> blocking(Player player){
        return sources;
    }

    protected boolean setBoundSoul(ItemStack stack, Player player, LivingEntity target, InteractionHand hand){
        if (target.getCommandSenderWorld().isClientSide || !target.isAlive()){
            return false;
        }
        ItemStack newStack = stack.copy();
        newStack.getOrCreateTag().putUUID(UUID_KEY, target.getUUID());
        newStack.getOrCreateTag().putString(NAME_KEY, target.getDisplayName().getString());
        ForgeChunkManager.forceChunk(((ServerLevel)player.level), UniqueDungeons.MOD_ID, target, target.chunkPosition().x, target.chunkPosition().z, true, false);
        player.setItemInHand(hand, newStack);
        return true;
    }

    protected boolean removeBoundSoul(UseOnContext context){
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (player == null){
            return false;
        }
        ItemStack stack = context.getItemInHand();
        if (level.isClientSide || !stack.getTag().contains(UUID_KEY)) {
            //System.out.println("fail");
            return false;
        }
        Entity entity = ((ServerLevel)level).getEntity(stack.getTag().getUUID(UUID_KEY));
        stack.getTag().remove(UUID_KEY);
        stack.getTag().remove(NAME_KEY);
        if (entity!= null){
            ForgeChunkManager.forceChunk(((ServerLevel)player.level), UniqueDungeons.MOD_ID, entity, entity.chunkPosition().x, entity.chunkPosition().z, false, false);
            //System.out.println("" + entity);
        }
        stack.setTag(null);
        return true;
    }

    @Override
    public Item getRepairItem() {
        return Items.RABBIT_HIDE;
    }


    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        Collection<Component> components = new ArrayList<Component>();
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            if (stack.getTag().contains(NAME_KEY)){
                components.add(Component.translatable("lore.item.uniquedungeons.bound_soul").withStyle(ChatFormatting.GREEN)
                        .append("" + stack.getTag().getString(NAME_KEY)));
            }
            components.add(Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE).append(getRepairItem().getName(getRepairItem().getDefaultInstance())));

        }
        return components;
    }
}
