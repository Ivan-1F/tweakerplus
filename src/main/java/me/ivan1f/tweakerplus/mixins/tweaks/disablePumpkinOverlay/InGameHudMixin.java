package me.ivan1f.tweakerplus.mixins.tweaks.disablePumpkinOverlay;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    private void disablePumpkinOverlay(CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_PUMPKIN_OVERLAY.getBooleanValue()) ci.cancel();
    }
}
