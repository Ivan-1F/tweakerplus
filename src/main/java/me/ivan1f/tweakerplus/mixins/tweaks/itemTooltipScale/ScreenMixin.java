package me.ivan1f.tweakerplus.mixins.tweaks.itemTooltipScale;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.RenderUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {
    @Nullable
    private RenderUtil.Scaler scaler = null;

    @Inject(method = "renderTooltipFromComponents", at = @At("HEAD"))
    private void tweakerPlus_itemTooltipScale_push(MatrixStack matrices, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(x, y, TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.getDoubleValue());
            this.scaler.apply(matrices);
        }
    }

    @Inject(method = "renderTooltipFromComponents", at = @At("RETURN"))
    private void tweakerPlus_itemTooltipScale_pop(MatrixStack matrices, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
