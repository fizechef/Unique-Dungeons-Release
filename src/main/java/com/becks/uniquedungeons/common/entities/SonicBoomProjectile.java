package com.becks.uniquedungeons.common.entities;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;

public class SonicBoomProjectile extends Projectile {

    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(SonicBoomProjectile.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(SonicBoomProjectile.class, EntityDataSerializers.BYTE);

    private static final float DAMAGE = 15f;

    private static final float MAX_HEALTH_DAMAGE = 0.5f;

    private Vec3 shotFrom = new Vec3(0,0,0);

    @Nullable
    private IntOpenHashSet piercingIgnoreEntityIds = new IntOpenHashSet(5);

    private static final int LIFE_TIME = 100;

    private int timeAlive = 0;

    public SonicBoomProjectile(EntityType<SonicBoomProjectile> pEntityType, LivingEntity pShooter, Level pLevel, Vec3 shotFrom) {
        super(pEntityType, pLevel);
        this.setOwner(pShooter);
        this.shotFrom = shotFrom;
    }

    public SonicBoomProjectile(EntityType<SonicBoomProjectile> amethystProjectileEntityType, Level level) {
        super(amethystProjectileEntityType, level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ID_FLAGS, (byte)0);
        this.entityData.define(PIERCE_LEVEL, (byte)0);
    }

    @Override
    public void tick() {
        //System.out.println("ticking sonic boom projectile");
        if (level instanceof ServerLevel){
            ((ServerLevel)level).sendParticles(ParticleTypes.SONIC_BOOM, shotFrom.x, shotFrom.y, shotFrom.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            ((ServerLevel)level).sendParticles(ParticleTypes.BUBBLE, this.position().x, this.position().y, this.position().z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        timeAlive++;
        if (timeAlive >= LIFE_TIME){
            this.remove(RemovalReason.KILLED);
            return;
        }
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.remove(RemovalReason.KILLED);
                        return;
                    }
                }
            }
        }

        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
            this.clearFire();
        }
        Vec3 vec32 = this.position();
        Vec3 vec33 = vec32.add(vec3);
        HitResult hitresult = this.level.clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec33 = hitresult.getLocation();
        }

        EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
        while (entityhitresult != null) {
            entityhitresult.getEntity();
            hitresult = entityhitresult;

            if (hitresult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)hitresult).getEntity();
                Entity entity1 = this.getOwner();
                if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                    hitresult = null;
                    entityhitresult = null;
                }
            }

            if (hitresult != null && hitresult.getType() != HitResult.Type.MISS  && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                //System.out.println("hit");
                this.onHit(hitresult);
                this.hasImpulse = true;
            }
            entityhitresult = this.findHitEntity(vec32, vec33);
        }


        vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;
            /*for(int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.CRIT, this.getX() + d5 * (double)i / 4.0D, this.getY() + d6 * (double)i / 4.0D, this.getZ() + d1 * (double)i / 4.0D, -d5, -d6 + 0.2D, -d1);
            }*/

        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        this.setPos(d7, d2, d3);
        this.checkInsideBlocks();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level, this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float f = (float)this.getDeltaMovement().length();


        if (this.piercingIgnoreEntityIds == null) {
            this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
        }

        this.piercingIgnoreEntityIds.add(entity.getId());

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.sonicBoom(entity1);
        } else {
            damagesource = DamageSource.sonicBoom(entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }
        boolean hurt = false;

        if (entity instanceof LivingEntity){
            hurt = entity.hurt(damagesource, Math.max((((LivingEntity)entity).getMaxHealth() * MAX_HEALTH_DAMAGE) + 2, DAMAGE));
        }
        else if (entity instanceof PartEntity){
            Entity entityParent = ((PartEntity)entity).getParent();
            if (entityParent instanceof LivingEntity){
                hurt = entity.hurt(damagesource, Math.max((((LivingEntity)entityParent).getMaxHealth() * MAX_HEALTH_DAMAGE) + 2, DAMAGE));
            }
        }
        if (hurt) {
            level.playSound(null, new BlockPos(shotFrom), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 5.0F, 1.0F);
            if (level instanceof ServerLevel){
                //System.out.println("Particle send");
                Vec3 particleVec = pResult.getLocation().add(shotFrom.scale(-1.0));
                for(int i = 1; i < Mth.floor(particleVec.length()) + 7; ++i) {
                    Vec3 displayVec3 = shotFrom.add(particleVec.normalize().scale((double)i));
                    ((ServerLevel)level).sendParticles((ServerPlayer) entity1, ParticleTypes.SONIC_BOOM, true, displayVec3.x, displayVec3.y, displayVec3.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    ((ServerLevel)level).sendParticles(ParticleTypes.SONIC_BOOM, displayVec3.x, displayVec3.y, displayVec3.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
                }

                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

            }

            //this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }

    }

    @Override
    protected boolean canHitEntity(Entity p_36743_) {
        return super.canHitEntity(p_36743_) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(p_36743_.getId()));
    }
}
