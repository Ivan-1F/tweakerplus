package me.ivan1f.tweakerplus.mixins.tweaks.tweakerpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render(FJZ)V",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/gui/screen/Screen;render(IIF)V"))
    private void onDrawScreenPost(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        RecipeSelectorRenderer.getInstance().onDrawScreenPost();
    }
}
