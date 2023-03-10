package com.becks.uniquedungeons.client.render;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.MultiHighlightBlock;
import com.becks.uniquedungeons.core.init.EntityInit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;

public class ClientRenderEventHandler {
    public static void onHighlightBlockEvent(RenderHighlightEvent event)
    {
        final Camera camera = event.getCamera();
        final PoseStack poseStack = event.getPoseStack();
        final Entity entity = camera.getEntity();
        final Level level = entity.level;
        final BlockHitResult hit =  (event.getTarget() instanceof BlockHitResult) ? (BlockHitResult)(event.getTarget()) : null;
        final BlockPos pos = (hit != null) ? hit.getBlockPos() : null;
        final BlockPos lookingAt = (pos != null) ? new BlockPos(pos) : null;

        //noinspection ConstantConditions
        if (lookingAt != null && entity instanceof Player player)
        {
            BlockState stateAt = level.getBlockState(lookingAt);
            Block blockAt = stateAt.getBlock();

            if (blockAt instanceof MultiHighlightBlock multiHiBlockAt)
            {
                // Pass on to custom implementations
                if (multiHiBlockAt.drawHighlight(level, lookingAt, player, hit, poseStack, event.getMultiBufferSource(), camera.getPosition()))
                {
                    // Cancel drawing this block's bounding box
                    event.setCanceled(true);
                }
            }
        }
    }
}
