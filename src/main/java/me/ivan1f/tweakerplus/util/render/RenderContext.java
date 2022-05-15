package me.ivan1f.tweakerplus.util.render;

import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.math.Matrix4f;

@SuppressWarnings("deprecation")
public class RenderContext {
    @NotNull
    private final MatrixStack matrixStack;

    public RenderContext(@NotNull MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    public @NotNull MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public void pushMatrix() {
        RenderSystem.pushMatrix();
    }

    public void popMatrix() {
        RenderSystem.popMatrix();
    }

    public void translate(double x, double y, double z) {
        RenderSystem.translated(x, y, z);
    }

    public void scale(double x, double y, double z) {
        RenderSystem.scaled(x, y, z);
    }

    public void multMatrix(Matrix4f matrix4f) {
        RenderSystem.multMatrix(matrix4f);
    }

    public void enableDepthTest() {
        RenderSystem.enableDepthTest();
    }

    public void disableDepthTest() {
        RenderSystem.disableDepthTest();
    }

    public void enableTexture() {
        RenderSystem.enableTexture();
    }

    public void enableAlphaTest() {
        RenderSystem.enableAlphaTest();
    }

    public void depthMask(boolean mask) {
        RenderSystem.depthMask(mask);
    }

    public void enableBlend() {
        RenderSystem.enableBlend();
    }

    public void blendFunc(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor) {
        RenderSystem.blendFunc(srcFactor, dstFactor);
    }

    public void disableLighting() {
        RenderSystem.disableLighting();
    }
}
