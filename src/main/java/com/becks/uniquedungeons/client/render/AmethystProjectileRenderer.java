package com.becks.uniquedungeons.client.render;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.client.model.AmethystProjectileModel;
import com.becks.uniquedungeons.common.entities.AmethystProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class AmethystProjectileRenderer extends EntityRenderer<AmethystProjectile> {
    public AmethystProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AmethystProjectileModel(pContext.bakeLayer(AmethystProjectileModel.LAYER_LOCATION));
    }

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(UniqueDungeons.MOD_ID, "textures/entity/amethyst_projectile.png");

    private final AmethystProjectileModel model;

    @Override
    public ResourceLocation getTextureLocation(AmethystProjectile pEntity) {
        return TEXTURE_LOCATION;
    }
    @Override
    public void render(AmethystProjectile pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, true);
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
