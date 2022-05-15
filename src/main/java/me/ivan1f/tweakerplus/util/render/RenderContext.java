package me.ivan1f.tweakerplus.util.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.util.math.Matrix4f;

public class RenderContext {
    public RenderContext() {
    }

    public void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    public void popMatrix() {
        GlStateManager.popMatrix();
    }

    public void translate(double x, double y, double z) {
        GlStateManager.translated(x, y, z);
    }

    public void scale(double x, double y, double z) {
        GlStateManager.scaled(x, y, z);
    }

    public void multMatrix(Matrix4f matrix4f) {
        GlStateManager.multMatrix(matrix4f);
    }

    public void enableDepthTest() {
        GlStateManager.enableDepthTest();
    }

    public void disableDepthTest() {
        GlStateManager.disableDepthTest();
    }

    public void enableTexture() {
        GlStateManager.enableTexture();
    }

    public void enableAlphaTest() {
        GlStateManager.enableAlphaTest();
    }

    public void depthMask(boolean mask) {
        GlStateManager.depthMask(mask);
    }

    public void color4f(float red, float green, float blue, float alpha) {
        GlStateManager.color4f(red, green, blue, alpha);
    }

    public void enableBlend() {
        GlStateManager.enableBlend();
    }

    public void blendFunc(GlStateManager.SourceFactor srcFactor, GlStateManager.DestFactor dstFactor) {
        GlStateManager.blendFunc(srcFactor, dstFactor);
    }

    public void disableLighting() {
        GlStateManager.disableLighting();
    }
}
