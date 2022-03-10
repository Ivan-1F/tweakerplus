package me.ivan1f.tweakerplus.mixins.core.gui;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WidgetListBase.class)
public abstract class WidgetListBaseMixin<TYPE, WIDGET extends WidgetListEntryBase<TYPE>> {
    private boolean shouldRenderTweakerPlusConfigGuiDropDownList = false;

    @Inject(method = "drawContents", at = @At("HEAD"), remap = false)
    private void drawTweakerPlusConfigGuiDropDownListSetFlag$tweakerplus(CallbackInfo ci) {
        shouldRenderTweakerPlusConfigGuiDropDownList = true;
    }

    @Inject(
            method = "drawContents",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetBase;postRenderHovered(IIZLnet/minecraft/client/util/math/MatrixStack;)V",
                    remap = true
            ),
            remap = false
    )
    private void drawTweakerPlusConfigGuiDropDownListAgainBeforeHover$tweakerplus(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        this.drawTweakerPlusConfigGuiDropDownListAgain$tweakerplus(matrixStack, mouseX, mouseY);
    }

    @Inject(method = "drawContents", at = @At("TAIL"), remap = false)
    private void drawTweakerMoreConfigGuiDropDownListAgainAfterHover$tweakerplus(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        this.drawTweakerPlusConfigGuiDropDownListAgain$tweakerplus(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isTweakerPlusConfigGui() {
        if ((WidgetListBase<?, ?>) (Object) this instanceof WidgetListConfigOptions) {
            GuiConfigsBase guiConfig = ((WidgetListConfigOptionsAccessor) this).getParent();
            return guiConfig instanceof TweakerPlusConfigGui;
        }
        return false;
    }

    private void drawTweakerPlusConfigGuiDropDownListAgain$tweakerplus(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.isTweakerPlusConfigGui() && this.shouldRenderTweakerPlusConfigGuiDropDownList) {
            GuiConfigsBase guiConfig = ((WidgetListConfigOptionsAccessor) this).getParent();

            // render it again to make sure it's on the top but below hovering widgets
            ((TweakerPlusConfigGui) guiConfig).renderDropDownList(matrixStack, mouseX, mouseY);
            this.shouldRenderTweakerPlusConfigGuiDropDownList = false;
        }
    }
}
