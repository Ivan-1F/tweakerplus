package me.ivan1f.tweakerplus.mixins.tweaks.tweakerpVillagerAutoTrade.render;

import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.RecipeSelectorRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "renderBackground()V", at = @At("RETURN"))
    private void onDrawDefaultBackgroundPost(CallbackInfo ci) {
        RecipeSelectorRenderer.getInstance().onDrawBackgroundPost();
    }
}
