package me.ivan1f.tweakerplus.mixins.tweaks.disablePortalOverlay;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void disablePortalOverlay(CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_PORTAL_OVERLAY.getBooleanValue()) ci.cancel();
    }
}
