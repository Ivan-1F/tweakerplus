package me.ivan1f.tweakerplus.util;

//#if MC >= 11500
import com.mojang.blaze3d.systems.RenderSystem;
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//#endif

//#if MC > 11700
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import org.jetbrains.annotations.Nullable;
//#endif

public class RenderUtil {
    public static Scaler createScaler(int anchorX, int anchorY, double factor) {
        return new Scaler(anchorX, anchorY, factor);
    }

    public static class Scaler {
        private final int anchorX;
        private final int anchorY;
        private final double factor;

        //#if MC > 11700
        //$$ private MatrixStack matrixStack = null;
        //#endif

        private Scaler(int anchorX, int anchorY, double factor) {
            this.anchorX = anchorX;
            this.anchorY = anchorY;
            if (factor <= 0) {
                throw new IllegalArgumentException("factor should be greater than 0, but " + factor + " found");
            }
            this.factor = factor;
        }

        public void apply(
                //#if MC > 11700
                //$$ MatrixStack matrixStack
                //#endif
        ) {
            //#if MC >= 11700
            //$$ this.matrixStack = matrixStack;
            //$$ matrixStack.push();
            //$$ matrixStack.translate(-anchorX * factor, -anchorY * factor, 0);
            //$$ matrixStack.scale((float) factor, (float) factor, 1);
            //$$ matrixStack.translate(anchorX / factor, anchorY / factor, 0);
            //#elseif MC >= 11500
            RenderSystem.pushMatrix();
            RenderSystem.translated(-anchorX * factor, -anchorY * factor, 0);
            RenderSystem.scaled(factor, factor, 1);
            RenderSystem.translated(anchorX / factor, anchorY / factor, 0);
            //#else
            //$$ GlStateManager.pushMatrix();
            //$$ GlStateManager.translated(-anchorX * factor, -anchorY * factor, 0);
            //$$ GlStateManager.scaled(factor, factor, 1);
            //$$ GlStateManager.translated(anchorX / factor, anchorY / factor, 0);
            //#endif
        }

        public void restore() {
            //#if MC >= 11700
            //$$ if (this.matrixStack != null) {
            //$$     this.matrixStack.pop();
            //$$ } else {
            //$$     throw new RuntimeException("RenderUtil.Scaler: Calling restore before calling apply");
            //$$ }
            //$$ this.matrixStack = null;
            //#elseif MC >= 11500
            RenderSystem.popMatrix();
            //#else
            //$$ GlStateManager.popMatrix();
            //#endif
        }
    }
}
