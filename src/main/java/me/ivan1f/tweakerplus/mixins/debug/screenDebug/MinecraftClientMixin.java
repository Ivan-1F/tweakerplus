package me.ivan1f.tweakerplus.mixins.debug.screenDebug;

import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "openScreen", at = @At("HEAD"))
    private void logOpenScreen(Screen screen, CallbackInfo ci) {
        if (TweakerPlusConfigs.SCREEN_DEBUG.getBooleanValue()) {
            if (screen != null) {
                if (screen.getTitle().getString().isEmpty()) {
                    TweakerPlusMod.LOGGER.info(String.format("[ScreenDebug] %s", screen.getClass().getName()));
                } else {
                    TweakerPlusMod.LOGGER.info(String.format("[ScreenDebug] %s | %s", screen.getTitle().getString(), screen.getClass().getName()));
                }
            }
        }
    }
}
