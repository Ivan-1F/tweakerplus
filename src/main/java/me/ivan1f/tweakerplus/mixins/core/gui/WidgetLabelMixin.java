package me.ivan1f.tweakerplus.mixins.core.gui;

import me.ivan1f.tweakerplus.util.render.RenderContext;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetLabel;
import me.ivan1f.tweakerplus.gui.TweakerPlusOptionLabel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(WidgetLabel.class)
public abstract class WidgetLabelMixin extends WidgetBase {
    @Shadow(remap = false)
    protected boolean centered;

    @Shadow(remap = false)
    @Final
    protected int textColor;

    @Shadow(remap = false)
    @Final
    protected List<String> labels;

    public WidgetLabelMixin(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    private boolean shouldUseTranslatedOptionLabelLogic$tweakerplus() {
        WidgetLabel self = (WidgetLabel) (Object) this;
        return self instanceof TweakerPlusOptionLabel && ((TweakerPlusOptionLabel) self).shouldShowOriginalLines();
    }

    @ModifyVariable(
            method = "render",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=0",
                    remap = false
            ),
            remap = false,
            ordinal = 4
    )
    private int translatedOptionLabelShiftyTextStart$tweakerplus(int yTextStart) {
        if (this.shouldUseTranslatedOptionLabelLogic$tweakerplus()) {
            yTextStart -= this.fontHeight * TweakerPlusOptionLabel.TRANSLATION_SCALE * 0.6;
        }
        return yTextStart;
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetLabel;centered:Z",
                    remap = false
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void translatedOptionLabelRenderTranslation$tweakerplus(int mouseX, int mouseY, boolean selected, CallbackInfo ci, int fontHeight, int yCenter, int yTextStart, int i, String text) {
        if (this.shouldUseTranslatedOptionLabelLogic$tweakerplus()) {
            int color = darkerColor$tweakerplus(this.textColor);
            double scale = TweakerPlusOptionLabel.TRANSLATION_SCALE;
            String originText = ((TweakerPlusOptionLabel) (Object) this).getOriginalLines()[i];
            int x = this.x + (this.centered ? this.width / 2 : 0);
            int y = (int) (yTextStart + (this.labels.size() + i * scale + 0.2) * fontHeight);

            RenderContext renderContext = new RenderContext();

            renderContext.pushMatrix();
            renderContext.scale(scale, scale, 1);
            x /= scale;
            y /= scale;

            if (this.centered) {
                this.drawCenteredStringWithShadow(x, y, color, originText);
            } else {
                this.drawStringWithShadow(x, y, color, originText);
            }
            renderContext.popMatrix();
        }
    }

    @SuppressWarnings("PointlessBitwiseExpression")
    private static int darkerColor$tweakerplus(int color) {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        r *= 0.6;
        g *= 0.6;
        b *= 0.6;
        return (a << 24) | (r << 16) | (g << 8) | (b << 0);
    }
}
