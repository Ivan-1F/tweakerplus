package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(
            method = "render(FJZ)V",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    //#if MC > 11600
                    //$$ target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"
                    //#else
                    target = "Lnet/minecraft/client/gui/screen/Screen;render(IIF)V"
                    //#endif
            )
    )
    private void onDrawScreenPost(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        RecipeSelectorRenderer
                .getInstance()
                .onDrawScreenPost(
                        //#if MC > 11600
                        //$$ new MatrixStack()
                        //#endif
                );
    }
}
