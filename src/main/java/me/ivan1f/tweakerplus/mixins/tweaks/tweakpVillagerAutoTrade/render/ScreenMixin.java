package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12000
//$$ import net.minecraft.client.gui.DrawContext;
//#elseif MC > 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(
            //#if MC >= 12006
            //$$ method = "renderWithTooltip",
            //#elseif MC >= 12000
            //$$ method = "renderBackground",
            //#elseif MC > 11600
            //$$ method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V",
            //#else
            method = "renderBackground()V",
            //#endif
            at = @At("RETURN")
    )
    private void onDrawDefaultBackgroundPost(
            //#if MC > 12000
            //$$ DrawContext matrixStackOrDrawContext,
            //#elseif MC > 11600
            //$$ MatrixStack matrixStackOrDrawContext,
            //#endif

            //#if MC >= 12002
            //$$ int mouseX, int mouseY, float delta,
            //#endif
            CallbackInfo ci
    ) {
        RecipeSelectorRenderer
                .getInstance()
                .onDrawBackgroundPost(
                        //#if MC > 11600
                        //$$ matrixStackOrDrawContext
                        //#endif
                );
    }
}
