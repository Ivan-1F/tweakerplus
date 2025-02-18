package me.ivan1f.tweakerplus.mixins.tweaks.itemTooltipScale;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.RenderUtil;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//#if MC >= 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import net.minecraft.text.OrderedText;
//#endif

//#if MC >= 11700
//$$ import net.minecraft.client.gui.tooltip.TooltipComponent;
//#elseif MC >= 11600
//$$ import net.minecraft.text.OrderedText;
//#endif

@Mixin(Screen.class)
public class ScreenMixin {
    @Nullable
    private RenderUtil.Scaler scaler = null;

    @Inject(
            //#if MC > 11700
            //$$ method = "renderTooltipFromComponents",
            //#elseif MC > 11600
            //$$ method = "renderOrderedTooltip",
            //#else
            method = "renderTooltip(Ljava/util/List;II)V",
            //#endif
            at = @At("HEAD")
    )
    private void tweakerPlus_itemTooltipScale_push(
            //#if MC > 11700
            //$$ MatrixStack matrices, List<TooltipComponent> components,
            //#elseif MC > 11600
            //$$ MatrixStack matrices, List<? extends OrderedText> lines,
            //#else
            List<String> text,
            //#endif
            int x, int y, CallbackInfo ci
    ) {
        this.scaler = null;
        if (TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(x, y, TweakerPlusConfigs.ITEM_TOOLTIP_SCALE.getDoubleValue());
            this.scaler.apply(
                    //#if MC >= 11700
                    //$$ matrices
                    //#endif
            );
        }
    }

    @Inject(
            //#if MC > 11700
            //$$ method = "renderTooltipFromComponents",
            //#elseif MC > 11600
            //$$ method = "renderOrderedTooltip",
            //#else
            method = "renderTooltip(Ljava/util/List;II)V",
            //#endif
            at = @At("RETURN")
    )
    private void tweakerPlus_itemTooltipScale_pop(
            //#if MC > 11700
            //$$ MatrixStack matrices, List<TooltipComponent> components,
            //#elseif MC > 11600
            //$$ MatrixStack matrices, List<? extends OrderedText> lines,
            //#else
            List<String> text,
            //#endif
            int x, int y, CallbackInfo ci
    ) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
