package com.becks.uniquedungeons.common.items.artifacts;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import com.becks.uniquedungeons.common.entities.AmethystProjectile;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItemShield;
import com.becks.uniquedungeons.core.init.EntityInit;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class AmethystShield extends ArtifactItemShield {
    public AmethystShield(Properties pProperties) {super(pProperties);}

    private static final String DAMAGE_STACKS_KEY = UniqueDungeons.MOD_ID + ".damageStacks";

    private static final Function<Float, Float> damageMultiplierFunction = (m) -> 1.0f + (m * 0.125f);

    private static final int MAX_DAMAGE_STACK = 10;

    private static final double SPREAD = 1.0;

    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        Collection<Component> components = new ArrayList<Component>();
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound == null || !nbtTagCompound.contains(UniqueDungeons.MOD_ID + ".blood")) {
            addDamegeStacks(stack, 0);
        }
        components.add(Component.translatable("lore.item.uniquedungeons.amethyst_shield.damage_stacks").withStyle(ChatFormatting.DARK_RED)
                .append(Component.literal("" + nbtTagCompound.getInt(DAMAGE_STACKS_KEY)).withStyle(ChatFormatting.DARK_RED)));
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.amethyst_shield.lore").withStyle(ChatFormatting.GRAY));
            // MutableComponent repairItems = Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE);
            components.add(Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE).append(getRepairItem().getName(getRepairItem().getDefaultInstance())));
            // components.add(repairItems);
        }
        return components;
    }

    @Override
    public Item getRepairItem() {
        return Items.AMETHYST_SHARD;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        castProjectiles(pStack, pLivingEntity, pLevel);
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    private boolean castProjectiles(ItemStack stack, Entity source, Level level){
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(DAMAGE_STACKS_KEY)) {
            Random random = new Random();
            int currentStacks = nbtTagCompound.getInt(DAMAGE_STACKS_KEY);
            Vec3 look = source.getLookAngle();
            look.normalize();
            //System.out.println(look);
            Vec3 pos = source.getEyePosition().add(0,-0.75,0);
            for (int i = currentStacks; i > 0; i--){
                Vec3 deltaOffset = new Vec3((random.nextDouble()-0.5) * SPREAD, (random.nextDouble()-0.5) * SPREAD, (random.nextDouble()-0.5) * SPREAD);
                AmethystProjectile arrow = new AmethystProjectile(EntityInit.AMETHYST_PROJECTILE.get(), (LivingEntity)source, level, damageMultiplierFunction.apply((float) currentStacks));
                arrow.setPos(pos.x +  look.scale(-0.1).x, pos.y +  look.scale(-0.1).y, pos.z +  look.scale(-0.1).z);
                arrow.setDeltaMovement(look.scale(1.5).add(deltaOffset));
                level.addFreshEntity(arrow);
            }
            nbtTagCompound.remove(DAMAGE_STACKS_KEY);
            nbtTagCompound.putInt(DAMAGE_STACKS_KEY, 0);
            stack.setTag(nbtTagCompound);
            return true;
        }
        addDamegeStacks(stack, 0);
        return false;
    }

    public void addDamegeStacks(ItemStack stack, int damageStacks) {
        CompoundTag nbtTagCompound = stack.getTag();
        if (nbtTagCompound != null && nbtTagCompound.contains(DAMAGE_STACKS_KEY)) {
            int currentStacks = nbtTagCompound.getInt(DAMAGE_STACKS_KEY);
            damageStacks = Math.min(currentStacks+damageStacks, MAX_DAMAGE_STACK);
            nbtTagCompound.remove(DAMAGE_STACKS_KEY);
            nbtTagCompound.putInt(DAMAGE_STACKS_KEY, damageStacks);
            stack.setTag(nbtTagCompound);
            return;
        }
        else if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
        }
        nbtTagCompound.putInt(DAMAGE_STACKS_KEY, 0);
        stack.setTag(nbtTagCompound);
    }
}
