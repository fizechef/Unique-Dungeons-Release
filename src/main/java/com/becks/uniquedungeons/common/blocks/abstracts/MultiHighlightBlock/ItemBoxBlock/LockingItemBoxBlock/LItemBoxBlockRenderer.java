package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.LockingItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBox;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlockEntity;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlockRenderer;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.MultiHighlightBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.becks.uniquedungeons.util.DirectionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public abstract class LItemBoxBlockRenderer extends ItemBoxBlockRenderer {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ItemBoxBlockEntity blockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn){
        //System.out.println("hello from render");
        super.render(blockEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        SelectionBox b = ((LockingItemBoxBlock)(blockEntity.getBlockState().getBlock())).getLockItemBox();
        BlockState state = blockEntity.getBlockState();
        Rotation relativeRotation = DirectionHelper.getRotation(state.getValue(MultiHighlightBlock.FACING), Direction.SOUTH);
        Vector3d blockMiddleOffset = new Vector3d(-8,-8,-8);
        if (blockEntity.getItemType(((ItemBox)b).getSlot()).equals(ItemStack.EMPTY) || blockEntity.getItemType(((ItemBox)b).getSlot()).equals(Items.AIR)){

        }
        else {
            int lightLevel = getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above());
            ItemStack stack = blockEntity.getItem(((ItemBox)b).getSlot());
            Vector3d translation = new Vector3d(((ItemBox) b).getRenderTranslation(stack)[0], ((ItemBox) b).getRenderTranslation(stack)[1], ((ItemBox) b).getRenderTranslation(stack)[2]);
            translation.scale(16.0);
            translation.add(blockMiddleOffset);
            translation = DirectionHelper.getRotatedVec(translation, relativeRotation);
            blockMiddleOffset.scale(-1);
            translation.add(blockMiddleOffset);
            translation.scale(1.0/16.0);
            Quaternion relativeQuat = ((ItemBox) b).getRenderRotation();
            relativeQuat.mul(Vector3f.YP.rotationDegrees(DirectionHelper.getRotationDegrees(relativeRotation)));
            super.renderItem(stack, new double[]{translation.x, translation.y, translation.z},
                    relativeQuat, matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, ((ItemBox) b).getRenderScale());
        }
    }
}
