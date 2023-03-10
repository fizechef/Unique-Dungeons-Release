package com.becks.uniquedungeons.mixin;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.items.artifacts.RageBlade;
import com.becks.uniquedungeons.common.stackCooldowns.StackCooldownMap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Inject(method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "HEAD"), cancellable = false)
    void inject$renderGuiItemDecorations(Font pFr, ItemStack pStack, int pXPosition, int pYPosition, String pText, CallbackInfo ci){
        //System.out.println("Called Mixin");
        if (!pStack.isEmpty() && (pStack.getItem() instanceof RageBlade)){
            //System.out.println("stack there " + pStack);
            Player player = UniqueDungeons.proxy.getPlayer();
            float f = 0.0f;
            if (player != null ) {
                //System.out.println(StackCooldownMap.getCooldowns(player));
                f = (StackCooldownMap.getCooldowns(player).getCooldownPercent(pStack, Minecraft.getInstance().getFrameTime()));
                //System.out.println(f);
            }
            if (f > 0.0F) {
               //System.out.println(f);
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tesselator tesselator1 = Tesselator.getInstance();
                BufferBuilder bufferbuilder1 = tesselator1.getBuilder();
                //((ItemRenderer)(Object)this).fillRect(bufferbuilder1, pXPosition, pYPosition + Mth.floor(16.0F * (1.0F - f)), 16, Mth.ceil(16.0F * f), 255, 255, 255, 127);
                double pY = pYPosition + Mth.floor(16.0F * (1.0F - f));
                double pWidth = 16;
                double pHeight = Mth.ceil(16.0F * f);
                int pRed = 255;
                int pGreen = 255;
                int pBlue = 255;
                int pAlpha = 127;
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                bufferbuilder1.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                bufferbuilder1.vertex((double)(pXPosition + 0), (double)(pY + 0), 0.0D).color(pRed, pGreen, pBlue, pAlpha).endVertex();
                bufferbuilder1.vertex((double)(pXPosition + 0), (double)(pY + pHeight), 0.0D).color(pRed, pGreen, pBlue, pAlpha).endVertex();
                bufferbuilder1.vertex((double)(pXPosition + pWidth), (double)(pY + pHeight), 0.0D).color(pRed, pGreen, pBlue, pAlpha).endVertex();
                bufferbuilder1.vertex((double)(pXPosition + pWidth), (double)(pY + 0), 0.0D).color(pRed, pGreen, pBlue, pAlpha).endVertex();
                BufferUploader.drawWithShader(bufferbuilder1.end());

                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
        }
    }
}
