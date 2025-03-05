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

//#if MC >= 12100
//$$ import net.minecraft.client.render.RenderTickCounter;
//#endif

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    //#if MC >= 12000
                    //$$ target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V"
                    //#elseif MC >= 11900
                    //$$ target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"
                    //#elseif MC > 11600
                    //$$ target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"
                    //#else
                    target = "Lnet/minecraft/client/gui/screen/Screen;render(IIF)V"
                    //#endif
            )
    )
    private void onDrawScreenPost(
            //#if MC >= 12100
            //$$ RenderTickCounter tickCounter, boolean tick,
            //#else
            float partialTicks, long nanoTime, boolean renderWorldIn,
            //#endif
            CallbackInfo ci
    ) {
        RecipeSelectorRenderer
                .getInstance()
                .onDrawScreenPost(
                        //#if MC > 11600
                        //$$ new MatrixStack()
                        //#endif
                );
    }
}
