package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render(FJZ)V",
            at = @At(
                    value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"
            )
    )
    private void onDrawScreenPost(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        RecipeSelectorRenderer.getInstance().onDrawScreenPost(new MatrixStack());
    }
}
