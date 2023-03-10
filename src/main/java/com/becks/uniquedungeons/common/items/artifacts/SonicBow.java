package com.becks.uniquedungeons.common.items.artifacts;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import com.becks.uniquedungeons.common.entities.SonicBoomProjectile;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItemBow;
import com.becks.uniquedungeons.core.init.EntityInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class SonicBow extends ArtifactItemBow {

    public static final Predicate<ItemStack> ECHO_SHARD = (p_43017_) -> {
        return p_43017_.is(Items.ECHO_SHARD);
    };
    public SonicBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ECHO_SHARD;
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            ItemStack itemstack = this.getProjectile(player, pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty());
            if (i < 0) return;

            if (!itemstack.isEmpty()) {
                if (i >= 20){
                    Vec3 pos = player.getEyePosition();
                    if (!pLevel.isClientSide) {
                        Vec3 look = player.getLookAngle().normalize();
                        //System.out.println(look);
                        SonicBoomProjectile arrow = new SonicBoomProjectile(EntityInit.SONIC_BOOM_PROJECTILE.get(), (LivingEntity)player, pLevel, pos);
                        arrow.setPos(pos.x +  look.scale(-0.1).x, pos.y +  look.scale(-0.1).y, pos.z +  look.scale(-0.1).z);
                        arrow.setDeltaMovement(look.scale(2.5));
                        pLevel.addFreshEntity(arrow);
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                        pStack.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                    }
                    pLevel.playSound(null, new BlockPos(pos), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 5.0F, 1.0F);
                }
            }
        }
    }

    public ItemStack getProjectile(Player player, ItemStack pShootable) {
        if (!(pShootable.getItem() instanceof ProjectileWeaponItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)pShootable.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(player, predicate);
            if (!itemstack.isEmpty()) {
                return net.minecraftforge.common.ForgeHooks.getProjectile(player, pShootable, itemstack);
            } else {
                predicate = ((ProjectileWeaponItem)pShootable.getItem()).getAllSupportedProjectiles();

                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack1 = player.getInventory().getItem(i);
                    if (predicate.test(itemstack1)) {
                        return net.minecraftforge.common.ForgeHooks.getProjectile(player, pShootable, itemstack1);
                    }
                }

                return net.minecraftforge.common.ForgeHooks.getProjectile(player, pShootable, player.getAbilities().instabuild ? new ItemStack(Items.ECHO_SHARD) : ItemStack.EMPTY);
            }
        }
    }

    @Override
    public Collection<Component> generateInfoLoreText(ItemStack stack, Level level) {
        Collection<Component> components = new ArrayList<Component>();
        CompoundTag nbtTagCompound = stack.getTag();
        if (!Screen.hasShiftDown()){
            components.add(Component.translatable("lore.item.uniquedungeons.shift_for_more").withStyle(ChatFormatting.DARK_GRAY));
        }
        else {
            components.add(Component.translatable("lore.item.uniquedungeons.sonic_bow.lore").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("lore.item.uniquedungeons.repair").withStyle(ChatFormatting.WHITE).append(getRepairItem().getName(getRepairItem().getDefaultInstance())));
        }
        return components;
    }

    @Override
    public Item getRepairItem() {
        return Items.ECHO_SHARD;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
