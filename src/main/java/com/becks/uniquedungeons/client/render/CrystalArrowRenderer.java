package com.becks.uniquedungeons.client.render;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.client.model.AmethystProjectileModel;
import com.becks.uniquedungeons.common.entities.AmethystProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class CrystalArrowRenderer extends ArrowRenderer {

    public CrystalArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return new ResourceLocation(UniqueDungeons.MOD_ID,"textures/entity/crystal_arrow.png");
    }
}
