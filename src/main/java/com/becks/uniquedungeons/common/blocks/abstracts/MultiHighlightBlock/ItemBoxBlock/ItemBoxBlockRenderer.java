package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.MultiHighlightBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.becks.uniquedungeons.util.DirectionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ItemBoxBlockRenderer implements BlockEntityRenderer<ItemBoxBlockEntity> {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ItemBoxBlockEntity blockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn){
        ItemBoxBlock block = (ItemBoxBlock)(blockEntity.getBlockState().getBlock());
        BlockState state = blockEntity.getBlockState();
        Rotation relativeRotation = DirectionHelper.getRotation(state.getValue(MultiHighlightBlock.FACING), Direction.SOUTH);
        Vector3d blockMiddleOffset = new Vector3d(-8,-8,-8);
        for (SelectionBox b : block.getItemBoxes()){
            //System.out.println("Rendering Slot " + b.getSlot() + " with Item: " + blockEntity.getItem(b.getSlot()));
            if (!(blockEntity.getItemType(((ItemBox)b).getSlot()).equals(Items.AIR))){
                //System.out.println("Rendering " + blockEntity.getItem(((ItemBox)b).getSlot()).getDefaultInstance());
                int lightLevel = getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above());
                ItemStack stack = blockEntity.getItem(((ItemBox)b).getSlot());
                Vector3d translation = new Vector3d(((ItemBox) b).getRenderTranslation(stack)[0], ((ItemBox) b).getRenderTranslation(stack)[1], ((ItemBox) b).getRenderTranslation(stack)[2]);
                translation.scale(16);
                translation.add(blockMiddleOffset);
                translation = DirectionHelper.getRotatedVec(translation, relativeRotation);
                blockMiddleOffset.scale(-1);
                translation.add(blockMiddleOffset);
                translation.scale(1.0/16.0);
                Quaternion relativeQuat = ((ItemBox) b).getRenderRotation();
                relativeQuat.mul(Vector3f.ZP.rotationDegrees(DirectionHelper.getRotationDegrees(relativeRotation)));
                //System.out.println(translation.x + " " + translation.y + " " + translation.z + " " + relativeRotation);
                renderItem(stack, new double[]{translation.x, translation.y, translation.z},
                        relativeQuat, matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, ((ItemBox) b).getRenderScale());
            }
        }
    }

    protected void renderItem(ItemStack stack, double[] translation, Quaternion rotation, PoseStack matrixStack,
                            MultiBufferSource buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
        //System.out.println("Rendering Item at " + translation[0] + " " + translation[1] + " " + translation[2] + " at scale " + scale);
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);

        BakedModel model = mc.getItemRenderer().getModel(stack, null, null, 1);
        mc.getItemRenderer().render(stack, ItemTransforms.TransformType.GROUND, true, matrixStack, buffer,
                lightLevel, combinedOverlay, model);
        matrixStack.popPose();
    }

    protected int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
