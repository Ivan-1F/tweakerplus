package me.ivan1f.tweakerplus.mixins.tweaks.itemTooltipScale;

import me.fallenbreath.tweakermore.util.render.RenderUtils;
import me.fallenbreath.tweakermore.util.render.context.RenderContext;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DrawContext.class)
public abstract class ScreenMixin {
    @Shadow public abstract MatrixStack getMatrices();

    @Nullable
    private RenderUtils.Scaler scaler = null;

    @Inject(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Lnet/minecraft/client/gui/tooltip/TooltipPositioner;II)V",
            at = @At("HEAD")
    )
    private void itemTooltipScale_push(TextRenderer textRenderer, List<OrderedText> text, TooltipPositioner positioner, int x, int y, CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.isModified()) {
            this.scaler = RenderUtils.createScaler(x, y, TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.getDoubleValue());
            this.scaler.apply(RenderContext.of(this.getMatrices()));
        }
    }

    @Inject(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Lnet/minecraft/client/gui/tooltip/TooltipPositioner;II)V",
            at = @At("RETURN")
    )
    private void itemTooltipScale_pop(TextRenderer textRenderer, List<OrderedText> text, TooltipPositioner positioner, int x, int y, CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
