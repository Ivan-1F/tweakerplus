// Reference: https://github.com/Fallen-Breath/tweakermore/blob/master/versions/1.20.1/src/main/java/me/fallenbreath/tweakermore/util/render/context/RenderContextUtils.java

package me.ivan1f.tweakerplus.util.render.context;

import me.fallenbreath.tweakermore.mixins.util.render.DrawContextAccessor;
import me.fallenbreath.tweakermore.util.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class RenderContextUtils {
    public static DrawContext createDrawContext(MatrixStack matrixStack) {
        var drawContext = new DrawContext(MinecraftClient.getInstance(), RenderUtils.getVertexConsumer());
        ((DrawContextAccessor) drawContext).setMatrices(matrixStack);
        return drawContext;
    }
}
