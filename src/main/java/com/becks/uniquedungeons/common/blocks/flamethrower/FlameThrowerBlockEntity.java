package com.becks.uniquedungeons.common.blocks.flamethrower;

import com.becks.uniquedungeons.core.init.BlockEntityInit;
import com.becks.uniquedungeons.core.init.BlockInit;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

import static com.becks.uniquedungeons.common.blocks.flamethrower.FlameThrower.FACING;
import static com.becks.uniquedungeons.common.blocks.flamethrower.FlameThrower.POWERED;

public class FlameThrowerBlockEntity extends BlockEntity {

    private static final Random rand = new Random();

    protected static final AABB BURN_XPLUS = new AABB(0.0D, 0.0D, 0.0D, 3.0D, 1.0D, 1.0D);
    protected static final AABB BURN_XMINUS = new AABB(0.0D, 0.0D, 0.0D, -2.0D, 1.0D, 1.0D);
    protected static final AABB BURN_YPLUS = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 3.0D, 1.0D);
    protected static final AABB BURN_YMINUS = new AABB(0.0D, 0.0D, 0.0D, 1.0D, -2.0D, 1.0D);
    protected static final AABB BURN_ZPLUS = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 3.0D);
    protected static final AABB BURN_ZMINUS = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -2.0D);


    public FlameThrowerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.FLAME_THROWER.get(), p_155229_, p_155230_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FlameThrowerBlockEntity blockEntity) {

        AABB axisalignedbb = null;
        if (level.getBlockState(pos).getBlock() != BlockInit.FLAME_THROWER.get()){
            //System.out.println(pos + " " + level.getBlockState(pos));
            //System.out.println("Loading error catch");
            return;
        }
        switch (level.getBlockState(pos).getValue(FACING)) {
            case NORTH:
                axisalignedbb = BURN_ZMINUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            case SOUTH:
                axisalignedbb = BURN_ZPLUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            case EAST:
                axisalignedbb = BURN_XPLUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            case WEST:
                axisalignedbb = BURN_XMINUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            case UP:
                axisalignedbb = BURN_YPLUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            case DOWN:
                axisalignedbb = BURN_YMINUS.move(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                break;
            default:
                break;
        }

        if (!level.isClientSide() && level.getBlockState(pos).getValue(POWERED)) {
            List<? extends Entity> list = level.getEntities((Entity)null, axisalignedbb);
            //System.out.println("checking");
            for (Entity e : list) {
                //System.out.println("Found " + e.toString());
                e.setSecondsOnFire(15);
            }
        }
        if (level.isClientSide()){

            double MotionMultiplier = (rand.nextDouble() * 0.5) + 1;
            double MotionOffset = (rand.nextDouble() * 0.04) - 0.02;

            double xMotion = 0;
            double yMotion = 0;
            double zMotion = 0;

            if (level.getBlockState(pos).getValue(POWERED)) {
                switch (level.getBlockState(pos).getValue(FACING)) {
                    case EAST:
                        xMotion = 0.15 * MotionMultiplier;
                        yMotion = MotionOffset;
                        zMotion = MotionOffset;
                        break;
                    case WEST:
                        xMotion = -0.15 * MotionMultiplier;
                        yMotion = MotionOffset;
                        zMotion = MotionOffset;
                        break;
                    case SOUTH:
                        xMotion = MotionOffset;
                        yMotion = MotionOffset;
                        zMotion = 0.15 * MotionMultiplier;
                        break;
                    case NORTH:
                        xMotion = MotionOffset;
                        yMotion = MotionOffset;
                        zMotion = -0.15 * MotionMultiplier;
                        break;
                    case UP:
                        xMotion = MotionOffset;
                        yMotion = 0.15 * MotionMultiplier;
                        zMotion = MotionOffset;
                        break;
                    case DOWN:
                        xMotion = MotionOffset;
                        yMotion = -0.15 * MotionMultiplier;
                        zMotion = MotionOffset;
                        break;
                    default:
                        break;
                }
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
                level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
                level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
                level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
            }
        }

    }
}
