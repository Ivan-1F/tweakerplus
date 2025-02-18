package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(
            //#if MC > 11600
            //$$ method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V",
            //#else
            method = "renderBackground()V",
            //#endif
            at = @At("RETURN")
    )
    private void onDrawDefaultBackgroundPost(
            //#if MC > 11600
            //$$ MatrixStack matrixStack,
            //#endif
            CallbackInfo ci
    ) {
        RecipeSelectorRenderer
                .getInstance()
                .onDrawBackgroundPost(
                        //#if MC > 11600
                        //$$ matrixStack
                        //#endif
                );
    }
}
